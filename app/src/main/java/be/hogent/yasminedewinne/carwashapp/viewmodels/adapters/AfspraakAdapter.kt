package be.hogent.yasminedewinne.carwashapp.viewmodels.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import be.hogent.yasminedewinne.carwashapp.databinding.ItemListAfspraakBinding
import be.hogent.yasminedewinne.carwashapp.models.domain.Afspraak
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_ITEM = 1

class AfspraakAdapter(val clickListener: AfspraakItemClickListener) :
    ListAdapter<AfspraakAdapter.DataItem, RecyclerView.ViewHolder>(AfspraakDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun setList(list: List<Afspraak>?) {
        adapterScope.launch {
            val items = list?.map { DataItem.AfspraakItem(it) }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val afspraakItem = getItem(position) as DataItem.AfspraakItem
                holder.bind(clickListener, afspraakItem.afspraak)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.AfspraakItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class ViewHolder private constructor(private val binding: ItemListAfspraakBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: AfspraakItemClickListener, item: Afspraak) {
            binding.afspraak = item
            binding.clickListener = clickListener

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListAfspraakBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    class AfspraakDiffCallback : DiffUtil.ItemCallback<AfspraakAdapter.DataItem>() {

        override fun areItemsTheSame(oldItem: AfspraakAdapter.DataItem, newItem: AfspraakAdapter.DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AfspraakAdapter.DataItem, newItem: AfspraakAdapter.DataItem): Boolean {
            return oldItem == newItem
        }
    }

    sealed class DataItem {
        data class AfspraakItem(val afspraak: Afspraak) : DataItem() {
            override val id = afspraak.id
        }

        abstract val id: Int
    }
}

class AfspraakItemClickListener(val clickListener: (afspraakId: Int) -> Unit) {
    fun onClick(afspraak: Afspraak) = clickListener(afspraak.id)
}
