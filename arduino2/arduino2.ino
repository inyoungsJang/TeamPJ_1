#include <Wire.h>//LCD
#include <LiquidCrystal_I2C.h>//LCD
#include <SPI.h>//RFID
#include <MFRC522.h>//RFID
#include <SoftwareSerial.h>//bluetooth

#define RST_PIN         9   //RFID
#define SS_PIN          10  //RFID
#define TRIG 5      //WAVE
#define ECHO 4      //WAVE
#define LED_WAVE_RED 8  //WAVE

#define BUZZER 6
#define LED_GREEN 7


SoftwareSerial BTSerial(2, 3); //bluetooth module Tx:Digital 2 Rx: Digital 3
LiquidCrystal_I2C lcd(0x27, 16, 2); //LCD
MFRC522 mfrc522(SS_PIN, RST_PIN);   // RFID Create MFRC522 instance

byte buffer[512];
int bufferPosition;
String strRFID = "";
boolean isCheckRFID;

void setup() {
  Init();
  SerialInit();
}

void Init() {
  pinMode(TRIG, OUTPUT);
  pinMode(ECHO, INPUT);
  pinMode(LED_WAVE_RED, OUTPUT);

  pinMode(BUZZER, OUTPUT);
  pinMode(LED_GREEN, OUTPUT);

  lcd.init();
  lcd.backlight();
  isCheckRFID = false;
  bufferPosition = 0;
}

void SerialInit() {
  Serial.begin(9600);
  SPI.begin();
  Serial.println(F("Read personal data on a MIFARE PICC:"));
  BTSerial.begin(9600);
}

void loop() {
  loopWave();
  loopBT();

  if ( ! mfrc522.PICC_IsNewCardPresent()) {
    return;
  }
  if ( ! mfrc522.PICC_ReadCardSerial()) {
    return;
  }
  if (!isCheckRFID) {
    sendRFID(); // RFID 값이 읽히면 sendRFID()가 수행됨
  }
}

void loopBT() {
  if (BTSerial.available()) {
    byte data = BTSerial.read();
    buffer[bufferPosition++] = data;
    if (data == '\n') {
      buffer[bufferPosition - 1] = 0; //buffer에 마지막 한자리떄문에 비교 못하기에 0으로 초기화
      String msg = String((char*)buffer);
      isOpenDoor(msg);
    }
  }
}

void loopWave() {
  digitalWrite(TRIG, LOW);
  delayMicroseconds(2);
  digitalWrite(TRIG, HIGH);
  delayMicroseconds(2);
  digitalWrite(TRIG, LOW);

  long distance = pulseIn(ECHO, HIGH) / 58.2;
  //Serial.print(distance+String(", "));
  digitalWrite(LED_WAVE_RED, LOW);

  if (distance < 30) {
    digitalWrite(LED_WAVE_RED, HIGH);
  }
  delay(100);
}

void sendRFID() { // RFID가 읽히면 앱으로 RFID값을 전송한다.
  isCheckRFID = true;
  lcd.clear();

  for (byte i = 0; i < 4; i++) {
    strRFID += mfrc522.uid.uidByte[i];
  }
  Serial.println(String("RFID value: ") + strRFID);
  strRFID = strRFID + '\n'; //전송할떄는 \n을 추가해야함.
  BTSerial.print(strRFID);
  lcd.setCursor(0, 1);
  lcd.print(strRFID);
  strRFID = "";
}

void isOpenDoor(String msg) {

  Serial.println(String("msg: ") + msg);
  if (msg == "signup") {
    Serial.println("카드를 등록하였습니다.");
    lcd.setCursor(0, 0);
    lcd.print("Resister RFID");

    //digitalWrite(LED_GREEN, HIGH);
    //tone(BUZZER, 523, 100);
    //delay(500);

  } else if (msg == "true") {
    Serial.println("등록된 카드입니다.");
    lcd.setCursor(0, 0);
    lcd.print("Welcome");

    digitalWrite(LED_GREEN, HIGH);
    tone(BUZZER, 523, 100);
  }  else if (msg == "false") {
    Serial.println("넌 도대체 누구냐?");
    lcd.setCursor(0, 0);
    lcd.print("WHO ARE YOU?");

    digitalWrite(LED_GREEN, LOW);
    tone(BUZZER, 523, 100);
    delay(200);
    tone(BUZZER, 523, 100);
    delay(200);


  }delay(3000);
  memset(buffer, 0, sizeof(buffer));
  bufferPosition = 0;
  digitalWrite(LED_GREEN, LOW);
  isCheckRFID = false;
}
