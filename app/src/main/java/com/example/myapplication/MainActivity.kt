package com.example.myapplication

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavigationHost(navController)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf("dashboard", "scanner")
    val icons = listOf(Icons.Default.Dashboard, Icons.Default.QrCodeScanner)
    val labels = listOf("Dashboard", "Scanner")

    val currentRoute = navController.currentBackStackEntryAsState()

    NavigationBar {
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                selected = currentRoute.value?.destination?.route == screen,
                onClick = { navController.navigate(screen) },
                icon = { Icon(icons[index], contentDescription = null) },
                label = { Text(labels[index]) }
            )
        }
    }
}


@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController, startDestination = "dashboard") {
        composable("dashboard") { DashboardScreen() }
        composable("scanner") { ScannerScreen() }
    }
}

@Composable
fun DashboardScreen() {
    val qrContent = "https://your-event-link.com"
    val qrBitmap = generateQrCode(qrContent)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Core Dashboard",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            modifier = Modifier
                .background(Color(0xFF1E3A8A))
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Your Event QR Code",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        qrBitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "QR Code",
                modifier = Modifier.size(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "NOTE: You're whitelisted to scan QR codes.",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Blue,
            modifier = Modifier
                .background(Color(0xFFD6F0FF))
                .padding(12.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Button(onClick = { }) {
                Text("Check-In")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { }) {
                Text("Check-Out")
            }
        }
    }
}

fun generateQrCode(content: String): Bitmap? {
    return try {
        val bitMatrix = MultiFormatWriter().encode(
            content,
            BarcodeFormat.QR_CODE,
            400, 400
        )
        val barcodeEncoder = BarcodeEncoder()
        barcodeEncoder.createBitmap(bitMatrix)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
@Composable
fun ScannerScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Scanner screen will launch camera soon!",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.DarkGray
        )
    }
}
