package com.deuksoft.smartiv

data class PatientData(var uid : String, var name : String, var birth : String, var bt_mac : String, var gender : String, var guardian_pn : String
, var uniquenes : String, var did : String, var disease_name : String, var family_doctor : String, var guardian : String, var history : String)

data class getData(var res : String)

data class loginss(var id : String, var pw : String, var BluetoothMac :String)