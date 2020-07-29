package be.hogent.yasminedewinne.carwashapp.ui.carwash


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders

import be.hogent.yasminedewinne.carwashapp.R
import be.hogent.yasminedewinne.carwashapp.databinding.CarwashFragmentMainBinding
import be.hogent.yasminedewinne.carwashapp.viewmodels.CarwashViewModel
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import java.time.LocalDate
import java.time.LocalTime

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

        binding.btnCarwashBevestig.setOnClickListener {
            binding.viewModel?.auto?.value = binding.txtCarwashAuto.editText?.text.toString()
            //binding.viewModel?.tarief?.value = binding.txtCarwashTarief.editText?.text.
            binding.viewModel?.uitleg?.value = binding.txtCarwashUitleg.editText?.text.toString()
            binding.viewModel?.carwashAfwerken()
            Toast.makeText(this.context, "De carwash werd succesvol gepost! ", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun registerListeners(){

        binding.btnCarwashDatum.setOnClickListener {
            var initTime = LocalDate.now()
            val currentDate = binding.viewModel?.datum?.value
            if(currentDate != null)
                initTime = currentDate

            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val date = LocalDate.of(year, month+1, dayOfMonth)

                binding.viewModel?.datum?.value = date
            }
            DatePickerDialog(context!!, dateSetListener, initTime.year, initTime.monthValue-1, initTime.dayOfMonth).show()
        }

        binding.btnCarwashUur.setOnClickListener {
            var initTime = LocalTime.now()
            val currentTime = binding.viewModel?.beginUur?.value
            if (currentTime != null)
                initTime = currentTime

            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val time = LocalTime.of(hourOfDay, minute)

                binding.viewModel?.beginUur?.value = time
            }
            TimePickerDialog(context, timeSetListener, initTime.hour, initTime.minute, true).show()
        }
    }
}
