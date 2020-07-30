package be.hogent.yasminedewinne.carwashapp.models.domain

import androidx.room.*
import be.hogent.yasminedewinne.carwashapp.util.converters.DateConverter
import be.hogent.yasminedewinne.carwashapp.util.converters.TimeConverter
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "afspraken",
    indices = [
        (Index(value = ["id"], unique = true))
    ]
)
@TypeConverters(value = [DateConverter::class, TimeConverter::class])
data class Afspraak (

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,

    var gebruikerId: Int,

    var carwashId: Int,
    var carwashAdres: String,
    var carwashMerk: String,
    var carwashAuto: String,
    var carwashTarief: Int,
    var carwashDatum: LocalDate,
    var carwashBeginTijd: LocalTime,
    var carwashEindTijd: LocalTime
)