package io.nathanisaac.codescanner

import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
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
        val barcodeImage = findViewById<ImageView>(R.id.barcodeImage)

        val imageBitmap: Bitmap = BitmapFactory.decodeResource(this.resources, R.drawable.barcode_generator)
        val image: FirebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap)

        val codeReader: CodeReader = CodeReader()

        readCodeButton.setOnClickListener {
            codeReader.getImageBarcodes(image) { codes ->
                for (code in codes) {
                    val bounds = code.boundingBox
                    val corners = code.cornerPoints
                    val rawValue = code.rawValue
                    val valueType = code.valueType
                    if (bounds !== null) {
                        val imageCopy = imageBitmap.copy(Bitmap.Config.ARGB_8888, true)
                        val canvas = Canvas(imageCopy)
                        val paint = Paint()
                        val rectangle = Rect(bounds.left, bounds.top, bounds.right, bounds.bottom)

                        paint.color = Color.RED
                        paint.style = Paint.Style.STROKE
                        paint.strokeWidth = 10.toFloat()

                        canvas.drawRect(rectangle, paint)

                        barcodeImage.setImageBitmap(imageCopy)
                    }

                    textView.text = code.rawValue.toString()
                }
            }
        }
    }
}
