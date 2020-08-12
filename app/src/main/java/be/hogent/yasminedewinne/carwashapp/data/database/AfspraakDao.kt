package be.hogent.yasminedewinne.carwashapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.hogent.yasminedewinne.carwashapp.models.domain.Afspraak

@Dao
interface AfspraakDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(afspraak: Afspraak)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg afspraken: Afspraak)

    @Query("DELETE FROM afspraken")
    fun clear(): Int

    @Query("SELECT * FROM afspraken WHERE carwashDatum >= date('now') ORDER BY carwashDatum ASC")
    fun getKomendeAfspraken(): LiveData<List<Afspraak>>

    @Query("SELECT * FROM afspraken WHERE carwashDatum < date('now') ORDER BY carwashDatum ASC")
    fun getAfgelopenAfspraken(): LiveData<List<Afspraak>>

    @Query("SELECT * FROM afspraken WHERE id = :id")
    fun getById(id: Int): Afspraak

    @Query("DELETE FROM afspraken WHERE id = :id")
    fun deleteAfspraak(id: Int): Int
}
