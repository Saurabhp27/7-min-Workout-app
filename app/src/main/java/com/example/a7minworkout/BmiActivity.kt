package com.example.a7minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minworkout.databinding.ActivityBmiBinding

class BmiActivity : AppCompatActivity() {
    private var binding: ActivityBmiBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBmiBinding.inflate(layoutInflater)
        //Todo 5 connect the layout to this activity
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //set back button
        supportActionBar?.title = "CALCULATE BMI" // Setting a title in the action bar.
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculateUnits?.setOnClickListener(){
            calculatebmi()
        }
    }

    private fun calculatebmi(){
        if (validate()){
            // Remaining functionalities of rounding off decimal(setscale) & display description not added
            var weight : Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
            var height : Float = binding?.etMetricUnitHeight?.text.toString().toFloat()
            var Bmivalue : Float = weight/ (height * height)
            binding?.llDiplayBMIResult?.visibility = View.VISIBLE
            binding?.tvBMIValue?.text = Bmivalue.toString()
        }
        else{
            Toast.makeText(this, "invalid weight or height", Toast.LENGTH_SHORT).show()
        }
    }
    private fun validate () : Boolean {
        if (binding?.etMetricUnitHeight.toString().isEmpty()){
            return false
        }
        if (binding?.etMetricUnitWeight.toString().isEmpty()){
            return false
        }
            return true
    }




}
