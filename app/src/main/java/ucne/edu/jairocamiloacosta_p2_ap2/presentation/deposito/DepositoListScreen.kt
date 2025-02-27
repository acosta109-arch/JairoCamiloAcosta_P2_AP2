package ucne.edu.jairocamiloacosta_p2_ap2.presentation.depositos

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import ucne.edu.jairocamiloacosta_p2_ap2.data.remote.dto.DepositoDto
import ucne.edu.jairocamiloacosta_p2_ap2.presentation.deposito.DepositoUiState
import ucne.edu.jairocamiloacosta_p2_ap2.presentation.deposito.DepositoViewModel

@Composable
fun DepositoListScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: DepositoViewModel = hiltViewModel(),
    createDeposito: () -> Unit,
    onEditDeposito: (Int) -> Unit,
    onDeleteDeposito: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var lastDepositoCount by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.getDepositos()
    }

    LaunchedEffect(uiState.depositos) {
        if (uiState.depositos.size > lastDepositoCount) {
            Toast.makeText(context, "Nuevo depósito: ${uiState.depositos.lastOrNull()?.fecha}", Toast.LENGTH_LONG).show()
        }
        lastDepositoCount = uiState.depositos.size
    }

    DepositoListBodyScreen(
        drawerState = drawerState,
        scope = scope,
        uiState = uiState,
        createDeposito = createDeposito,
        onEditDeposito = onEditDeposito,
        onDeleteDeposito = onDeleteDeposito,
        reloadDepositos = { viewModel.getDepositos() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositoListBodyScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: DepositoUiState,
    createDeposito: () -> Unit,
    onEditDeposito: (Int) -> Unit,
    onDeleteDeposito: (Int) -> Unit,
    reloadDepositos: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lista de Depósitos", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Blue)
            )
        },
        floatingActionButton = {
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.fillMaxSize()
            ) {
                FloatingActionButton(
                    onClick = reloadDepositos,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomEnd)
                        .offset(y = (-16).dp),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Recargar Depósitos")
                }

                FloatingActionButton(
                    onClick = createDeposito,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomEnd)
                        .offset(y = (-80).dp),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Añadir Depósito")
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(uiState.depositos) { deposito ->
                    DepositoRow(deposito, onEditDeposito, onDeleteDeposito)
                }
            }
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun DepositoRow(
    deposito: DepositoDto,
    onEditDeposito: (Int) -> Unit,
    onDeleteDeposito: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(5f)) {
                Text("ID Deposito: ${deposito.idDeposito}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("Fecha: ${deposito.fecha}", fontSize = 14.sp)
                Text("ID Cuenta: ${deposito.idCuenta}", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text("Concepto: ${deposito.concepto}", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text("Monto: ${deposito.monto}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Green)
            }
            Box(modifier = Modifier.weight(1f)) {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Opciones")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Editar") },
                        onClick = {
                            expanded = false
                            onEditDeposito(deposito.idDeposito)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Eliminar") },
                        onClick = {
                            expanded = false
                            onDeleteDeposito(deposito.idDeposito)
                        }
                    )
                }
            }
        }
    }
}
