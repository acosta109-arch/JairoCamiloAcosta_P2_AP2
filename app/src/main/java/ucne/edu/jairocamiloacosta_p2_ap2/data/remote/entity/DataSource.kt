package ucne.edu.jairocamiloacosta_p2_ap2.data.remote.entity

import ucne.edu.jairocamiloacosta_p2_ap2.data.remote.dto.DepositoDto
import javax.inject.Inject

class DataSource @Inject constructor(
    private val depositoManagerApi: DepositoManagerApi
){
    suspend fun getDepositos() = depositoManagerApi.getDepositos()

    suspend fun saveDeposito(depositoDto: DepositoDto) = depositoManagerApi.saveDeposito(depositoDto)

    suspend fun actualizarDeposito(id: Int, depositoDto: DepositoDto) = depositoManagerApi.actualizarDeposito(id, depositoDto)

    suspend fun deleteDeposito(id: Int) = depositoManagerApi.deleteDeposito(id)
}