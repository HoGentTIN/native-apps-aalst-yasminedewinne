package be.hogent.yasminedewinne.carwashapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import be.hogent.yasminedewinne.carwashapp.data.database.getDatabase
import be.hogent.yasminedewinne.carwashapp.models.domain.Carwash
import be.hogent.yasminedewinne.carwashapp.models.domain.repositories.CarwashRepository
import kotlinx.coroutines.launch

class CarwashDetailsDialogViewModel(application:Application, carwashId: Int) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val carwashRepository = CarwashRepository(database.carwashDao)

    private val _carwash = MutableLiveData<Carwash>()
    val carwash: LiveData<Carwash>
        get() = _carwash

    init {
        viewModelScope.launch {
            val carwash = carwashRepository.getById(carwashId)
            _carwash.value = carwash
        }
    }


    class Factory(private val application: Application, private val carwashId: Int) : ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CarwashDetailsDialogViewModel::class.java))
                return CarwashDetailsDialogViewModel(application, carwashId) as T

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}