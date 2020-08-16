package be.hogent.yasminedewinne.carwashapp.models.domain

data class User(
    val id: Int = 0,
    var voornaam: String,
    var familienaam: String,
    var email: String,
    var phoneNumber: String,
    var adres: Adres
)
