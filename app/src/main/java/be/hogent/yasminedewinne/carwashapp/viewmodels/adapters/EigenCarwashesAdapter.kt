package be.hogent.yasminedewinne.carwashapp.viewmodels.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import be.hogent.yasminedewinne.carwashapp.databinding.ItemListEigenCarwashesBinding
import be.hogent.yasminedewinne.carwashapp.models.domain.Carwash
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EigenCarwashesAdapter(clickListener: (Int) -> Unit) : ListAdapter<Carwash, RecyclerView.ViewHolder>(TaskDiffCallback()) {

    companion object {
        private const val ITEM_VIEW_TYPE_ITEM = 1
    }

    private val clickListener = ClickListener(clickListener)
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun setList(list: List<Carwash>?) {
        adapterScope.launch {
            withContext(Dispatchers.Main) {
                submitList(list)
            }
        }
    }

    fun getItemAt(position: Int) = getItem(position)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val item = getItem(position)
                holder.bind(clickListener, item)
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
        return ITEM_VIEW_TYPE_ITEM
    }

    class ViewHolder private constructor(private val binding: ItemListEigenCarwashesBinding) : RecyclerView.ViewHolder(binding.root) {

        val viewForeground = binding.viewForeGround!!

        fun bind(clickListener: ClickListener, item: Carwash) {
            binding.carwash = item
            binding.clickListener = clickListener

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListEigenCarwashesBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    class ClickListener(val clickListener: (Int) -> Unit) {
        fun onClick(item: Carwash) = clickListener(item.id)
    }

    private class TaskDiffCallback : DiffUtil.ItemCallback<Carwash>() {

        override fun areItemsTheSame(oldItem: Carwash, newItem: Carwash): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Carwash, newItem: Carwash): Boolean {
            return oldItem == newItem
        }
    }
}
