package ucne.edu.jairocamiloacosta_p2_ap2.presentation.depositos

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ucne.edu.jairocamiloacosta_p2_ap2.presentation.deposito.DepositoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDepositoScreen(
    depositoId: Int,
    viewModel: DepositoViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(depositoId) {
        viewModel.find(depositoId)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Editar Depósito",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Blue
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                label = { Text(text = "Fecha") },
                value = uiState.fecha,
                onValueChange = { viewModel.onFechaChange(it) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                label = { Text(text = "ID Cuenta") },
                value = uiState.idCuenta.toString(),
                onValueChange = { value ->
                    viewModel.onIdCuentaChange(value.toIntOrNull() ?: 0)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                label = { Text(text = "Concepto") },
                value = uiState.concepto,
                onValueChange = { viewModel.onConceptoChange(it) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                label = { Text(text = "Monto") },
                value = uiState.monto.toString(),
                onValueChange = { value ->
                    viewModel.onMontoChange(value.toDoubleOrNull() ?: 0.0)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.update() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Guardar Cambios")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Guardar Cambios")
            }

            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage!!,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
