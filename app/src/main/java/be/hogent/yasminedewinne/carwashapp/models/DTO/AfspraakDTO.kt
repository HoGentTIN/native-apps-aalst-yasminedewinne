package be.hogent.yasminedewinne.carwashapp.models.DTO

import be.hogent.yasminedewinne.carwashapp.models.domain.Afspraak
import be.hogent.yasminedewinne.carwashapp.models.domain.Carwash
import be.hogent.yasminedewinne.carwashapp.models.domain.User
import java.time.LocalDate
import java.time.LocalTime

data class AfspraakDTO(
    val id: Int = 0,

    var gebruikerId: Int,
    var gebruiker: User? = null,

    var carwashId: Int,
    var carwash: Carwash? = null
) {
    fun toModel(): Afspraak{
        return Afspraak(
            id = id,
            gebruikerId = gebruikerId,
            carwashId = carwash?.id ?: 0,
            carwashAdres = carwash?.gebruikerAdres ?: "",
            carwashMerk = carwash?.autoMerk ?: "",
            carwashAuto = carwash?.autoNaam ?: "",
            carwashTarief = carwash?.tarief ?: 0,
            carwashDatum = carwash?.datum ?: LocalDate.now(),
            carwashBeginTijd = carwash?.beginTijd ?: LocalTime.now(),
            carwashEindTijd = carwash?.eindTijd ?: LocalTime.now()
        )
    }
}