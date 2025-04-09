package com.example.awslpu.ui.theme


import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class ScanContract : ActivityResultContract<ScanOptions, ScanIntentResult>() {
    override fun createIntent(context: Context, input: ScanOptions): Intent {
        return ScanContract().createIntent(context, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ScanIntentResult {
        return ScanContract().parseResult(resultCode, intent)
    }
}
