package io.nathanisaac.codescanner

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.common.FirebaseVisionImage

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        val textView = findViewById<TextView>(R.id.textValue)
        val readCodeButton = findViewById<Button>(R.id.getCode)

        val imageBitmap = BitmapFactory.decodeResource(this.resources, R.drawable.barcode_generator)
        val image: FirebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap)

        val codeReader: CodeReader = CodeReader()

        readCodeButton.setOnClickListener {
            codeReader.getImageBarcodes(image) { codes ->
                for (code in codes) {
                    textView.text = code
                }
            }
        }
    }
}
