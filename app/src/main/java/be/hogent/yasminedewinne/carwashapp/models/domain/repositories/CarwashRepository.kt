package be.hogent.yasminedewinne.carwashapp.models.domain.repositories

import be.hogent.yasminedewinne.carwashapp.App
import be.hogent.yasminedewinne.carwashapp.data.database.CarwashDao
import be.hogent.yasminedewinne.carwashapp.data.network.CarwashService
import be.hogent.yasminedewinne.carwashapp.models.DTO.CarwashDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.InterruptedIOException

class CarwashRepository(private val carwashDao: CarwashDao) {

    val carwashes = carwashDao.getCarwashes()

    suspend fun postCarwash(carwash: CarwashDTO): Int{
        val userHelper = App.getUserHelper()
        val user = userHelper.getSignedInUser()
        if(user != null){
            val id = user.id
            carwash.aanbiederId = id

            return withContext(Dispatchers.IO){
                val call = CarwashService.HTTP.postCarwash(carwash)

                val response = try {
                    val result = call.await()
                    carwashDao.insert(result.toModel())

                    200
                } catch (e: HttpException) {
                    e.printStackTrace()

                    e.code()
                } catch (e: InterruptedIOException) {
                    e.printStackTrace()

                    503
                } catch (e: Exception) {
                    e.printStackTrace()

                    -1
                }

                response
            }
        }
        return -1
    }

    suspend fun loadCarwashes(): Boolean{
        return withContext(Dispatchers.IO){
            val carwashCall = CarwashService.HTTP.getAllCarwashes()
            try {
                val carwashes = carwashCall.await()
                carwashDao.clear()
                carwashDao.insertAll(*carwashes.toTypedArray())

                true
            }
            catch (e: HttpException){
                e.printStackTrace()

                false
            }catch (e: Exception){
                e.printStackTrace()

                false
            }
        }
    }
}