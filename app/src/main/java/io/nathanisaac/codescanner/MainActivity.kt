package io.nathanisaac.codescanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.common.FirebaseVisionImage

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textValue)
        val readCodeButton = findViewById<Button>(R.id.getCode)
        val barcodeImage = findViewById<ImageView>(R.id.barcodeImage)

        FirebaseApp.initializeApp(this)

        val imageBitmap: Bitmap = BitmapFactory.decodeResource(this.resources, R.drawable.barcode_generator)
        val image: FirebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap)

        val codeReader: CodeReader = CodeReader()

        readCodeButton.setOnClickListener {
//            codeReader.getImageBarcodes(image) { codes ->
//                for (code in codes) {
//                    val bounds = code.boundingBox
//                    val corners = code.cornerPoints
//                    val rawValue = code.rawValue
//                    val valueType = code.valueType
//                    if (bounds !== null) {
//                        val imageCopy = imageBitmap.copy(Bitmap.Config.ARGB_8888, true)
//                        val canvas = Canvas(imageCopy)
//                        val paint = Paint()
//                        val rectangle = Rect(bounds.left, bounds.top, bounds.right, bounds.bottom)
//
//                        paint.color = Color.RED
//                        paint.style = Paint.Style.STROKE
//                        paint.strokeWidth = 10.toFloat()
//
//                        canvas.drawRect(rectangle, paint)
//
//                        barcodeImage.setImageBitmap(imageCopy)
//                    }

//                    textView.text = code.rawValue.toString()
//                }
//            }

            dispatchTakePictureIntent()


//            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                showCameraPerview()
//            } else {
//                // Camera permission has not been granted.
//
//                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
//                    Toast.makeText(this, "Camera permission is needed to show the camera preview.", Toast.LENGTH_SHORT).show()
//                }
//
//                requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA)
//            }


        }
    }

    val REQUEST_IMAGE_CAPTURE = 1

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val codeReader: CodeReader = CodeReader()
        val barcodeImage = findViewById<ImageView>(R.id.barcodeImage)
        val textView = findViewById<TextView>(R.id.textValue)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            barcodeImage.setImageBitmap(imageBitmap)
            val image: FirebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap)

            codeReader.getImageBarcodes(image) { codes ->
                for (code in codes) {
//                    val bounds = code.boundingBox
//                    val corners = code.cornerPoints
//                    val rawValue = code.rawValue
//                    val valueType = code.valueType
//                    if (bounds !== null) {
//                        val imageCopy = imageBitmap.copy(Bitmap.Config.ARGB_8888, true)
//                        val canvas = Canvas(imageCopy)
//                        val paint = Paint()
//                        val rectangle = Rect(bounds.left, bounds.top, bounds.right, bounds.bottom)
//
//                        paint.color = Color.RED
//                        paint.style = Paint.Style.STROKE
//                        paint.strokeWidth = 10.toFloat()
//
//                        canvas.drawRect(rectangle, paint)
//
//                        barcodeImage.setImageBitmap(imageCopy)
//                    }

                    textView.text = code.rawValue.toString()
                }
            }
        }
    }
}
