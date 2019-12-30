package be.hogent.yasminedewinne.carwashapp.models.domain.repositories

import be.hogent.yasminedewinne.carwashapp.App
import be.hogent.yasminedewinne.carwashapp.data.database.AfspraakDao
import be.hogent.yasminedewinne.carwashapp.data.network.AfspraakService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AfspraakRepository(private val afspraakDao: AfspraakDao) {

    val afspraken = afspraakDao.getAfspraken()

    suspend fun loadAfspraken(): Boolean{
        val userHelper = App.getUserHelper()
        val user = userHelper.getSignedInUser()

        if( user != null){
            val id = user.id

            return withContext(Dispatchers.IO){
                val afsprakenCall = AfspraakService.HTTP.getAfsprakenForUser((id))
                try {
                    val afspraken = afsprakenCall.await()
                    afspraakDao.insertAll(*afspraken.toTypedArray())

                    true
                }catch (e:HttpException){
                    e.printStackTrace()

                    false
                }catch (e:Exception){
                    e.printStackTrace()

                    false
                }
            }
        }
        return false
    }
}