package be.hogent.yasminedewinne.carwashapp.ui.home


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

import be.hogent.yasminedewinne.carwashapp.R
import be.hogent.yasminedewinne.carwashapp.databinding.FragmentCarwashDetailsDialogBinding
import be.hogent.yasminedewinne.carwashapp.viewmodels.CarwashDetailsDialogViewModel
import kotlinx.android.synthetic.main.fragment_carwash_details_dialog.*

/**
 * A simple [Fragment] subclass.
 */
class CarwashDetailsDialogFragment(carwashId: Int) : DialogFragment() {

    private lateinit var binding: FragmentCarwashDetailsDialogBinding

    private val viewModel: CarwashDetailsDialogViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        ViewModelProviders
            .of(this, CarwashDetailsDialogViewModel.Factory(activity!!.application, carwashId))
            .get(CarwashDetailsDialogViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarwashDetailsDialogBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnAfspraakMaken.setOnClickListener {
            binding.viewModel?.afspraakAfwerken()
            Toast.makeText(this.context, "De afspraak werd succesvol geplaatst! ", Toast.LENGTH_SHORT).show()
            this.dialog?.dismiss()
        }

        binding.btnAnnuleren.setOnClickListener {
            this.dialog?.cancel()
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        return dialog
    }

}
