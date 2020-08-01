package be.hogent.yasminedewinne.carwashapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.hogent.yasminedewinne.carwashapp.data.database.getDatabase
import be.hogent.yasminedewinne.carwashapp.models.domain.repositories.AfspraakRepository

class AfspraakViewModel(application: Application): AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val afspraakRepository = AfspraakRepository(database.afspraakDao)

    val komendeAfspraken = afspraakRepository.komendeAfspraken
    val afgelopenAfspraken = afspraakRepository.afgelopenAfspraken

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