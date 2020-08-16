package be.hogent.yasminedewinne.carwashapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import be.hogent.yasminedewinne.carwashapp.data.database.getDatabase
import be.hogent.yasminedewinne.carwashapp.models.domain.repositories.AfspraakRepository
import be.hogent.yasminedewinne.carwashapp.models.domain.repositories.CarwashRepository
import kotlinx.coroutines.launch

class AfspraakViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val afspraakRepository = AfspraakRepository(database.afspraakDao)
    private val carwashRepository = CarwashRepository(database.carwashDao)

    val komendeAfspraken = afspraakRepository.komendeAfspraken
    val afgelopenAfspraken = afspraakRepository.afgelopenAfspraken
    val eigenCarwashes = carwashRepository.eigenCarwashes

    private val _isLoading = MutableLiveData<Boolean>(null)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun refreshData() {
        viewModelScope.launch {
            _isLoading.value = true
            afspraakRepository.loadAfspraken()
            carwashRepository.loadCarwashes()
            _isLoading.value = false
            _isLoading.value = null
        }
    }

    fun carwashVerwijderen(id: Int) {
        viewModelScope.launch {
            carwashRepository.deleteCarwash(id)
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AfspraakViewModel::class.java))
                return AfspraakViewModel(application) as T

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
