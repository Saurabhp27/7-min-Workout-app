package com.example.a7minworkout

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minworkout.databinding.ActivityExersiceBinding
import java.util.*
import kotlin.collections.ArrayList

class Exersice : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExersiceBinding? = null

    private var restTimer : CountDownTimer? = null
    private var restprogress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var ExerciseList : ArrayList<ExerciseModel>? = null
    private var currentExersiceposition = -1

    private var tts : TextToSpeech? = null
    private var player :MediaPlayer? = null

    private var exerciseAdapter : ExerciseStatusAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExersiceBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExersice)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        ExerciseList = Constant.defaultExerciseList()


        binding?.toolbarExersice?.setNavigationOnClickListener(){
            onBackPressed()
            finish()
        }
        tts = TextToSpeech(this, this)
        setupRestview()
        setUpExerciseStatusRecyclerView()
    }


    private fun setUpExerciseStatusRecyclerView (){
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager (this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(ExerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }


    private fun setupRestview (){

        try {
            val soundURI =
                Uri.parse("android.resource://com.example.a7minworkout/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false // Sets the player to be looping or non-looping.
            player?.start() // Starts Playback.
        } catch (e: Exception) {
            e.printStackTrace()
        }


        binding?.flprogressbar?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility =View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE

        binding?.upcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE


        if (restTimer != null){
            restTimer?.cancel()
            restprogress = 0
        }
        binding?.tvUpcomingExerciseName?.text = ExerciseList!![currentExersiceposition +1].getName()
        SetRestProgressBar()
    }

    private fun SetRestProgressBar (){
        binding?.progressBar?.progress= restprogress

        restTimer = object : CountDownTimer(5000, 1000){
            override fun onTick(millisUntilFinished: Long) {

                restprogress++
                binding?.progressBar?.progress = 10 - restprogress
                binding?.tvTimer?.text = (10 - restprogress).toString()

            }

            override fun onFinish() {

                currentExersiceposition++

                ExerciseList!![currentExersiceposition].setIsSelected(true) // Current Item is selected
                exerciseAdapter!!.notifyDataSetChanged()

                setupExerciseView()
            }
        }.start()
    }

    private fun setupExerciseView() {

        speakOut(ExerciseList!![currentExersiceposition].getName())

        // Here according to the view make it visible as this is Exercise View so exercise view is visible and rest view is not.
        binding?.flprogressbar?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility =View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE

        binding?.tvExerciseName?.text = ExerciseList!![currentExersiceposition].getName()
        binding?.ivImage?.setImageResource(ExerciseList!![currentExersiceposition].getImage())

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        speakOut(ExerciseList!![currentExersiceposition].getName())

        binding?.upcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE


        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {

        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++

                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                if (currentExersiceposition < ExerciseList!!.size -1){
                    ExerciseList!![currentExersiceposition].setIsSelected(false) // exercise is completed so selection is set to false
                    ExerciseList!![currentExersiceposition].setIsCompleted(true) // updating in the list that this exercise is completed
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestview()
                }
                else{
                    finish()
                        val intent = Intent(this@Exersice,FinishActivity::class.java)
                        startActivity(intent)
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null

        if (restTimer != null){
            restTimer?.cancel()
            restprogress = 0
        }
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player != null){
            player!!.stop()
        }


    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }


    private fun speakOut(text: String) {
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null, "")
    }
}
