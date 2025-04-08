package com.example.myapplication  // ✅ Keep your actual package name

import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                ScannerScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScannerScreen() {
    var result by remember { mutableStateOf("Scanned result will appear here") }

    // Launcher for the barcode scanner
    val scannerLauncher = rememberLauncherForActivityResult(contract = ScanContract()) { scanResult ->
        if (scanResult.contents != null) {
            result = "Result: ${scanResult.contents}"
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Simple Scanner App") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                val options = ScanOptions().apply {
                    setPrompt("Scan a barcode or QR code")
                    setBeepEnabled(true)
                    setOrientationLocked(true)
                }
                scannerLauncher.launch(options)
            }) {
                Text("Scan Code")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = result)
        }
    }
}
