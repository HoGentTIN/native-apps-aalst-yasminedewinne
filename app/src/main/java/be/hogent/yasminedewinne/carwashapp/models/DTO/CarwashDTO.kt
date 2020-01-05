package be.hogent.yasminedewinne.carwashapp.models.DTO

import be.hogent.yasminedewinne.carwashapp.models.domain.Carwash
import java.time.LocalDate
import java.time.LocalTime

data class CarwashDTO(
    val id: Int = 0,

    var aanbiederId: Int,
    var auto: String,
    var tarief: String,
    var takenUitlig: String,
    var datum: LocalDate,
    var beginUur: LocalTime
) {
    fun toModel(): Carwash{
        return Carwash(
            id = id,
            aanbiederId = aanbiederId,
            auto = auto,
            tarief = tarief,
            takenUitleg = takenUitlig,
            datum = datum,
            beginUur = beginUur
        )
    }
}