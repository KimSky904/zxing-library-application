package com.cloud.zxing_library_application

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.BeepManager
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import java.util.*


class ContinuousCaptureActivity : AppCompatActivity() {
    private var barcodeView: DecoratedBarcodeView? = null
    private var beepManager: BeepManager? = null
    private var lastText: String? = null

    private val callback: BarcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            if (result.text == null || result.text == lastText) {
                // Prevent duplicate scans
                return
            }
            lastText = result.text
            barcodeView!!.setStatusText(result.text)
            beepManager!!.playBeepSoundAndVibrate()

            //Added preview of scanned barcode
            val imageView: ImageView = findViewById(R.id.barcodePreview)
            imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW))
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.continuous_scan)
        barcodeView = findViewById(R.id.barcode_scanner)
        val formats: Collection<BarcodeFormat> =
            listOf(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39)
        barcodeView?.barcodeView?.decoderFactory = DefaultDecoderFactory(formats)
        barcodeView?.initializeFromIntent(intent)
        barcodeView?.decodeContinuous(callback)
        beepManager = BeepManager(this)
    }

    override fun onResume() {
        super.onResume()
        barcodeView!!.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView!!.pause()
    }

    fun pause(view: View?) {
        barcodeView!!.pause()
    }

    fun resume(view: View?) {
        barcodeView!!.resume()
    }

    fun triggerScan(view: View?) {
        barcodeView!!.decodeSingle(callback)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return barcodeView!!.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }
}