package be.hogent.yasminedewinne.carwashapp.models.domain

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "carwashes",
    indices = [
        (Index(value = ["id"], unique = true))
    ]
)
data class Carwash(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @Json(name = "aanbiederId")
    var userId: Int = 0,

    var auto: String = "",

    var tarief: Double = 0.0,

    //var takenlijst:

    var uitleg: String = "",

    var datum: LocalDate,

    var beginUur: LocalTime
)