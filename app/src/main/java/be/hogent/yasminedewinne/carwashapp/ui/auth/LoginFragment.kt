package be.hogent.yasminedewinne.carwashapp.ui.auth


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import be.hogent.yasminedewinne.carwashapp.R
import be.hogent.yasminedewinne.carwashapp.databinding.AccountFragmentLoginBinding
import be.hogent.yasminedewinne.carwashapp.viewmodels.LoginViewModel
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.NotEmpty

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    private lateinit var binding: AccountFragmentLoginBinding

    private val viewModel: LoginViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        ViewModelProviders
            .of(this, LoginViewModel.Factory(activity.application))
            .get(LoginViewModel::class.java)
    }

    /*****  VALIDATIE  *****/
    private lateinit var validator: Validator

    @NotEmpty(messageResId = R.string.error_empty)
    private lateinit var txtEmail: EditText

    @NotEmpty(messageResId = R.string.error_empty)
    private lateinit var txtWachtwoord: EditText

    /*****  EINDE VALIDATIE  *****/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = AccountFragmentLoginBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnLoginRegistreren.setOnClickListener { view:View->
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return binding.root
    }


}
