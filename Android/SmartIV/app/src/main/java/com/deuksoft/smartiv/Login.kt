package com.deuksoft.smartiv

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.HashMap


class Login : AppCompatActivity(), View.OnClickListener {

    var mBluetoothAdapter: BluetoothAdapter? = null
    var mPairedDevices: Set<BluetoothDevice>? = null
    var mBluetoothDevice: BluetoothDevice? = null
    val BT_REQUEST_ENABLE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        var logo : ImageView = findViewById(R.id.logo)
        var id_txt: TextView = findViewById(R.id.id_txt)
        var pw_txt: TextView = findViewById(R.id.pw_txt)
        var qrbtn : Button = findViewById(R.id.qrbtn)
        var loginbtn : Button = findViewById(R.id.loginbtn)

        qrbtn.setOnClickListener(this)
        loginbtn.setOnClickListener(this)
        logo.setOnClickListener(this)
    }

    override fun onClick(b: View?) {
        when(b!!.id){
            R.id.qrbtn -> {
                if (mBluetoothAdapter!!.isEnabled) {
                    val intentIntegrator = IntentIntegrator(this)
                    intentIntegrator.setBeepEnabled(false) //바코드 인식시 소리
                    intentIntegrator.setOrientationLocked(false)
                    intentIntegrator.setPrompt("")
                    intentIntegrator.setCameraId(1)
                    intentIntegrator.initiateScan()
                } else {
                    Toast.makeText(getApplicationContext(), "블루투스를 활성화 해주세요", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.loginbtn -> {
                /*val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()*/
                //getPatientInfo("74:9E:F5:81:8F:49")
                //callDoctor("74:9E:F5:81:8F:49")
            }
            R.id.logo -> {
                bluetoothOn()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            BT_REQUEST_ENABLE -> {
                if (resultCode == RESULT_OK) { // 블루투스 활성화를 확인을 클릭하였다면
                    Toast.makeText(getApplicationContext(), "블루투스 활성화", Toast.LENGTH_LONG).show();
                } else if (resultCode == RESULT_CANCELED) { // 블루투스 활성화를 취소를 클릭하였다면
                    Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_LONG).show();
                }
            }
            else ->{
                var scanningResult : IntentResult = IntentIntegrator.parseActivityResult(
                    requestCode,
                    resultCode,
                    data
                )

                if(scanningResult != null){
                    var contents : String = scanningResult.contents
                    var format : String = scanningResult.formatName
                    mPairedDevices = mBluetoothAdapter!!.getBondedDevices()
                    //Toast.makeText(this, "$contents, $format", Toast.LENGTH_LONG).show()
                   if(mPairedDevices!!.size > 0){
                        for(tempDevice in mPairedDevices!!) {
                            Log.e("addr : ", tempDevice.name)
                            if (contents.equals(tempDevice.name)) {
                                mBluetoothDevice = tempDevice
                                Toast.makeText(this, "$contents, $format", Toast.LENGTH_LONG).show()
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra("PatientBluetooth", mBluetoothDevice)
                                startActivity(intent)
                                //finish()
                                break
                            }
                        }
                    }else{
                       Toast.makeText(this, "일치하는 블루투스가 없습니다.", Toast.LENGTH_SHORT).show()
                   }

                }
            }
        }
    }

    fun bluetoothOn() {
        if (mBluetoothAdapter == null) {
            Toast.makeText(applicationContext, "블루투스를 지원하지 않는 기기입니다.", Toast.LENGTH_LONG).show()
        } else {
            if (mBluetoothAdapter!!.isEnabled) {
                Toast.makeText(applicationContext, "블루투스가 이미 활성화 되어 있습니다.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "블루투스가 활성화 되어 있지 않습니다.", Toast.LENGTH_LONG).show()
                val intentBluetoothEnable = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE)
            }
        }
    }




}