package be.hogent.yasminedewinne.carwashapp.viewmodels.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import be.hogent.yasminedewinne.carwashapp.databinding.ItemListCarwashBinding
import be.hogent.yasminedewinne.carwashapp.models.domain.Carwash
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_ITEM = 1

class CarwashAdapter(private val clickListener: CarwashItemClickListener) :
    ListAdapter<CarwashAdapter.DataItem, RecyclerView.ViewHolder>(CarwashDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun setList(list: List<Carwash>?) {
        adapterScope.launch {
            val items = list?.map { DataItem.CarwashItem(it) }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val carwashItem = getItem(position) as DataItem.CarwashItem
                holder.bind(clickListener, carwashItem.carwash)
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
            is DataItem.CarwashItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class ViewHolder private constructor(private val binding: ItemListCarwashBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: CarwashItemClickListener, item: Carwash) {
            binding.carwash = item
            binding.clickListener = clickListener

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListCarwashBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    class CarwashDiffCallback : DiffUtil.ItemCallback<DataItem>() {

        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }

    sealed class DataItem {
        data class CarwashItem(val carwash: Carwash) : DataItem() {
            override val id = carwash.id
        }

        abstract val id: Int
    }
}

class CarwashItemClickListener(val clickListener: (carwashId: Int) -> Unit) {
    fun onClick(carwash: Carwash) = clickListener(carwash.id)
}
