package be.hogent.yasminedewinne.carwashapp.ui.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import be.hogent.yasminedewinne.carwashapp.R
import be.hogent.yasminedewinne.carwashapp.databinding.HomeFragmentMainBinding

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = HomeFragmentMainBinding.inflate(inflater)

        binding.btnHomeNieuweAanbieding.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_carwashFragment)
        )

        return binding.root
    }


}
