package be.hogent.yasminedewinne.carwashapp.models.DTO

import be.hogent.yasminedewinne.carwashapp.models.domain.Afspraak

data class AfspraakDTO(
    val id: Int = 0,

    var gebruikerId: Int,
    var carwashId: Int
) {
    fun toModel(): Afspraak{
        return Afspraak(
            id = id,
            gebruikerId = gebruikerId,
            carwashId = carwashId
        )
    }
}