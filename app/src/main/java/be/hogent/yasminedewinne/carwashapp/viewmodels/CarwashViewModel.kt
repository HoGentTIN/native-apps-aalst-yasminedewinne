package be.hogent.yasminedewinne.carwashapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import be.hogent.yasminedewinne.carwashapp.data.database.getDatabase
import be.hogent.yasminedewinne.carwashapp.models.DTO.CarwashDTO
import be.hogent.yasminedewinne.carwashapp.models.domain.repositories.AutoRepository
import be.hogent.yasminedewinne.carwashapp.models.domain.repositories.CarwashRepository
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.coroutines.launch

class CarwashViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val carwashRepository = CarwashRepository(database.carwashDao)
    private val autoRepository = AutoRepository(database.autoDao)

    val autos = autoRepository.autosPerUser

    var autoId = MutableLiveData<Int>()
    var tarief = MutableLiveData<Int>()
    var uitleg = MutableLiveData<String>()
    var datum = MutableLiveData<LocalDate>()
    var beginUur = MutableLiveData<LocalTime>()
    var eindUur = MutableLiveData<LocalTime>()

    fun carwashAfwerken() {
        viewModelScope.launch {
            val carwash = CarwashDTO(
                tarief = tarief.value!!,
                takenlijst = uitleg.value!!,
                datum = datum.value!!,
                beginTijd = beginUur.value!!,
                eindTijd = eindUur.value!!,
                gebruikerId = 0, // wordt in de repository ingesteld
                autoId = autoId.value!!
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
