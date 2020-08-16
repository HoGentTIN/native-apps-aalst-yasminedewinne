package be.hogent.yasminedewinne.carwashapp.ui.carwash

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import be.hogent.yasminedewinne.carwashapp.databinding.CarwashFragmentMainBinding
import be.hogent.yasminedewinne.carwashapp.models.domain.Auto
import be.hogent.yasminedewinne.carwashapp.viewmodels.CarwashViewModel
import be.hogent.yasminedewinne.carwashapp.viewmodels.adapters.AutoAdapter
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CarwashFragmentMainBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        registerListeners()
        startObservers()

        binding.btnCarwashBevestig.setOnClickListener {
            binding.viewModel?.tarief?.value = binding.txtCarwashTarief.editText?.text.toString().toInt()
            binding.viewModel?.uitleg?.value = binding.txtCarwashUitleg.editText?.text.toString()
            binding.viewModel?.carwashAfwerken()
            Toast.makeText(this.context, "De carwash werd succesvol gepost! ", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun startObservers() {
        binding.viewModel?.autos?.observe(this, Observer { autos ->
            val adapter = AutoAdapter(requireContext(), autos)
            binding.spinnerCarwashAuto.adapter = adapter
            binding.spinnerCarwashAuto.setSelection(0)
        })
    }

    private fun registerListeners() {

        binding.btnCarwashDatum.setOnClickListener {
            var initTime = LocalDate.now()
            val currentDate = binding.viewModel?.datum?.value
            if (currentDate != null)
                initTime = currentDate

            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val date = LocalDate.of(year, month + 1, dayOfMonth)

                binding.viewModel?.datum?.value = date
                binding.btnCarwashDatum.text = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            }
            DatePickerDialog(context!!, dateSetListener, initTime.year, initTime.monthValue - 1, initTime.dayOfMonth).show()
        }

        binding.btnCarwashBeginTijd.setOnClickListener {
            var initTime = LocalTime.now()
            val currentTime = binding.viewModel?.beginUur?.value
            if (currentTime != null)
                initTime = currentTime

            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val time = LocalTime.of(hourOfDay, minute)

                binding.viewModel?.beginUur?.value = time
                binding.btnCarwashBeginTijd.text = time.toString()
            }
            TimePickerDialog(context, timeSetListener, initTime.hour, initTime.minute, true).show()
        }

        binding.btnCarwashEindTijd.setOnClickListener {
            var initTime = LocalTime.now()
            val currentTime = binding.viewModel?.eindUur?.value
            if (currentTime != null)
                initTime = currentTime

            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val time = LocalTime.of(hourOfDay, minute)

                binding.viewModel?.eindUur?.value = time
                binding.btnCarwashEindTijd.text = time.toString()
            }
            TimePickerDialog(context, timeSetListener, initTime.hour, initTime.minute, true).show()
        }

        binding.spinnerCarwashAuto.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val auto = binding.spinnerCarwashAuto.selectedItem as Auto
                binding.viewModel?.autoId?.value = auto.id
            }
        }
    }
}
