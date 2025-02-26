package ucne.edu.jairocamiloacosta_p2_ap2.data.repository

import ucne.edu.jairocamiloacosta_p2_ap2.data.remote.entity.DataSource
import javax.inject.Inject
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ucne.edu.jairocamiloacosta_p2_ap2.data.remote.dto.EntityDto
import ucne.edu.jairocamiloacosta_p2_ap2.data.remote.entity.Resource

class EntityRepository @Inject constructor(
    private val dataSource: DataSource
){
    fun getEntitys(): Flow<Resource<List<EntityDto>>> = flow {
        try{
            emit(Resource.Loading())
            val entitys = dataSource.getEntitys()
            emit(Resource.Success(entitys))
        }catch (e: HttpException){
            val errorMessage = e.response()?.errorBody()?.string()?: e.message()
            Log.e("EntityRepository", "HttpException: ${e.message}")
            emit(Resource.Error("Error de conexion: ${e.message}"))
        }catch (e: Exception){
            Log.e("EntityRepository", "Exception: ${e.message}")
            emit(Resource.Error("Error:${e.message}"))
        }
    }

    suspend fun update(id: Int, entityDto: EntityDto) =
        dataSource.actualizarEntity(id, entityDto)

    suspend fun find(id: Int) = dataSource.getEntity(id)

    suspend fun save(entityDto: EntityDto) = dataSource.saveEntity(entityDto)

    suspend fun delete(id: Int) = dataSource.deleteEntity(id)
}