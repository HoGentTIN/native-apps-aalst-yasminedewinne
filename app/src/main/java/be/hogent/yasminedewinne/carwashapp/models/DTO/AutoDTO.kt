package be.hogent.yasminedewinne.carwashapp.models.DTO

import be.hogent.yasminedewinne.carwashapp.models.domain.Auto

data class AutoDTO(
    val id: Int = 0,

    var merk: String,
    var naam: String,
    var gebruikerId: Int
) {
    fun toModel(): Auto {
        return Auto(
            id = id,
            merk = merk,
            naam = naam,
            gebruikerId = gebruikerId
        )
    }
}