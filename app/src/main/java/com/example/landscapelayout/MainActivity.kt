package com.example.landscapelayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

// create an 'extra' -- a way to move data between activities
// it needs a unique key (the string we set it equal to)
const val EXTRA_SQUARE_SIZE = "com.example.landscapelayout.tap_the_square.SQUARE_SIZE"

class MainActivity : AppCompatActivity() {

    private lateinit var seekBar: SeekBar
    private lateinit var seekBarLabel: TextView
    private lateinit var showSquareButton: Button

    private val squareResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result -> handleSquareResult(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar = findViewById(R.id.seek_bar)
        seekBarLabel = findViewById(R.id.seek_bar_label)
        showSquareButton = findViewById(R.id.show_square_button)

        val initialProgress = seekBar.progress
        updateLabel(initialProgress)

        // TODO add listener to update label as seekbar changes
        // seekbar change listener

        seekBar.setOnSeekBarChangeListener( object: SeekBar.OnSeekBarChangeListener {
            // p0 -- seekbarComponent, p1 -- progress, p2 -- fromUser
            override fun onProgressChanged(seebarComponent: SeekBar?, progress: Int, fromUser: Boolean) {
                updateLabel(progress)
            }

            // need these, but don't need to have anything in them
            // our app doesn't need to do anything specific when use starts/stops using seekbar
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        showSquareButton.setOnClickListener {
            showSquare()
        }

    }

    private fun updateLabel(progress: Int) {
        seekBarLabel.text = getString(R.string.seekbar_value_message, progress)
    }

    private fun showSquare() {
        // launch the SquareActivity -- use intent, sent to Android system,
        // it will check which activity, make sure it's allowed, etc. Then Android system
        // will close/end mainActivity and run the new activity
        // all activities extend the android activity class (which is a java class)
        // asking the android system to make the activity
        // 'this' refers to the current object, the instance of mainActivity
        val showSquareIntent = Intent(this, SquareActivity::class.java)

        // this function takes a key (the key we built above as a global variable) and a value
        // our value is the progress of the seekbar, which we pass to the show square activity
        // so it can set the size of the square
        showSquareIntent.putExtra(EXTRA_SQUARE_SIZE, seekBar.progress)
//        startActivity(showSquareIntent)
        squareResultLauncher.launch(showSquareIntent)

        // Tell the SquareActivity how large the square should be
        // based on the progress of the Seekbar
    }

    private fun handleSquareResult(result: ActivityResult) {
        // display result to user
        if (result.resultCode == RESULT_OK) {
            val intent = result.data
            // elvis operator for non nullable type
            val tapped = intent?.getBooleanExtra(EXTRA_TAPPED_INSIDE_SQUARE, false) ?: false

            // using if as an expression to assign a value to the message variable
            val message = if (tapped) {
                getString(R.string.tapped_square_message)
            } else {
                getString(R.string.you_missed_message)
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

            // adding on to test what happens if use exits / hits back button without tapping screen
        } else if (result.resultCode == RESULT_CANCELED) {
            Toast.makeText(this, getString(R.string.no_taps_message), Toast.LENGTH_SHORT).show()
        }
    }

}