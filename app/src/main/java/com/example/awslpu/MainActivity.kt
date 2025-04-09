package com.example.myapplication

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.awslpu.LoginScreen
import com.example.awslpu.ui.theme.MyApplicationTheme
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AppEntry()
            }
        }
    }
}

@Composable
fun AppEntry() {
    var isLoggedIn by remember { mutableStateOf(false) }

    if (!isLoggedIn) {
        LoginScreen(
            onLoginClick = { email, pass ->
                Log.d("Login", "Email: $email, Password: $pass")
                // You can add validation here if needed
                isLoggedIn = true
            },
            onForgotPasswordClick = {
                Log.d("Login", "Forgot password clicked")
            }
        )
    } else {
        MainScreen()
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
        composable("dashboard") { DashboardScreen(userName = "Anubhav Jaiswal") }
        composable("scanner") { ScannerScreen() }
    }
}

@Composable
fun DashboardScreen(userName: String) {
    val qrContent = "https://your-event-link.com"
    val qrBitmap = generateQrCode(qrContent)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logged-in badge
        Text(
            text = "Logged in as $userName (Core Team)",
            modifier = Modifier
                .padding(top = 16.dp)
                .background(Color(0xFFFFF3CD), shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            color = Color(0xFFB94700)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Welcome card
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E3A8A)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Core Dashboard", style = MaterialTheme.typography.headlineSmall, color = Color.White)
                Text("Hey! $userName", style = MaterialTheme.typography.titleMedium, color = Color.White)
                Text("Welcome to the AWS Cloud Club LPU Admin Dashboard.", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // QR Code Card
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Your Event QR Code", style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(12.dp))

                qrBitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "QR Code",
                        modifier = Modifier.size(200.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Note section
                Row(
                    modifier = Modifier
                        .background(Color(0xFFD6F0FF), shape = RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Info, contentDescription = "Info", tint = Color(0xFF007BFF))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("NOTE: You're whitelisted to scan QR codes for other members.", color = Color(0xFF007BFF))
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Buttons
                Row {
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A8A))
                    ) {
                        Text("Check-In", color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A8A))
                    ) {
                        Text("Check-Out", color = Color.White)
                    }
                }
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
