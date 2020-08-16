package be.hogent.yasminedewinne.carwashapp.models.domain

data class Adres(
    var huisNr: String,
    var straatNaam: String,
    var postcode: String,
    var stad: String,
    var land: String
) {
    override fun toString(): String {
        return String.format("%s %s, %s %s", straatNaam, huisNr, postcode, stad)
    }
}
