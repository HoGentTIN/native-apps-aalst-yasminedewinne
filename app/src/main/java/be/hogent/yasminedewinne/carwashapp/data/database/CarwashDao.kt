package be.hogent.yasminedewinne.carwashapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.hogent.yasminedewinne.carwashapp.models.domain.Carwash

@Dao
interface CarwashDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(carwash: Carwash)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg carwashes: Carwash)

    @Query("DELETE FROM carwashes")
    fun clear(): Int

    @Query("SELECT * FROM carwashes ORDER BY datum ASC")
    fun getCarwashes(): LiveData<List<Carwash>>

    @Query("SELECT * FROM carwashes WHERE id = :id")
    fun getById(id: Int): Carwash
}

