package net.quber.aidlpractice.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import net.quber.aidlpractice.ICalc

class MyService : Service() {
    private val iCalcBinder = object : ICalc.Stub() {
        override fun getLCM(a: Int, b: Int): Int {
            var i = 1;
            while (!(i % a == 0 && i % b == 0)) {
                i += 1;
            }
            return i
        }

        override fun isPrime(n: Int): Boolean {
            var i : Int
            for (i in 2..n-1) {
                if (n % i == 0)
                    return false
            }
            return true
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return iCalcBinder
    }
}
