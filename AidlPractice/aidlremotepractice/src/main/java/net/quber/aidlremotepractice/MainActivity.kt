package net.quber.aidlremotepractice

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import net.quber.aidlpractice.ICalc

class MainActivity : Activity() {
    private val SERVER_ACTION = "net.quber.minsub.CALC"
    private val SERVER_PACKAGE = "net.quber.aidlpractice"

    private var myService: ICalc? = null
    private lateinit var edit1: EditText
    private lateinit var edit2: EditText
    private lateinit var btn1: Button
    private lateinit var btn2: Button

    private val conn = object: ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            myService = null
        }

        override fun onServiceConnected(p0: ComponentName?, iBinder: IBinder?) {
            myService = ICalc.Stub.asInterface(iBinder)
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edit1 = main_activity_edit1
        edit2 = main_activity_edit2
        btn1 = main_activity_btn1
        btn2 = main_activity_btn2

        val intent = Intent(SERVER_ACTION)
        intent.setPackage(SERVER_PACKAGE)
        bindService(intent, conn, Context.BIND_AUTO_CREATE)

        btn1.setOnClickListener {
            if (myService != null) {
                if (edit1.text.toString().length > 0 &&
                        edit2.text.toString().length > 0) {
                    Toast.makeText(applicationContext,
                            myService!!.getLCM(
                                    edit1.text.toString().toInt(),
                                    edit2.text.toString().toInt()
                            ).toString(),
                            Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(applicationContext,
                            "Enter the number!",
                            Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext,
                        "Not Connected with Service!",
                        Toast.LENGTH_SHORT).show()
            }
        }

        btn2.setOnClickListener {
            if (myService != null) {
                if (edit1.text.toString().length > 0 &&
                        edit2.text.toString().length > 0) {
                    Toast.makeText(applicationContext,
                            myService!!.isPrime(
                                    edit1.text.toString().toInt()
                            ).toString(),
                            Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext,
                            "Enter the number!",
                            Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext,
                        "Not Connected with Service!",
                        Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(conn)
    }
}
