package be.hogent.yasminedewinne.carwashapp.ui.afspraak


import android.app.Application
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import be.hogent.yasminedewinne.carwashapp.R
import be.hogent.yasminedewinne.carwashapp.databinding.FragmentAfspraakDetailsDialogBinding
import be.hogent.yasminedewinne.carwashapp.viewmodels.AfspraakDetailsDialogViewModel

/**
 * A simple [Fragment] subclass.
 */
class AfspraakDetailsDialogFragment(afspraakId: Int) : DialogFragment() {

    private lateinit var binding: FragmentAfspraakDetailsDialogBinding
    private val afspraakId = afspraakId

    private val viewModel: AfspraakDetailsDialogViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        ViewModelProviders
            .of(this, AfspraakDetailsDialogViewModel.Factory(activity!!.application, afspraakId))
            .get(AfspraakDetailsDialogViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAfspraakDetailsDialogBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnClose.setOnClickListener {
            this.dialog?.cancel()
        }

        binding.btnVerwijderen.setOnClickListener {
            binding.viewModel?.afspraakVerwijderen(afspraakId)
            Toast.makeText(this.context, "Uw afspraak werd verwijderd.", Toast.LENGTH_SHORT).show()
            this.dialog?.dismiss()
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        return dialog
    }
}
