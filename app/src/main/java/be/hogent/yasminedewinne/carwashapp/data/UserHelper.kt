package be.hogent.yasminedewinne.carwashapp.data

import android.content.Context
import be.hogent.yasminedewinne.carwashapp.models.domain.Adres
import be.hogent.yasminedewinne.carwashapp.models.domain.User
import com.auth0.android.jwt.JWT

class UserHelper(context: Context) {

    private val dataHelper: LocalDataHelper = LocalDataHelper("Auth", context)

    val authToken: String?
        get() = dataHelper.getString(LocalDataHelper.Key.STR_USERTOKEN)

    fun isSignedIn(): Boolean {
        return authToken != null
    }

    fun getSignedInUser(): User? {
        if (!isSignedIn())
            return null

        val jwt = JWT(authToken!!)

        val id = jwt.getClaim("id").asInt()!!
        val email = jwt.getClaim("email").asString()!!
        val voornaam = jwt.getClaim("voornaam").asString()!!
        val familienaam = jwt.getClaim("familienaam").asString()!!
        val telNr = jwt.getClaim("telNr").asString()!!

        val straatnaam = jwt.getClaim("straatnaam").asString()!!
        val huisNr = jwt.getClaim("huisNr").asString()!!
        val postcode = jwt.getClaim("postcode").asString()!!
        val stad = jwt.getClaim("stad").asString()!!
        val land = jwt.getClaim("land").asString()!!

        val adres = Adres(huisNr, straatnaam, postcode, stad, land)

        return User(id, voornaam, familienaam, email, telNr, adres)
    }

    fun saveUser(authToken: String) {
        dataHelper.put(LocalDataHelper.Key.STR_USERTOKEN, authToken)

        dataHelper.put(LocalDataHelper.Key.STR_USERPICTURE, "")

        dataHelper.applyChanges()
    }

    fun signOut() {
        dataHelper.remove(LocalDataHelper.Key.BOOL_ISFIRSTSETUP)
        dataHelper.remove(LocalDataHelper.Key.STR_USERPICTURE)
        dataHelper.remove(LocalDataHelper.Key.STR_USERTOKEN)
        dataHelper.applyChanges()
    }
}
