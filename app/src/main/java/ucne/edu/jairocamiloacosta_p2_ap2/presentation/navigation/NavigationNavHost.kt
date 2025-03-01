package ucne.edu.jairocamiloacosta_p2_ap2.presentation.navigation

import androidx.navigation.NavHostController

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ucne.edu.jairocamiloacosta_p2_ap2.presentation.deposito.DeleteDepositoScreen
import ucne.edu.jairocamiloacosta_p2_ap2.presentation.depositos.DepositoListScreen
import ucne.edu.jairocamiloacosta_p2_ap2.presentation.depositos.DepositoScreen
import ucne.edu.jairocamiloacosta_p2_ap2.presentation.depositos.EditDepositoScreen

@Composable
fun registro_depositos(navHostController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navHostController,
        startDestination = Screen.DepositoList
    ) {
        composable<Screen.DepositoList> {
            DepositoListScreen(
                drawerState = drawerState,
                scope = scope,
                createDeposito = {
                    navHostController.navigate(Screen.Deposito(0))
                },
                onEditDeposito = { deposito ->
                    navHostController.navigate(Screen.EditDeposito(deposito))
                },
                onDeleteDeposito = { deposito ->
                    navHostController.navigate(Screen.DeleteDeposito(deposito))
                }
            )
        }

        composable<Screen.Deposito> {
            val args = it.toRoute<Screen.Deposito>()
            DepositoScreen(
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }

        composable<Screen.EditDeposito> {
            val args = it.toRoute<Screen.EditDeposito>()
            EditDepositoScreen(
                depositoId = args.depositoId,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }

        composable<Screen.DeleteDeposito> {
            val args = it.toRoute<Screen.DeleteDeposito>()
            DeleteDepositoScreen(
                depositoId = args.depositoId,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }

    }
}