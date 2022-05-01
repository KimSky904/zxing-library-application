package com.cloud.zxing_library_application

import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.ViewfinderView
import java.util.*


class CustomScannerActivity : Activity(), DecoratedBarcodeView.TorchListener {
    private var capture: CaptureManager? = null
    private var barcodeScannerView: DecoratedBarcodeView? = null
    private var switchFlashlightButton: Button? = null
    private var viewfinderView: ViewfinderView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_scanner)
        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner)
        barcodeScannerView!!.setTorchListener(this)
        switchFlashlightButton = findViewById(R.id.switch_flashlight)
        viewfinderView = findViewById(R.id.zxing_viewfinder_view)

        // if the device does not have flashlight in its camera,
        // then remove the switch flashlight button...
        if (!hasFlash()) {
            switchFlashlightButton?.visibility = View.GONE
        }
        capture = CaptureManager(this, barcodeScannerView)
        capture!!.initializeFromIntent(getIntent(), savedInstanceState)
        capture!!.setShowMissingCameraPermissionDialog(false)
        capture!!.decode()
        changeMaskColor(null)
        changeLaserVisibility(true)
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return barcodeScannerView!!.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    /**
     * Check if the device's camera has a Flashlight.
     * @return true if there is Flashlight, otherwise false.
     */
    private fun hasFlash(): Boolean {
        return applicationContext.packageManager
            .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    fun switchFlashlight(view: View?) {
        if ("플래시 켜짐" == switchFlashlightButton?.text ) {
            barcodeScannerView!!.setTorchOn()
        } else {
            barcodeScannerView!!.setTorchOff()
        }
    }

    fun changeMaskColor(view: View?) {
        val rnd = Random()
        val color: Int = Color.argb(100, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        viewfinderView!!.setMaskColor(color)
    }

    fun changeLaserVisibility(visible: Boolean) {
        viewfinderView!!.setLaserVisibility(visible)
    }

    override fun onTorchOn() {
        switchFlashlightButton?.text = "플래시 꺼짐"
    }

    override fun onTorchOff() {
        switchFlashlightButton?.text = "플래시 켜짐"
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        capture!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}