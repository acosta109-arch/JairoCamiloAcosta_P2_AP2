package ucne.edu.jairocamiloacosta_p2_ap2.presentation.deposito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ucne.edu.jairocamiloacosta_p2_ap2.data.local.entities.DepositoEntity
import ucne.edu.jairocamiloacosta_p2_ap2.data.remote.dto.DepositoDto
import ucne.edu.jairocamiloacosta_p2_ap2.data.remote.entity.Resource
import ucne.edu.jairocamiloacosta_p2_ap2.data.repository.DepositoRepository
import javax.inject.Inject

@HiltViewModel
class DepositoViewModel @Inject constructor(
    private val depositoRepository: DepositoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DepositoUiState())
    val uiState: StateFlow<DepositoUiState> = _uiState.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        getDepositos()
    }

    fun save() {
        viewModelScope.launch {
            if (isValid()) {
                depositoRepository.save(_uiState.value.toEntity())
            }
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            depositoRepository.delete(id)
        }
    }

    fun update() {
        viewModelScope.launch {
            _uiState.value.idDeposito.let {
                if (it != null) {
                    depositoRepository.update(
                        it, DepositoDto(
                            idDeposito = _uiState.value.idDeposito,
                            fecha = _uiState.value.fecha,
                            concepto = _uiState.value.concepto,
                            monto = _uiState.value.monto.toDouble(),
                            idCuenta = _uiState.value.idCuenta.toInt()
                        )
                    )
                }
            }
        }
    }

    fun new() {
        _uiState.value = DepositoUiState()
    }

    fun onFechaChange(fecha: String) {
        _uiState.update {
            it.copy(fecha = fecha)
        }
    }

    fun onIdCuentaChange(idCuenta: Int) {
        _uiState.update {
            it.copy(idCuenta = idCuenta)
        }
    }

    fun onMontoChange(monto: Double) {
        _uiState.update {
            it.copy(monto = monto)
        }
    }

    fun onConceptoChange(concepto: String) {
        _uiState.update {
            it.copy(
                concepto = concepto,
                errorMessage = if (concepto.isBlank()) "Debes rellenar el campo Concepto" else null
            )
        }
    }

    fun getDepositos() {
        viewModelScope.launch {
            depositoRepository.getDepositos().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                depositos = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message ?: "Error desconocido",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }


    fun find(depositoId: Int) {
        viewModelScope.launch {
            val deposito = depositoRepository.find(depositoId)

            if (deposito != null) {
                _uiState.update {
                    it.copy(
                        idDeposito = deposito.idDeposito ?: 0,
                        fecha = deposito.fecha,
                        concepto = deposito.concepto,
                        monto = deposito.monto ?: 0.0,
                        idCuenta = deposito.idCuenta ?: 0
                    )
                }
            }
        }
    }


    fun isValid(): Boolean {
        return uiState.value.concepto.isNotBlank() && uiState.value.monto > 0
    }
}

fun DepositoUiState.toEntity() = DepositoDto(
    idDeposito = idDeposito,
    fecha = fecha,
    idCuenta = idCuenta.toInt(),
    concepto = concepto,
    monto = monto.toDouble()
)

data class DepositoUiState(
    val idDeposito: Int = 0,
    val fecha: String = "",
    val idCuenta: Int = 0,
    val concepto: String = "",
    val monto: Double = 0.0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val depositos: List<DepositoEntity> = emptyList()
)
