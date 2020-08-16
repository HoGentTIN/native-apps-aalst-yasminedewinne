package be.hogent.yasminedewinne.carwashapp.models.domain

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import be.hogent.yasminedewinne.carwashapp.util.converters.DateConverter
import be.hogent.yasminedewinne.carwashapp.util.converters.TimeConverter
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "carwashes",
    indices = [
        (Index(value = ["id"], unique = true))
    ]
)
@TypeConverters(value = [DateConverter::class, TimeConverter::class])
data class Carwash(

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,

    var tarief: Int = 0,

    var takenlijst: String = "",

    var datum: LocalDate, // DB Format: yyyy-MM-dd

    var beginTijd: LocalTime, // DB Format: HH:mm:ss

    var eindTijd: LocalTime, // DB Format: HH:mm:ss

    var gebruikerId: Int,
    var gebruikerStad: String,
    var gebruikerAdres: String,

    var autoId: Int,
    var autoMerk: String,
    var autoNaam: String
)
