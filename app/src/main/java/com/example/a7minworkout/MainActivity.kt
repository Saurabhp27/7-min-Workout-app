package com.example.a7minworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.example.a7minworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding?.root)

//        val start_btn : FrameLayout = findViewById(R.id.flStart)

        binding?.flStart?.setOnClickListener(){
            val intent = Intent(this, Exersice ::class.java)
            startActivity(intent)
        }

        binding?.flBMI?.setOnClickListener {
            // Launching the BMI Activity
            val intent = Intent(this, BmiActivity::class.java)
            startActivity(intent)
        }

        binding?.flhistory?.setOnClickListener {
            // Launching the History Activity
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}