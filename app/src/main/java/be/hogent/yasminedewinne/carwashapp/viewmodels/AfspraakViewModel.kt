package be.hogent.yasminedewinne.carwashapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import be.hogent.yasminedewinne.carwashapp.data.database.getDatabase
import be.hogent.yasminedewinne.carwashapp.models.domain.repositories.AfspraakRepository
import kotlinx.coroutines.launch

class AfspraakViewModel(application: Application): AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val afspraakRepository = AfspraakRepository(database.afspraakDao)

    val komendeAfspraken = afspraakRepository.komendeAfspraken
    val afgelopenAfspraken = afspraakRepository.afgelopenAfspraken

    private val _isLoading = MutableLiveData<Boolean>(null)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun refreshData() {
        viewModelScope.launch {
            _isLoading.value = true
            afspraakRepository.loadAfspraken()
            _isLoading.value = false
            _isLoading.value = null
        }
    }

    fun setSelectedAfspraak(afspraakId: Int) {

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