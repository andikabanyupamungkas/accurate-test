package com.example.accuratetask.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.accuratetask.R
import com.example.accuratetask.ui.home.HomeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //simulate splash screen delay
        Handler(Looper.getMainLooper()).postDelayed({
            gotoHomeActivity()
        }, 3000)
    }

    private fun gotoHomeActivity() {
        startActivity(Intent(this@MainActivity, HomeActivity::class.java))
        finish()
    }

}