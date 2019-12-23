package be.hogent.yasminedewinne.carwashapp.models.domain

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import be.hogent.yasminedewinne.carwashapp.util.converters.DateConverter
import be.hogent.yasminedewinne.carwashapp.util.converters.TimeConverter
import com.squareup.moshi.Json
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

    var aanbiederId: Int = 0,

    var auto: String = "",

    var tarief: Double = 0.0,

    var takenUitleg: String = "",

    var datum: LocalDate, // DB Format: yyyy-MM-dd

    var beginUur: LocalTime // DB Format: HH:mm:ss
)