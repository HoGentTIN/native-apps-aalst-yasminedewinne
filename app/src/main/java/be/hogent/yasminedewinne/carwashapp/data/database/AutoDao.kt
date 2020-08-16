package be.hogent.yasminedewinne.carwashapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.hogent.yasminedewinne.carwashapp.models.domain.Auto

@Dao
interface AutoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(auto: Auto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg autos: Auto)

    @Query("DELETE FROM autos")
    fun clear(): Int

    @Query("SELECT * FROM autos WHERE gebruikerId = :id")
    fun getAutos(id: Int): LiveData<List<Auto>>
}
