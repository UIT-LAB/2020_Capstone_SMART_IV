package com.deuksoft.smartiv

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dinuscxj.progressbar.CircleProgressBar
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
    val BT_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    val BT_MESSAGE_READ = 2
    val BT_CONNECTING_STATUS = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var healthLV : CircleProgressBar = findViewById(R.id.healthLV)
        if(intent.hasExtra("PatientBluetooth")){
            mBluetoothDevice = intent.getParcelableExtra("PatientBluetooth")
            try {
                mBluetoothSocket = mBluetoothDevice!!.createInsecureRfcommSocketToServiceRecord(BT_UUID)
                mBluetoothSocket!!.connect()
                mThreadConnectedBluetooth = ConnectedBluetoothThread(mBluetoothSocket)
                mThreadConnectedBluetooth!!.start()
                mBluetoothHandler!!.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget()
            }catch (e: IOException){
                Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }

        }

        mBluetoothHandler = object : Handler() {
            override fun handleMessage(msg: Message) {
                if (msg.what == BT_MESSAGE_READ) {
                    var readMessage: String? = null
                    try {
                        readMessage = String((msg.obj as ByteArray), charset("UTF-8"))
                        Log.e("readMessage", readMessage)
                        //여기에 리시브 받는거 해줘야 할듯!!!!!!!
                    } catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        healthLV.progress = 108
        healthLV.max = 150
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
                        SystemClock.sleep(100)
                        bytes = mmInStream.available()
                        bytes = mmInStream.read(buffer, 0, bytes)
                        mBluetoothHandler!!.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget()
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
}


