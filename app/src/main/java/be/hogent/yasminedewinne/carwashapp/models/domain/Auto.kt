package be.hogent.yasminedewinne.carwashapp.models.domain

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "autos",
    indices = [
        (Index(value = ["id"], unique = true))
    ]
)
data class Auto(

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,

    var merk: String = "",

    var naam: String = "",

    var gebruikerId: Int = 0
)
