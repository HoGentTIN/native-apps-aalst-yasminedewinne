package be.hogent.yasminedewinne.carwashapp.ui.afspraak


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import be.hogent.yasminedewinne.carwashapp.App

import be.hogent.yasminedewinne.carwashapp.R
import be.hogent.yasminedewinne.carwashapp.databinding.AfspraakFragmentMainBinding
import be.hogent.yasminedewinne.carwashapp.models.domain.Afspraak
import be.hogent.yasminedewinne.carwashapp.ui.activity.StartupActivity
import be.hogent.yasminedewinne.carwashapp.viewmodels.AfspraakViewModel
import be.hogent.yasminedewinne.carwashapp.viewmodels.adapters.AfspraakAdapter
import be.hogent.yasminedewinne.carwashapp.viewmodels.adapters.AfspraakItemClickListener

/**
 * A simple [Fragment] subclass.
 */
class AfspraakFragment : Fragment() {

    private lateinit var binding: AfspraakFragmentMainBinding
    lateinit var komendeAdapter: AfspraakAdapter
    lateinit var afgelopenAdapter: AfspraakAdapter

    private val viewModel: AfspraakViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onActivityCreated()"
        }

        ViewModelProviders
            .of(this, AfspraakViewModel.Factory(activity.application))
            .get(AfspraakViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = AfspraakFragmentMainBinding.inflate(inflater)
        activity?.title = resources.getString(R.string.title_afspraken)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        komendeAdapter = AfspraakAdapter(AfspraakItemClickListener { afspraakId ->  
            val dialog = AfspraakDetailsDialogFragment(afspraakId)
            val fm = this.fragmentManager
            dialog.show(fm!!, "")
        })

        afgelopenAdapter = AfspraakAdapter(AfspraakItemClickListener{afspraakId ->
            val dialog = AfspraakDetailsDialogFragment(afspraakId)
            val fm = this.fragmentManager
            dialog.show(fm!!, "")
        })

        binding.recyclerAfsprakenKomende.layoutManager = LinearLayoutManager(context)
        binding.recyclerAfsprakenKomende.adapter = komendeAdapter

        binding.recyclerAfsprakenHistoriek.layoutManager = LinearLayoutManager(context)
        binding.recyclerAfsprakenHistoriek.adapter = afgelopenAdapter

        binding.btnAfmelden.setOnClickListener {
            App.getUserHelper().signOut()
            startActivity(Intent(requireContext(), StartupActivity::class.java))
            activity!!.finish()
        }

        registerListeners()
        startObservers()

        return binding.root
    }

    private fun registerListeners() {
        binding.pullrefreshAfspraken.setOnRefreshListener {
            binding.viewModel?.refreshData()
        }
    }

    private fun startObservers(){
        binding.viewModel?.komendeAfspraken?.observe(this, Observer{ komende: List<Afspraak> ->
            komendeAdapter.setList(komende)
        })

        binding.viewModel?.afgelopenAfspraken?.observe(this, Observer { afgelopen: List<Afspraak> ->
            afgelopenAdapter.setList(afgelopen)
        })

        binding.viewModel?.isLoading?.observe(this, Observer { isLoading: Boolean? ->
            if (isLoading != null && !isLoading)
                binding.pullrefreshAfspraken.isRefreshing = false
        })
    }


}
