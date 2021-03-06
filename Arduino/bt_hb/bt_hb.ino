#include <SoftwareSerial.h> 

SoftwareSerial BTSerial(A3, A5); // 소프트웨어 시리얼 (TX,RX)

int PulseSensorPurplePin = 0;
int Signal;

void setup(){
  Serial.begin(9600);
  Serial.println("Hello!");  
  BTSerial.begin(9600);
}

void loop(){
  Signal = analogRead(PulseSensorPurplePin); 
  Serial.println(Signal);
  delay(100);
  
  while (BTSerial.available()){ 
    byte data = BTSerial.read();
    Serial.write(data);
  }
  BTSerial.println(Signal);
}
