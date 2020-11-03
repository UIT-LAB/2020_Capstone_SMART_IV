package com.deuksoft.smartiv

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dinuscxj.progressbar.CircleProgressBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.UnsupportedEncodingException
import java.util.*


class MainActivity : AppCompatActivity(), CircleProgressBar.ProgressFormatter{
    var mBluetoothDevice: BluetoothDevice? = null
    var mBluetoothSocket: BluetoothSocket? = null
    var mBluetoothHandler: Handler? = null
    var mThreadConnectedBluetooth: ConnectedBluetoothThread? = null
    var readMessage: String? = null
    val BT_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    val BT_MESSAGE_READ = 2
    val BT_CONNECTING_STATUS = 3
    lateinit var patientname : TextView //환자이름
    lateinit var disease_name_txt : TextView //병명
    lateinit var guardian_name_txt : TextView //보호자 이름
    lateinit var family_doctor_txt : TextView // 주치의 이름
    /*lateinit var patientid : TextView
    lateinit var patientid : TextView*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var healthLV : CircleProgressBar = findViewById(R.id.healthLV)
        patientname = findViewById(R.id.patientname)
        disease_name_txt = findViewById(R.id.disease_name_txt)
        guardian_name_txt = findViewById(R.id.guardian_name_txt)
        family_doctor_txt = findViewById(R.id.family_doctor_txt)

        if(intent.hasExtra("PatientBluetooth")){
            mBluetoothDevice = intent.getParcelableExtra("PatientBluetooth")
            Log.e("MACMAC", mBluetoothDevice!!.name);
            try {
                mBluetoothSocket = mBluetoothDevice!!.createRfcommSocketToServiceRecord(BT_UUID)
                mBluetoothSocket!!.connect()
                mThreadConnectedBluetooth = ConnectedBluetoothThread(mBluetoothSocket)
                mThreadConnectedBluetooth!!.start()
                //mBluetoothHandler!!.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget();
            }catch (e: IOException){
                Log.e("error",e.toString())
                Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }

        }

        mBluetoothHandler = object : Handler() {
            override fun handleMessage(msg: Message) {
                var arr :List<String>? = null
                if (msg.what == BT_MESSAGE_READ) {
                    readMessage = null
                    try {
                        readMessage = String((msg.obj as ByteArray), charset("UTF-8"))
                        //readMessage!!.split("\n").toString()
                        arr = readMessage!!.split("\n")

                        //Alcohol = Integer.parseInt(readMessage);
                    } catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                    }
                    healthLV.progress = Integer.parseInt(arr?.get(0).toString().trim())
                    healthLV.max = 1000

                }
            }
        }
        getPatientInfo(mBluetoothDevice!!.address)
        healthLV.setProgressFormatter(this)
        /*healthLV.setProgressFormatter { progress, max ->
            val DEFAULT_PATTERN = "%d BPM"
            String.format(DEFAULT_PATTERN, progress)
        }*/
    }
    override fun format(progress: Int, max: Int): CharSequence? {
        val DEFAULT_PATTERN = "%d BPM"
        return java.lang.String.format(DEFAULT_PATTERN, progress)
    }

    inner class ConnectedBluetoothThread(private val mmSocket: BluetoothSocket?) :Thread() {
        private val mmInStream: InputStream?
        private val mmOutStream: OutputStream?
        override fun run() {
            val buffer = ByteArray(1024)
            var bytes: Int
            while (true) {
                try {
                    bytes = mmInStream!!.available()
                    if (bytes != 0) {
                        SystemClock.sleep(500)
                        bytes = mmInStream.available()
                        bytes = mmInStream.read(buffer, 0, bytes)
                        mBluetoothHandler!!.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer).sendToTarget()
                    }
                } catch (e: IOException) {
                    break
                }
            }
        }

        fun write(str: String) {
            val bytes = str.toByteArray()
            try {
                mmOutStream!!.write(bytes)
            } catch (e: IOException) {
                Toast.makeText(applicationContext, "데이터 전송 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        fun cancel() {
            try {
                mmSocket!!.close()
            } catch (e: IOException) {
                Toast.makeText(applicationContext, "소켓 해제 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        init {
            var tmpIn: InputStream? = null
            var tmpOut: OutputStream? = null
            try {
                tmpIn = mmSocket!!.inputStream
                tmpOut = mmSocket.outputStream
            } catch (e: IOException) {
                Toast.makeText(applicationContext, "소켓 연결 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
            mmInStream = tmpIn
            mmOutStream = tmpOut
        }
    }
    fun getPatientInfo(mac : String){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.65:80/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RetrofitInterface::class.java)
        var sendmac = HashMap<String, String>()
        sendmac.put("mac", mac)

        service.getPatientInfo(sendmac).enqueue(object : Callback<List<PatientData>> {
            override fun onFailure(call: Call<List<PatientData>>, t: Throwable) {
                Log.d("CometChatAPI::", "Failed API call with call: ${call} exception: ${t}")
            }

            override fun onResponse(call: Call<List<PatientData>>,response: Response<List<PatientData>>) {
                try{
                    Log.e("get",response.body()!!.size.toString())
                    for(i in 0..response.body()!!.size-1){
                        //Log.e("sfdsfsdfdsfsdfdsfsfsdfd", response.body()!!.get(i).name)
                        patientname.setText(response.body()!!.get(i).name)
                        disease_name_txt.setText(response.body()!!.get(i).disease_name)
                        guardian_name_txt.setText(response.body()!!.get(i).guardian)
                        family_doctor_txt.setText(response.body()!!.get(i).family_doctor)

                    }
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }

        })
    }
}


