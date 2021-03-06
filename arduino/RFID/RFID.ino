#include <SPI.h>
#include <MFRC522.h>

#define RST_PIN         9           // Configurable, see typical pin layout above
#define SS_PIN          10          // Configurable, see typical pin layout above

MFRC522 mfrc522(SS_PIN, RST_PIN);   // Create MFRC522 instance

//*****************************************************************************************//
void setup() {
  Serial.begin(9600);                                           // Initialize serial communications with the PC
  SPI.begin();                                                  // Init SPI bus
  mfrc522.PCD_Init();                                              // Init MFRC522 card
  Serial.println(F("Read personal data on a MIFARE PICC:"));    //shows in serial that it is ready to read
}


void loop() {  
  if ( ! mfrc522.PICC_IsNewCardPresent()) {    
    return;
  }

  if ( ! mfrc522.PICC_ReadCardSerial()) {
    
    return;
  }
  
  Serial.print("카드 ID: ");
  for(byte i = 0; i<4; i++){
    Serial.print(mfrc522.uid.uidByte[i]);
    Serial.print(" ");
  }
  Serial.println(); 
}
