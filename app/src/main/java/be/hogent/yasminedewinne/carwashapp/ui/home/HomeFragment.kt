package be.hogent.yasminedewinne.carwashapp.ui.home


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import be.hogent.yasminedewinne.carwashapp.R
import be.hogent.yasminedewinne.carwashapp.databinding.HomeFragmentMainBinding
import be.hogent.yasminedewinne.carwashapp.models.domain.Carwash
import be.hogent.yasminedewinne.carwashapp.viewmodels.HomeViewModel
import be.hogent.yasminedewinne.carwashapp.viewmodels.adapters.CarwashAdapter
import be.hogent.yasminedewinne.carwashapp.viewmodels.adapters.CarwashItemClickListener
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentMainBinding
    lateinit var carwashAdapter: CarwashAdapter

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
        activity?.title = resources.getString(R.string.title_alle_carwashes)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        carwashAdapter = CarwashAdapter(CarwashItemClickListener { carwashId ->
            val dialog = CarwashDetailsDialogFragment(carwashId)
            val fm = this.fragmentManager
            dialog.show(fm!!, "")
        })

        binding.recyclerHomeCarwashes.layoutManager = LinearLayoutManager(context)
        binding.recyclerHomeCarwashes.adapter = carwashAdapter

        binding.btnHomeNieuweAanbieding.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_carwashFragment)
        )

        registerListeners()
        startObservers()

        return binding.root
    }

    private fun registerListeners() {
        binding.pullrefreshMain.setOnRefreshListener {
            binding.viewModel?.refreshData()
        }
    }

    private fun startObservers(){

        binding.viewModel?.carwashes?.observe(this, Observer { carwashes: List<Carwash> ->
            if(carwashes.isEmpty()){
                Snackbar.make(view!!, "Er zijn geen nieuwe carwashes besschikbaar", Snackbar.LENGTH_LONG)

                //Clear list
                carwashAdapter.setList(arrayListOf())
            }else{
                carwashAdapter.setList(carwashes)
            }
        })

        binding.viewModel?.isLoading?.observe(this, Observer { isLoading: Boolean? ->
            if (isLoading != null && !isLoading)
                binding.pullrefreshMain.isRefreshing = false
        })
    }
}