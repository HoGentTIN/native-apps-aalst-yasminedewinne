package be.hogent.yasminedewinne.carwashapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.hogent.yasminedewinne.carwashapp.models.domain.repositories.CarwashRepository

class CarwashViewModel (application: Application): AndroidViewModel(application) {


    class Factory(private val application: Application) : ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CarwashViewModel::class.java))
                return CarwashViewModel(application) as T

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}