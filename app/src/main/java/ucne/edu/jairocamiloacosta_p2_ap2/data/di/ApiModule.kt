package ucne.edu.jairocamiloacosta_p2_ap2.data.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ucne.edu.jairocamiloacosta_p2_ap2.data.local.database.DepositoDb
import ucne.edu.jairocamiloacosta_p2_ap2.data.remote.entity.DepositoManagerApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            DepositoDb::class.java,
            "Deposito.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideDao(depositoDb: DepositoDb) = depositoDb.depositoDao()

    private const val BASE_URL = "https://sagapi-dev.azurewebsites.net/"

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()


    @Provides
    @Singleton
    fun providesDepositoApi(moshi: Moshi): DepositoManagerApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(DepositoManagerApi::class.java)
    }
}