package be.hogent.yasminedewinne.carwashapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import be.hogent.yasminedewinne.carwashapp.models.domain.Afspraak
import be.hogent.yasminedewinne.carwashapp.models.domain.Auto
import be.hogent.yasminedewinne.carwashapp.models.domain.Carwash

@Database(entities = [Carwash::class, Afspraak::class, Auto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract val carwashDao: CarwashDao
    abstract val afspraakDao: AfspraakDao
    abstract val autoDao: AutoDao
}

@Volatile
private lateinit var INSTANCE: AppDatabase

fun getDatabase(context: Context): AppDatabase {
    synchronized(AppDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room
                .databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database")
                .fallbackToDestructiveMigration()
                .build()
        }

        return INSTANCE
    }
}