package be.hogent.yasminedewinne.carwashapp.ui.afspraak


import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.hogent.yasminedewinne.carwashapp.App

import be.hogent.yasminedewinne.carwashapp.R
import be.hogent.yasminedewinne.carwashapp.databinding.AfspraakFragmentMainBinding
import be.hogent.yasminedewinne.carwashapp.models.domain.Afspraak
import be.hogent.yasminedewinne.carwashapp.models.domain.Carwash
import be.hogent.yasminedewinne.carwashapp.ui.activity.StartupActivity
import be.hogent.yasminedewinne.carwashapp.viewmodels.AfspraakViewModel
import be.hogent.yasminedewinne.carwashapp.viewmodels.adapters.*

/**
 * A simple [Fragment] subclass.
 */
class AfspraakFragment : Fragment() {

    private lateinit var binding: AfspraakFragmentMainBinding
    lateinit var komendeAdapter: AfspraakAdapter
    lateinit var afgelopenAdapter: AfspraakAdapter
    lateinit var eigenCarwashesAdapter: EigenCarwashesAdapter

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

        eigenCarwashesAdapter = EigenCarwashesAdapter { carwashId ->
        }

        binding.recyclerAfsprakenKomende.layoutManager = LinearLayoutManager(context)
        binding.recyclerAfsprakenKomende.adapter = komendeAdapter

        binding.recyclerAfsprakenHistoriek.layoutManager = LinearLayoutManager(context)
        binding.recyclerAfsprakenHistoriek.adapter = afgelopenAdapter

        binding.recyclerEigenCarwashes.layoutManager = LinearLayoutManager(context)
        binding.recyclerEigenCarwashes.adapter = eigenCarwashesAdapter
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerEigenCarwashes)

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
        binding.viewModel?.eigenCarwashes?.observe(this, Observer { eigenCarwashes: List<Carwash> ->
            eigenCarwashesAdapter.setList(eigenCarwashes)
        })

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

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val carwash = eigenCarwashesAdapter.getItemAt(viewHolder.adapterPosition)
            binding.viewModel?.carwashVerwijderen(carwash.id)

            // Reset swipe
            eigenCarwashesAdapter.notifyItemRemoved(viewHolder.adapterPosition)
        }

        /*

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            val foregroundView = (viewHolder as EigenCarwashesAdapter.ViewHolder).viewForeground
            getDefaultUIUtil().clearView(foregroundView)
        }

        override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            val foregroundView = (viewHolder as EigenCarwashesAdapter.ViewHolder).viewForeground
            getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive)
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            val foregroundView = (viewHolder as EigenCarwashesAdapter.ViewHolder).viewForeground
            getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive)
        }*/
    }

}
