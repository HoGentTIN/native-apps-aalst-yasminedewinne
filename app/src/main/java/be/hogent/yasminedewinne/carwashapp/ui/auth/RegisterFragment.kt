package be.hogent.yasminedewinne.carwashapp.ui.auth


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import be.hogent.yasminedewinne.carwashapp.R
import be.hogent.yasminedewinne.carwashapp.databinding.AccountFragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: AccountFragmentRegisterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = AccountFragmentRegisterBinding.inflate(inflater)

        return binding.root
    }


}
