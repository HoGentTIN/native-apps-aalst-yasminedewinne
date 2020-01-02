package be.hogent.yasminedewinne.carwashapp.ui.carwash


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import be.hogent.yasminedewinne.carwashapp.R
import be.hogent.yasminedewinne.carwashapp.databinding.CarwashFragmentMainBinding
import be.hogent.yasminedewinne.carwashapp.viewmodels.CarwashViewModel
import java.time.LocalDate
import java.time.LocalTime

/**
 * A simple [Fragment] subclass.
 */
class CarwashFragment : Fragment() {

    private lateinit var binding: CarwashFragmentMainBinding

    private val viewModel: CarwashViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        ViewModelProviders
            .of(this, CarwashViewModel.Factory(activity.application))
            .get(CarwashViewModel::class.java)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = CarwashFragmentMainBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        registerListeners()

        return binding.root
    }

    private fun registerListeners(){

        binding.btnCarwashDatum.setOnClickListener {
            var initTime = LocalDate.now()

            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val date = LocalDate.of(year, month, dayOfMonth)
            }
            DatePickerDialog(context!!, dateSetListener, initTime.year, initTime.monthValue-1, initTime.dayOfMonth).show()
        }

        binding.btnCarwashUur.setOnClickListener {
            var initTime = LocalTime.now()

            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val time = LocalTime.of(hourOfDay, minute)
            }
            TimePickerDialog(context, timeSetListener, initTime.hour, initTime.minute, true).show()
        }
    }
}
