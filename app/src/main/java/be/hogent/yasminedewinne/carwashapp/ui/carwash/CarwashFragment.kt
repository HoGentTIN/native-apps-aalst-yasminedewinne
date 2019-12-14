package be.hogent.yasminedewinne.carwashapp.ui.carwash


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import be.hogent.yasminedewinne.carwashapp.R
import be.hogent.yasminedewinne.carwashapp.databinding.CarwashFragmentMainBinding

/**
 * A simple [Fragment] subclass.
 */
class CarwashFragment : Fragment() {

    private lateinit var binding: CarwashFragmentMainBinding

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = CarwashFragmentMainBinding.inflate(inflater)

        return binding.root
    }


}
