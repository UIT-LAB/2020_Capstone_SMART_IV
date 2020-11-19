#include <SoftwareSerial.h> 

SoftwareSerial BTSerial(A4, A5); // 소프트웨어 시리얼 (TX,RX)

int PulseSensorPurplePin = 0;
int Signal;

void setup(){
  Serial.begin(9600);
  Serial.println("Hello!");  
  BTSerial.begin(9600);
}

void loop(){
  Signal = analogRead(PulseSensorPurplePin); 
  Signal = (Signal/10)+1;
  Serial.println(Signal);
  delay(1000);
  
  while (BTSerial.available()){ 
    byte data = BTSerial.read();
    Serial.write(data);
  }
  BTSerial.println(Signal);
}
