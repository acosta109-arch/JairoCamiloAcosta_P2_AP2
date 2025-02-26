package ucne.edu.jairocamiloacosta_p2_ap2.data.remote.entity

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ucne.edu.jairocamiloacosta_p2_ap2.data.remote.dto.EntityDto

interface EntityManagerApi{
    @GET("api/Entity")
    suspend fun getEntitys(): List<EntityDto>

    @GET("api/Entity/{id}")
    suspend fun getEntity(@Path("id")id: Int): EntityDto

    @POST("api/Entity")
    suspend fun saveEntity(@Body entityDto: EntityDto?): EntityDto

    @PUT("api/Entity/{id}")
    suspend fun actualizarEntity(
        @Path("id")entityId: Int,
        @Body entity: EntityDto
    ): EntityDto

    @DELETE("api/Entity/{id}")
    suspend fun deleteEntity(@Path("id")id: Int): ResponseBody
}