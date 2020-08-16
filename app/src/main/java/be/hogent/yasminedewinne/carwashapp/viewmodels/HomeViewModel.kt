package be.hogent.yasminedewinne.carwashapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import be.hogent.yasminedewinne.carwashapp.data.database.getDatabase
import be.hogent.yasminedewinne.carwashapp.models.domain.repositories.CarwashRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val carwashRepository = CarwashRepository(database.carwashDao)

    val carwashes = carwashRepository.carwashes

    private val _isLoading = MutableLiveData<Boolean>(null)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun refreshData() {
        viewModelScope.launch {
            _isLoading.value = true
            carwashRepository.loadCarwashes()
            _isLoading.value = false
            _isLoading.value = null
        }
    }

    fun setSelectedCarwash(carwashId: Int) {
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java))
                return HomeViewModel(application) as T

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
