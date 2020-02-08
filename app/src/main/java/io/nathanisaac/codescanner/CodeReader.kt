package io.nathanisaac.codescanner

import android.media.Image
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage

class CodeReader {
    private val detector: FirebaseVisionBarcodeDetector

    constructor() {
        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_CODE_128)
            .build()

        detector = FirebaseVision.getInstance()
            .getVisionBarcodeDetector(options)
    }

    public fun getImageBarcodes(image: FirebaseVisionImage, updateCodes: (ArrayList<String>) -> Unit) {

        val result = detector.detectInImage(image)
            .addOnSuccessListener { barcodes ->
                val codes = arrayListOf<String>()

                for (barcode in barcodes) {
                    val bounds = barcode.boundingBox
                    val corners = barcode.cornerPoints
                    val rawValue = barcode.rawValue
                    val valueType = barcode.valueType

                    codes.add(rawValue.toString())
                }

                updateCodes(codes)
            }
            .addOnFailureListener {exception ->
                val message = "failure"
            }
    }
}