package be.hogent.yasminedewinne.carwashapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import be.hogent.yasminedewinne.carwashapp.data.UserHelper
import be.hogent.yasminedewinne.carwashapp.data.network.AuthService
import be.hogent.yasminedewinne.carwashapp.models.DTO.LoginDTO
import java.io.InterruptedIOException
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private val userHelper = UserHelper(context)

    private val _loginResponse = MutableLiveData<Int>()
    val loginResponse: LiveData<Int>
        get() = _loginResponse

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val loginCall = AuthService.HTTP.loginUser(LoginDTO(email, password))
            try {
                val result = loginCall.await()
                userHelper.saveUser(result.authToken)
                _loginResponse.value = 200
            } catch (e: HttpException) {
                _loginResponse.value = e.code()
            } catch (e: InterruptedIOException) {
                _loginResponse.value = 504
            } catch (e: Exception) {
                _loginResponse.value = 400
                e.printStackTrace()
            }

            _loginResponse.value = null
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java))
                return LoginViewModel(application) as T

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
