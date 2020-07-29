package be.hogent.yasminedewinne.carwashapp.models.DTO

import be.hogent.yasminedewinne.carwashapp.models.domain.Carwash
import java.time.LocalDate
import java.time.LocalTime

data class CarwashDTO(
    val id: Int = 0,

    var auto: String,
    var tarief: Int,
    var takenlijst: String,
    var datum: LocalDate,
    var beginTijd: LocalTime,
    var eindTijd: LocalTime,
    var aanbiederId: Int,
    var autoId: Int
) {
    fun toModel(): Carwash{
        return Carwash(
            id = id,
            tarief = tarief,
            takenlijst = takenlijst,
            datum = datum,
            beginTijd = beginTijd,
            eindTijd = eindTijd,
            aanbiederId = aanbiederId,
            autoId = autoId
        )
    }
}