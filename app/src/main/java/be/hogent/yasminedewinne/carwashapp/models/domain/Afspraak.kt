package be.hogent.yasminedewinne.carwashapp.models.domain

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "afspraken",
    indices = [
        (Index(value = ["id"], unique = true))
    ]
)
data class Afspraak (

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,

    var gebruikerId: Int = 0,

    var carwashId: Int = 0
)