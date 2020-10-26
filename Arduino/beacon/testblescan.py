# test BLE Scanning software
# jcs 6/8/2014

import blescan
import sys

import bluetooth._bluetooth as bluez

dev_id = 0

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
		if int(TX) > 0:
			TX = str(-59)
		if devName == "ec:24:b8:44:83:6f" or devName == "ec:24:b8:05:54:8a" or devName == "de:68:2d:22:b9:d5":
			
			wheel1 = beacons("", "", "")
			wheel2 = beacons("", "", "")
			patient = beacons("", "", "")
			distance = 0
			if devName == "ec:24:b8:44:83:6f":
				wheel1 = beacons(devName, RSSI, TX)
				print("wheel1 devName = {0}, wheel1 RSSI = {1}, whee11 TX = {2}".format(wheel1.devName, wheel1.RSSI, wheel1.TX))
				print("distance = {0}".format(wheel1.distance(RSSI, TX)*100))
			elif devName == "ec:24:b8:05:54:8a":
				wheel2 = beacons(devName, RSSI, TX)
				print("wheel2 devName = {0}, wheel2 RSSI = {1}, wheel2 TX = {2}".format(wheel2.devName, wheel2.RSSI, wheel2.TX))
				print("distance = {0}".format(wheel1.distance(RSSI, TX)*100))
			elif devName == "de:68:2d:22:b9:d5":
				patient = beacons(devName, RSSI, TX)
				print("patient devName = {0}, patient RSSI = {1}, patient TX = {2}".format(patient.devName, patient.RSSI, patient.TX))
				print("distance = {0}".format(patient.distance(RSSI, TX)*100))
					
			print("wheel1: {0}".format(wheel1.devName))
			print("wheel2: {0}".format( wheel2.devName))
			print("patient: {0}".format( patient.devName))

