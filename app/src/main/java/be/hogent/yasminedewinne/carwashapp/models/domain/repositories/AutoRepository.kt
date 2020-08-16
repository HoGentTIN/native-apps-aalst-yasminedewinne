package be.hogent.yasminedewinne.carwashapp.models.domain.repositories

import be.hogent.yasminedewinne.carwashapp.App
import be.hogent.yasminedewinne.carwashapp.data.database.AutoDao
import be.hogent.yasminedewinne.carwashapp.data.network.AutoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AutoRepository(private val autoDao: AutoDao) {

    val userHelper = App.getUserHelper()
    val user = userHelper.getSignedInUser()

    val autosPerUser = autoDao.getAutos(user?.id!!)

    suspend fun loadAutos(): Boolean {
        val userHelper = App.getUserHelper()
        val user = userHelper.getSignedInUser()

        if (user != null) {
            val id = user.id

            return withContext(Dispatchers.IO) {
                val autosCall = AutoService.HTTP.getAutosForUser(id)
                try {
                    val autos = autosCall.await()
                    autoDao.insertAll(*autos.map { x -> x.toModel() }.toTypedArray())

                    true
                } catch (e: HttpException) {
                    e.printStackTrace()

                    false
                } catch (e: Exception) {
                    e.printStackTrace()

                    false
                }
            }
        }
        return false
    }
}
