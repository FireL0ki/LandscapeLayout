package com.example.landscapelayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast


// create key in SquareActivity so that it can send data back (if square was tapped or not boolean)
// back to mainActivity
const val EXTRA_TAPPED_INSIDE_SQUARE = "com.example.landscapelayout.TAPPED_INSIDE_SQUARE"



class SquareActivity : AppCompatActivity() {

    private lateinit var squareImage: ImageView
    private lateinit var container: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_square)

        squareImage = findViewById(R.id.square)
        container = findViewById(R.id.container)

        var squareSize = intent.getIntExtra(EXTRA_SQUARE_SIZE, 100) // needs default value

        if (squareSize == 0) {
            squareSize = 1
        }

        squareImage.layoutParams.width = squareSize
        squareImage.layoutParams.height = squareSize


        // event listener for when square is clicked
        squareImage.setOnClickListener {
            squareTapped(true)
        }

        container.setOnClickListener {
            squareTapped(false)
        }

    }

    private fun squareTapped(didTapSquare: Boolean) {
//        // send message back to main activity with boolean value
//        // end square activity
////        Toast.makeText(this, "square tapped $didTapSquare", Toast.LENGTH_SHORT).show()
//        // create intent to carry the data back to MainActivity
//        val resultIntent = Intent()
//        resultIntent.putExtra(EXTRA_TAPPED_INSIDE_SQUARE, didTapSquare)
//        setResult(RESULT_OK, resultIntent)
//        finish() // means end this activity

        // another cleaner way to write:
        Intent().apply {
            putExtra(EXTRA_TAPPED_INSIDE_SQUARE, didTapSquare)
            setResult(RESULT_OK, this)
            finish()
        }

    }


}