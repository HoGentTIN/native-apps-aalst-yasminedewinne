package be.hogent.yasminedewinne.carwashapp.viewmodels

import android.app.Activity
import android.app.Application
import androidx.lifecycle.*
import be.hogent.yasminedewinne.carwashapp.data.LocalDataHelper
import be.hogent.yasminedewinne.carwashapp.data.database.getDatabase
import kotlinx.coroutines.launch

class LoadingViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)

    private val dataHelper = LocalDataHelper("setup", application)

    private val _loadingResult = MutableLiveData<Int>()
    val isLoading: LiveData<Int>
        get() = _loadingResult

    init {
        viewModelScope.launch {
            // Check bij startup als de data gedownload is
            // Indien er geen data is, kunnen we geen functionaliteit aanbieden
            val isFirstSetup = dataHelper.getBoolean(LocalDataHelper.Key.BOOL_ISFIRSTSETUP, defaultValue = true)

            // Loading speed up: Als het niet de eerste startup is en niks is geladen
            // -> ga er dan vanuit dat de server niet beschikbaar is
            if (isFirstSetup) {
                /*if (isFirstSetup) {
                    _loadingResult.value = Activity.RESULT_CANCELED
                    return@launch
                }*/

                dataHelper.put(LocalDataHelper.Key.BOOL_ISFIRSTSETUP, false)
                dataHelper.applyChanges()
            }

            _loadingResult.value = Activity.RESULT_OK
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoadingViewModel::class.java))
                return LoadingViewModel(application) as T

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}