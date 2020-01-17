package be.hogent.yasminedewinne.carwashapp.ui.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import be.hogent.yasminedewinne.carwashapp.R
import be.hogent.yasminedewinne.carwashapp.databinding.HomeFragmentMainBinding
import be.hogent.yasminedewinne.carwashapp.viewmodels.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentMainBinding

    private val viewModel: HomeViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        ViewModelProviders
            .of(this, HomeViewModel.Factory(activity.application))
            .get(HomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = HomeFragmentMainBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

       binding.btnHomeNieuweAanbieding.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_carwashFragment)
        )

        return binding.root
    }


}
