# test BLE Scanning software
# jcs 6/8/2014

import blescan
import sys

import bluetooth._bluetooth as bluez

import RPi.GPIO as GPIO
from time import sleep

dev_id = 0

STOP = 0
GO = 1

CH1 = 0
CH2 = 1

OUTPUT = 1
INPUT = 0

HIGH = 1
LOW = 0

ENA = 26
ENB = 0

IN1 = 19
IN2 = 13
IN3 = 6
IN4 = 5

def setPinConfig(EN, INA, INB):
    GPIO.setup(EN, GPIO.OUT)
    GPIO.setup(INA, GPIO.OUT)
    GPIO.setup(INB, GPIO.OUT)
    pwm = GPIO.PWM(EN, 100)
    pwm.start(0)

def setMotorControl(pwm, INA, INB, speed, stat):
    pwm.ChangeDutyCycle(speed)
    if stat == GO:
        GPIO.output(INA, HIGH)
        GPIO.output(INB, LOW)
    elif stat == STOP:
        GPIO.output(INA, LOW)
        GPIO.output(INB, LOW)

def setMotor(ch, speed, stat):
    if ch == CH1:
        setMotorControl(pwmA, IN1, IN2, speed, stat)
    else:
        setMotorControl(pwmB, IN3, IN4, speed, stat)

GPIO.setmode(GPIO.BCM)

pwmA = setPinConfig(ENA, IN1, IN2)

#beacon data sava class : made Deuk
class beacons:
    def __init__(self, devName, RSSI, TX):
        self.devName = devName
        self.RSSI = RSSI
        self.TX = TX

    def distance(self, RSSI, TX):
        return (10 ** ((float(TX) - float(RSSI))/(10 * 2)))

try:
    sock = bluez.hci_open_dev(dev_id)
    print ("ble thread started")

except:
    print ("error accessing bluetooth device...")
    sys.exit(1)

blescan.hci_le_set_scan_parameters(sock)
blescan.hci_enable_le_scan(sock)

while True:
    returnedList = blescan.parse_events(sock, 10)
    print ("----------")
    for beacon in returnedList:
        beaconToken = beacon.split(",")
        devName = beaconToken[0]
        RSSI = beaconToken[5]
        TX = beaconToken[4]

        patient = beacons("", "", "")

        if int(TX) > 0:
            TX = str(-59)
        if devName == "78:a5:04:28:bd:76":

            patient = beacons("", "", "")
            distance = 0
            patient = beacons(devName, RSSI, TX)
            print("patient devName = {0}, patient RSSI = {1}, patient TX = {2}"$
            print("distance = {0}".format(patient.distance(RSSI, TX)*100))
            print("patient: {0}".format( patient.devName))

            
            if int(patient.RSSI) > -50:
                setMotor(CH1, 80, STOP)
            else if int(patient.RSSI) > - 80:
                setMotor(CH1, 66, GO)
            else:
				setMotor(CH1, 100, GO)

GPIO.cleanup()


