package io.nathanisaac.codescanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textValue)
        val readCodeButton = findViewById<Button>(R.id.getCode)

        textView.text = "this is a test"

        readCodeButton.setOnClickListener {
            textView.text = "Button Click"
        }
    }
}
