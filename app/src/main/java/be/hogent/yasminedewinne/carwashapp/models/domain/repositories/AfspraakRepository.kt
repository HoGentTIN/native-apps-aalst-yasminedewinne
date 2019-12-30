package be.hogent.yasminedewinne.carwashapp.models.domain.repositories

import be.hogent.yasminedewinne.carwashapp.App
import be.hogent.yasminedewinne.carwashapp.data.database.AfspraakDao
import be.hogent.yasminedewinne.carwashapp.data.network.AfspraakService
import be.hogent.yasminedewinne.carwashapp.models.DTO.AfspraakDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.InterruptedIOException

class AfspraakRepository(private val afspraakDao: AfspraakDao) {

    val afspraken = afspraakDao.getAfspraken()

    suspend fun postAfspraak(afspraak: AfspraakDTO): Int{
        val userHelper = App.getUserHelper()
        val user = userHelper.getSignedInUser()
        if(user != null){
            val id = user.id
            afspraak.gebruikerId = id

            return withContext(Dispatchers.IO){
                val call = AfspraakService.HTTP.postAfspraak(afspraak)

                val response = try {
                    val result = call.await()
                    afspraakDao.insert(result.toModel())

                    200
                }catch (e: HttpException){
                    e.printStackTrace()

                    e.code()
                }catch (e: InterruptedIOException){
                    e.printStackTrace()

                    503
                }catch (e: Exception){
                    e.printStackTrace()

                    -1
                }
                response
            }
        }
        return -1
    }

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