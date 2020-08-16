package be.hogent.yasminedewinne.carwashapp.models.DTO

import be.hogent.yasminedewinne.carwashapp.models.domain.Afspraak
import be.hogent.yasminedewinne.carwashapp.models.domain.User
import java.time.LocalDate
import java.time.LocalTime

data class AfspraakDTO(
    val id: Int = 0,

    var gebruikerId: Int,
    var gebruiker: User? = null,

    var carwashId: Int,
    var carwash: CarwashDTO? = null
) {
    fun toModel(): Afspraak {
        return Afspraak(
            id = id,
            gebruikerId = gebruikerId,
            carwashId = carwash?.id ?: 0,
            // carwashAdres = carwash?.gebruikerAdres ?: "",
            carwashAdres = carwash?.gebruiker?.adres?.straatNaam ?: "",
            // carwashMerk = carwash?.autoMerk ?: "",
            carwashMerk = carwash?.auto?.merk ?: "",
            // carwashAuto = carwash?.autoNaam ?: "",
            carwashAuto = carwash?.auto?.naam ?: "",
            carwashTarief = carwash?.tarief ?: 0,
            carwashDatum = carwash?.datum ?: LocalDate.now(),
            carwashBeginTijd = carwash?.beginTijd ?: LocalTime.now(),
            carwashEindTijd = carwash?.eindTijd ?: LocalTime.now()
        )
    }
}
