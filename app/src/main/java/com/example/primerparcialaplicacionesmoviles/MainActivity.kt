package com.example.primerparcialaplicacionesmoviles
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WalletApp()
        }
    }
}

@Composable
fun WalletApp() {
    var balance by remember { mutableDoubleStateOf(1000.00) }
    var amount by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Saldo
        Text(
            text = "Saldo: $${"%.2f".format(balance)}",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de texto
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Monto a retirar") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón
        Button(onClick = {
            when {
                amount.isEmpty() -> showToast(context, "Ingrese un monto")
                amount.toDoubleOrNull() == null -> showToast(context, "Monto inválido")
                amount.toDouble() <= 0 -> showToast(context, "Monto debe ser > 0")
                amount.toDouble() > balance -> showToast(context, "Saldo insuficiente")
                else -> {
                    balance -= amount.toDouble()
                    context.startActivity(
                        Intent(context, ReceiptActivity::class.java).apply {
                            putExtra("AMOUNT", amount.toDouble())
                        }
                    )
                }
            }
        }) {
            Text("Retirar")
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun WalletAppPreview() {
    WalletApp()
}