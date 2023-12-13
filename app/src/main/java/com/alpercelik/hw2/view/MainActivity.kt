package com.alpercelik.hw2.view

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import com.alpercelik.hw2.R
import com.alpercelik.hw2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {
    lateinit var binding: ActivityMainBinding

    val TAG:String="GESTURE"
    var gDetector: GestureDetectorCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root

        supportActionBar?.hide() // Hiding title bar

        setContentView(view)

        @Suppress("DEPRECATION") // for using deprecated codes without any highlights or errors
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) // Hiding the status bar
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) // Locking the orientation to Portrait

        // ANIMATIONS
        val colorBlinkAnimation = AnimationUtils.loadAnimation(this, R.anim.color_blink_animation) // Load the color blink animation from the XML resource
        binding.appTitle.startAnimation(colorBlinkAnimation)

        // Gesture detection
        gDetector = GestureDetectorCompat(this, this)
        gDetector?.setOnDoubleTapListener(this)

        // long-press gesture
        binding.appTitle.setOnLongClickListener {

            Toast.makeText(this, "Long press gesture detected on the title", Toast.LENGTH_SHORT).show()
            true
        }

        binding.btnStart.setOnClickListener {
            val maxAttempts = 1
            var attempts = 0
            while (attempts < maxAttempts) {
                val userInput = binding.inputText.text.toString().trim()

                if (userInput.equals("Ye", ignoreCase = true)) {
                    Toast.makeText(this, "LET'S ROLL!", Toast.LENGTH_SHORT).show()
                    break
                } else {
                    attempts++
                    Toast.makeText(
                        this,
                        "WRONG! Try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Clear the input text for a new attempt
                    binding.inputText.text = null
                }
            }

            if (attempts == maxAttempts) {
                //Toast.makeText(this, "You've reached the maximum attempts.", Toast.LENGTH_SHORT).show()
            } else {
                val switchActivityIntent = Intent(this, PlaylistActivity::class.java)
                startActivity(switchActivityIntent)
            }
        }


    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gDetector?.onTouchEvent(event)
        Log.i("GESTURE","onTouchEvent ${event.action}")
        //Toast.makeText(this, "Someone is touching to the app :)", Toast.LENGTH_SHORT).show()
        return super.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent): Boolean {
        //Log.i(TAG, "onDown")
        return true
    }

    override fun onShowPress(e: MotionEvent) {
        //Log.i(TAG, "onShowPress")
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        //Log.i(TAG, "onSingleTapUp")
        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        TODO("Not yet implemented")
    }


    override fun onLongPress(e: MotionEvent) {
        Log.i(TAG, "onLongPress")
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        //Log.i(TAG, "onSingleTapConfirmed")
        return true
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        //Log.i(TAG, "onDoubleTap")
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        //Log.i(TAG, "onDoubleTapEvent")
        return true
    }
}