package com.cloud.zxing_library_application

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView


class ToolbarCaptureActivity : AppCompatActivity() {
    private var capture: CaptureManager? = null
    private var barcodeScannerView: DecoratedBarcodeView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.capture_appcompat)
        val toolbar: Toolbar = findViewById(R.id.my_awesome_toolbar)
        toolbar.setTitle("Scan Barcode")
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner)
        capture = CaptureManager(this, barcodeScannerView)
        capture!!.initializeFromIntent(intent, savedInstanceState)
        capture!!.decode()
    }

    override fun onResume() {
        super.onResume()
        capture!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture!!.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture!!.onSaveInstanceState(outState)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return barcodeScannerView!!.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }
}