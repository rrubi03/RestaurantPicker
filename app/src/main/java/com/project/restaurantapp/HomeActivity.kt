package com.project.restaurantapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_activities)
        supportActionBar?.hide()
        // 3 second delay for regular screen to appear
        Handler().postDelayed({
            val intent = Intent(this@HomeActivity, HomeActivities::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
// change xml file to have the splash page on the blank one
// not the one with the buttons