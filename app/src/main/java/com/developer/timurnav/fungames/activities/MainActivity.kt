package com.developer.timurnav.fungames.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.developer.timurnav.fungames.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun play(view: View) {
        startActivity(Intent(this@MainActivity, Game2048Activity::class.java))
    }
}
