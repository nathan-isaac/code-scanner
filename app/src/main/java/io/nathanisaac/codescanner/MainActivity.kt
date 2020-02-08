package io.nathanisaac.codescanner

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        val textView = findViewById<TextView>(R.id.textValue)
        val readCodeButton = findViewById<Button>(R.id.getCode)

        val image = imageFromPath(this, Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.resources.getResourcePackageName(R.drawable.barcode_generator) + "/" + this.resources.getResourceTypeName(R.drawable.barcode_generator) + "/" + this.resources.getResourceEntryName((R.drawable.barcode_generator))))


        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_CODE_128)
            .build()

        val detector = FirebaseVision.getInstance()
            .getVisionBarcodeDetector(options)

        val result = detector.detectInImage(image)
            .addOnSuccessListener { barcodes ->
                // Task completed successfully
                // ...
                for (barcode in barcodes) {
                    val bounds = barcode.boundingBox
                    val corners = barcode.cornerPoints

                    val rawValue = barcode.rawValue
                    textView.text = rawValue.toString()

                    val valueType = barcode.valueType
                }
            }
            .addOnFailureListener {
                // Task failed with an exception
                // ...
                val message = "failure"
            }

        readCodeButton.setOnClickListener {
            textView.text = "Button Click"
        }
    }

    protected fun imageFromPath(context: Context, uri: Uri): FirebaseVisionImage {
        val image: FirebaseVisionImage
        try {
            image = FirebaseVisionImage.fromFilePath(context, uri)
            return image
        } catch (e: IOException) {
            e.printStackTrace()
            throw e
        }
    }
}
