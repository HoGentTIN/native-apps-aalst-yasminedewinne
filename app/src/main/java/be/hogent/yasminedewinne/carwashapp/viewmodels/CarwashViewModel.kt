package be.hogent.yasminedewinne.carwashapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import be.hogent.yasminedewinne.carwashapp.data.database.getDatabase
import be.hogent.yasminedewinne.carwashapp.models.DTO.CarwashDTO
import be.hogent.yasminedewinne.carwashapp.models.domain.repositories.CarwashRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class CarwashViewModel (application: Application): AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val carwashRepository = CarwashRepository(database.carwashDao)

    var auto = MutableLiveData<String>()
    var tarief = MutableLiveData<Int>()
    var uitleg = MutableLiveData<String>()
    var datum = MutableLiveData<LocalDate>()
    var beginUur = MutableLiveData<LocalTime>()

    fun carwashAfwerken(){
        viewModelScope.launch {
            val carwash = CarwashDTO(
                auto = auto.value!!,
                tarief = tarief.value!!,
                takenlijst = uitleg.value!!,
                datum = datum.value!!,
                beginTijd =  beginUur.value!!,
                eindTijd = LocalTime.now(),
                aanbiederId = 0,
                autoId = 0
            )
            carwashRepository.postCarwash(carwash)
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CarwashViewModel::class.java))
                return CarwashViewModel(application) as T

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}