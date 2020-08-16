package be.hogent.yasminedewinne.carwashapp.models.DTO

import be.hogent.yasminedewinne.carwashapp.models.domain.Auto
import be.hogent.yasminedewinne.carwashapp.models.domain.Carwash
import be.hogent.yasminedewinne.carwashapp.models.domain.User
import java.time.LocalDate
import java.time.LocalTime

data class CarwashDTO(
    val id: Int = 0,
    val auto: Auto? = null,
    val autoId: Int,
    var gebruikerId: Int,
    val gebruiker: User? = null,

    var tarief: Int,
    var takenlijst: String,
    var datum: LocalDate,
    var beginTijd: LocalTime,
    var eindTijd: LocalTime
) {
    fun toModel(): Carwash {
        return Carwash(
            id = id,
            tarief = tarief,
            takenlijst = takenlijst,
            datum = datum,
            beginTijd = beginTijd,
            eindTijd = eindTijd,
            gebruikerId = gebruiker?.id ?: 0,
            gebruikerStad = gebruiker?.adres?.stad ?: "",
            gebruikerAdres = gebruiker?.adres?.toString() ?: "",
            autoId = auto?.id ?: 0,
            autoMerk = auto?.merk ?: "",
            autoNaam = auto?.naam ?: ""
        )
    }
}
