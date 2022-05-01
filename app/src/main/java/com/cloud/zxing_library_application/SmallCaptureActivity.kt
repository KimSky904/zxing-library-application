package com.cloud.zxing_library_application


import android.view.View
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.DecoratedBarcodeView


class SmallCaptureActivity : CaptureActivity()  {
    override fun initializeContent(): DecoratedBarcodeView? {
        setContentView(R.layout.capture_small)
        return findViewById<View>(R.id.zxing_barcode_scanner) as DecoratedBarcodeView
    }
}