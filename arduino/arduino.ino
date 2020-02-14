#include <SPI.h>
#include <MFRC522.h>

//RST 9   //리셋
//SDA 10  //데이터를주고 받는
//MOSI 11
//MISO 12
//SCK 13
//GND GND
#define RST_PIN         9           // Configurable, see typical pin layout above
#define SS_PIN          10          // Configurable, see typical pin layout above

MFRC522 mfrc522(SS_PIN, RST_PIN);   // Create MFRC522 instance
//카드 244 140 191 42
//열쇠 87 28 101 123

byte Access[] = {244, 140, 191, 42};
int led_red = 4;
int led_green = 3;
int buzzer = 6;
boolean ccard = true;

void setup() {
  Serial.begin(9600);                                           // Initialize serial communications with the PC
  SPI.begin();                                                  // Init SPI bus
  mfrc522.PCD_Init();                                              // Init MFRC522 card
  Serial.println(F("Read personal data on a MIFARE PICC:"));    //shows in serial that it is ready to read

  pinMode(led_green, OUTPUT);
  pinMode(led_red, OUTPUT);
  pinMode(buzzer, OUTPUT);
}

void loop() {
  if ( ! mfrc522.PICC_IsNewCardPresent()) {
    return;
  }

  if ( ! mfrc522.PICC_ReadCardSerial()) {
    return;
  }
  ccard = true;
  for (byte i = 0; i < 4; i++) {
    if (mfrc522.uid.uidByte[i] != Access[i]) {
      ccard = false;
    }
  }

  if (ccard) {
    digitalWrite(led_green, HIGH);
    digitalWrite(led_red, LOW);
    Serial.println("등록된 카드입니다.");
    tone(6, 523, 100);
    delay(500);
  } else {
    digitalWrite(led_green, LOW);
    digitalWrite(led_red, HIGH);
    Serial.println("넌 도대체 누구냐?");
    tone(6, 523, 100);
    delay(200);
    tone(6, 523, 100);
    delay(200);
  }
  Serial.println();
  delay(1000);
  digitalWrite(led_green, LOW);
  digitalWrite(led_red, LOW);

}
