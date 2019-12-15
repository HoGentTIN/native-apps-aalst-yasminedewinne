package be.hogent.yasminedewinne.carwashapp.ui.afspraak


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import be.hogent.yasminedewinne.carwashapp.R
import be.hogent.yasminedewinne.carwashapp.databinding.AfspraakFragmentMainBinding

/**
 * A simple [Fragment] subclass.
 */
class AfspraakFragment : Fragment() {

    private lateinit var binding: AfspraakFragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = AfspraakFragmentMainBinding.inflate(inflater)

        return binding.root
    }


}
