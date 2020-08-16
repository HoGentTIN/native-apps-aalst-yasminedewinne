package be.hogent.yasminedewinne.carwashapp.ui.zoek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.hogent.yasminedewinne.carwashapp.databinding.ZoekFragmentMainBinding

/**
 * A simple [Fragment] subclass.
 */
class ZoekFragment : Fragment() {

    private lateinit var binding: ZoekFragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ZoekFragmentMainBinding.inflate(inflater)

        return binding.root
    }
}
