package ucne.edu.jairocamiloacosta_p2_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ucne.edu.jairocamiloacosta_p2_ap2.data.local.dao.DepositoDao
import ucne.edu.jairocamiloacosta_p2_ap2.data.local.entities.DepositoEntity

@Database(
    entities = [DepositoEntity::class],
    version = 1,
    exportSchema = false
)

abstract class DepositoDb(): RoomDatabase(){
    abstract fun depositoDao(): DepositoDao
}