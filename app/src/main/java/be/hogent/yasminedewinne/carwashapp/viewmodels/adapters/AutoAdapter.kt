package be.hogent.yasminedewinne.carwashapp.viewmodels.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SpinnerAdapter
import be.hogent.yasminedewinne.carwashapp.databinding.ItemDropdownBinding
import be.hogent.yasminedewinne.carwashapp.models.domain.Auto

class AutoAdapter(private val context: Context, private val mItems: List<Auto>) : BaseAdapter(), SpinnerAdapter {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return mItems.size
    }

    override fun getItem(position: Int): Any {
        return mItems[position]
    }

    override fun getItemId(position: Int): Long {
        return mItems[position].id.toLong()
    }

    // default state van de spinner
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemDropdownBinding.inflate(mInflater)

        val item = getItem(position) as Auto
        binding.txtDropdownCenter.text = item.merk + " " + item.naam

        return binding.txtDropdownCenter
    }

    // drop down item view
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemDropdownBinding.inflate(mInflater)

        val item = getItem(position) as Auto
        binding.txtDropdownCenter.text = item.merk + " " + item.naam

        return binding.txtDropdownCenter
    }
}
