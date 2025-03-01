package ucne.edu.jairocamiloacosta_p2_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import ucne.edu.jairocamiloacosta_p2_ap2.data.local.entities.DepositoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DepositoDao {
    @Upsert()
    suspend fun save(entidades: List<DepositoEntity>)
    @Query(
        """
            SELECT *
            FROM Depositos 
            WHERE idDeposito == :id limit 1
        """
    )
    suspend fun find(id: Int): DepositoEntity?
    @Delete
    suspend fun delete(deposito: DepositoEntity)
    @Query("SELECT * FROM Depositos")
    fun getAll(): Flow<List<DepositoEntity>>
}