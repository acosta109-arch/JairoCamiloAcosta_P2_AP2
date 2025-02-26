package ucne.edu.jairocamiloacosta_p2_ap2.data.remote.entity

import ucne.edu.jairocamiloacosta_p2_ap2.data.remote.dto.EntityDto
import javax.inject.Inject

class DataSource @Inject constructor(
    private val entityManagerApi: EntityManagerApi
){
    suspend fun getEntitys() = entityManagerApi.getEntitys()

    suspend fun getEntity(id: Int) = entityManagerApi.getEntity(id)

    suspend fun saveEntity(entityDto: EntityDto) = entityManagerApi.saveEntity(entityDto)

    suspend fun actualizarEntity(id: Int, entityDto: EntityDto) = entityManagerApi.actualizarEntity(id, entityDto)

    suspend fun deleteEntity(id: Int) = entityManagerApi.deleteEntity(id)
}