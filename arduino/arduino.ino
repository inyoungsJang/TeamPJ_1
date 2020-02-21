//LCD
#include <Wire.h>
#include <LiquidCrystal_I2C.h>

//RFID
#include <SPI.h>
#include <MFRC522.h>

//bluetooth
#include <SoftwareSerial.h>
SoftwareSerial BTSerial(2, 3); //bluetooth module Tx:Digital 2 Rx: Digital 3

//RFID
#define RST_PIN         9          
#define SS_PIN          10          


//bluetooth
byte buffer[1024];
int bufferPosition; //버퍼에 기록할 위치
//LCD
LiquidCrystal_I2C lcd(0x27, 16, 2);
// RFID
MFRC522 mfrc522(SS_PIN, RST_PIN);   // Create MFRC522 instance
// 등록 4 102 177 43
// 미등록 2 219 214 52
//byte Access[] = {4, 102, 177, 43};

//int led_red = 8;
int led_green = 7;
int buzzer = 6;
String strRFID = "";
boolean isOpen = false;

void setup() {
  lcd.init(); // I2C LC를 초기화
  lcd.backlight(); //lcd 백라이트 켜줍니다.
  lcd.print("hello aduino !!! heellelelfleflel");
  
  Serial.begin(9600);                                           // Initialize serial communications with the PC
  SPI.begin();                                                  // Init SPI busmfrc522.PCD_Init();                                              // Init MFRC522 card
  Serial.println(F("Read personal data on a MIFARE PICC:"));    //shows in serial that it is ready to read

  pinMode(led_green, OUTPUT);
//  pinMode(led_red, OUTPUT);
  pinMode(buzzer, OUTPUT);

  BTSerial.begin(9600); // 블루투스 모듈 초기화
  bufferPosition = 0;
}

void loop() {

  // 앱으로부터 Open 여부 수신
  if (BTSerial.available()) {
    byte data = BTSerial.read();
    //    Serial.write(data);
    buffer[bufferPosition++] = data;
    if (data == '\n') {      
      buffer[bufferPosition - 1] = 0; //buffer에 마지막 한자리떄문에 비교못하기에 0으로 초기화
      String msg = String((char*)buffer);
      //      Serial.print("받은 msg: ");
      //      Serial.println(msg);
      if(!isOpen){
        isOpenDoor(msg);
      }      
    }
  }  
  sendRFID();    
}

void sendRFID() { // RFID가 읽히면 앱으로 RFID값을 전송한다.

  if ( ! mfrc522.PICC_IsNewCardPresent()) {
    return;
  }
  if ( ! mfrc522.PICC_ReadCardSerial()) {
    return;
  }

  for (byte i = 0; i < 4; i++) {
    strRFID += mfrc522.uid.uidByte[i];
    //    Serial.print(mfrc522.uid.uidByte[i]);
    //    Serial.print("/");
  }
  
  Serial.println(strRFID);
  strRFID = strRFID+'\n'; //전송할떄는 \n을 추가해야함.
  BTSerial.print(strRFID);
  strRFID = "";
  delay(1500);
}

void isOpenDoor(String msg) {
  isOpen = true;
  lcd.clear();
  Serial.println(String("msg: ") + msg);
  if (msg == "true") {
    digitalWrite(led_green, HIGH);
//    digitalWrite(led_red, LOW);
    
    Serial.println("등록된 카드입니다.");
    lcd.setCursor(0,0);
    lcd.print("Welcome");
    
    tone(buzzer, 523, 100);
    delay(500);
  } else {
    digitalWrite(led_green, LOW);
//    digitalWrite(led_red, HIGH);
    
    Serial.println("넌 도대체 누구냐?");
    lcd.setCursor(0, 0);
    lcd.print("WHO ARE YOU?");
    
    tone(buzzer, 523, 100);
    delay(200);
    tone(buzzer, 523, 100);
    delay(200);
  }
  memset(buffer, 0, sizeof(buffer));  
  bufferPosition = 0;
  
  delay(5000);
  isOpen = false;
  digitalWrite(led_green, LOW);
//  digitalWrite(led_red, LOW);
}
