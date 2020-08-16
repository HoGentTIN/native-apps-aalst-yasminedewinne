package be.hogent.yasminedewinne.carwashapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import be.hogent.yasminedewinne.carwashapp.data.database.getDatabase
import be.hogent.yasminedewinne.carwashapp.models.domain.Afspraak
import be.hogent.yasminedewinne.carwashapp.models.domain.repositories.AfspraakRepository
import kotlinx.coroutines.launch

class AfspraakDetailsDialogViewModel(application: Application, afspraakId: Int) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val afspraakRepository = AfspraakRepository(database.afspraakDao)

    private val _afspraak = MutableLiveData<Afspraak>()
    val afspraak: LiveData<Afspraak>
        get() = _afspraak

    init {
        viewModelScope.launch {
            val afspraak = afspraakRepository.getById(afspraakId)
            _afspraak.value = afspraak
        }
    }

    fun afspraakVerwijderen(id: Int) {
        viewModelScope.launch {
            afspraakRepository.deleteAfspraak(id)
        }
    }

    class Factory(private val application: Application, private val afspraakId: Int) : ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AfspraakDetailsDialogViewModel::class.java))
                return AfspraakDetailsDialogViewModel(application, afspraakId) as T

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
