package ucne.edu.jairocamiloacosta_p2_ap2.data.repository

import ucne.edu.jairocamiloacosta_p2_ap2.data.remote.entity.DataSource
import javax.inject.Inject
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ucne.edu.jairocamiloacosta_p2_ap2.data.remote.dto.DepositoDto
import ucne.edu.jairocamiloacosta_p2_ap2.data.remote.entity.Resource

class DepositoRepository @Inject constructor(
    private val dataSource: DataSource
){
    fun getDepositos(): Flow<Resource<List<DepositoDto>>> = flow {
        try{
            emit(Resource.Loading())
            val depositos = dataSource.getDepositos()
            emit(Resource.Success(depositos))
        }catch (e: HttpException){
            val errorMessage = e.response()?.errorBody()?.string()?: e.message()
            Log.e("DepositoRepository", "HttpException: ${e.message}")
            emit(Resource.Error("Error de conexion: ${e.message}"))
        }catch (e: Exception){
            Log.e("DepositoRepository", "Exception: ${e.message}")
            emit(Resource.Error("Error:${e.message}"))
        }
    }

    suspend fun update(id: Int, depositoDto: DepositoDto) =
        dataSource.actualizarDeposito(id, depositoDto)

    suspend fun find(id: Int) = dataSource.getDeposito(id)

    suspend fun save(depositoDto: DepositoDto) = dataSource.saveDeposito(depositoDto)

    suspend fun delete(id: Int) = dataSource.deleteDeposito(id)
}