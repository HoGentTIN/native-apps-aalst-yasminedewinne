package be.hogent.yasminedewinne.carwashapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.hogent.yasminedewinne.carwashapp.data.database.getDatabase
import be.hogent.yasminedewinne.carwashapp.models.domain.repositories.CarwashRepository

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val carwashRepository = CarwashRepository(database.carwashDao)

    val carwashes = carwashRepository.carwashes


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