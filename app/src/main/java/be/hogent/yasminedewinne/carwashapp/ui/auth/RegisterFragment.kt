package be.hogent.yasminedewinne.carwashapp.ui.auth

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import be.hogent.yasminedewinne.carwashapp.R
import be.hogent.yasminedewinne.carwashapp.databinding.AccountFragmentRegisterBinding
import be.hogent.yasminedewinne.carwashapp.models.domain.Adres
import be.hogent.yasminedewinne.carwashapp.viewmodels.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.*

class RegisterFragment : Fragment(), Validator.ValidationListener {

    private lateinit var binding: AccountFragmentRegisterBinding
    private lateinit var isLoadingProgress: ProgressDialog

    private val viewModel: RegisterViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        ViewModelProviders
            .of(this, RegisterViewModel.Factory(activity.application))
            .get(RegisterViewModel::class.java)
    }

    /*****  VALIDATIE  *****/
    private lateinit var validator: Validator

    @NotEmpty(messageResId = R.string.error_empty)
    private lateinit var txtVoornaam: EditText

    @NotEmpty(messageResId = R.string.error_empty)
    private lateinit var txtFamilienaam: EditText

    @NotEmpty(messageResId = R.string.error_empty)
    @Email(messageResId = R.string.error_invalid_email)
    private lateinit var txtEmail: EditText

    @NotEmpty(messageResId = R.string.error_empty)
    @Pattern(regex =
    "(\\+[0-9]+[\\- \\.]*)?" +
            "(\\([0-9]+\\)[\\- \\.]*)?" +
            "([0-9][0-9\\- \\.]+[0-9])", messageResId = R.string.error_invalid_telnr)
    private lateinit var txtTelNr: EditText

    @NotEmpty(messageResId = R.string.error_empty)
    private lateinit var txtStraatnaam: EditText

    @NotEmpty(messageResId = R.string.error_empty)
    private lateinit var txtHuisnr: EditText

    @Pattern(regex = "([0-9]{4})")
    @NotEmpty(messageResId = R.string.error_empty)
    private lateinit var txtPostcode: EditText

    @NotEmpty(messageResId = R.string.error_empty)
    private lateinit var txtStad: EditText

    @NotEmpty(messageResId = R.string.error_empty)
    private lateinit var txtLand: EditText

    @Password(min = 6, scheme = Password.Scheme.ALPHA, messageResId = R.string.error_invalid_password)
    private lateinit var txtWachtwoord: EditText

    @ConfirmPassword(messageResId = R.string.error_invalid_passwordConfirm)
    private lateinit var txtWachtwoordHerhalen: EditText

    /*****  EINDE VALIDATIE  *****/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = AccountFragmentRegisterBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        registerListeners()
        startObservers()
        registerValidation()

        return binding.root
    }

    private fun registerValidation() {
        this.validator = Validator(this)
        this.validator.setValidationListener(this)

        this.txtVoornaam = binding.txtRegisterVoornaam.editText!!
        this.txtFamilienaam = binding.txtRegisterFamilienaam.editText!!
        this.txtEmail = binding.txtRegisterEmail.editText!!
        this.txtTelNr = binding.txtRegisterTelnr.editText!!
        this.txtStraatnaam = binding.txtRegisterStraatnaam.editText!!
        this.txtHuisnr = binding.txtRegisterHuisnr.editText!!
        this.txtPostcode = binding.txtRegisterPostcode.editText!!
        this.txtStad = binding.txtRegisterStad.editText!!
        this.txtLand = binding.txtRegisterLand.editText!!
        this.txtWachtwoord = binding.txtRegisterWachtwoord.editText!!
        this.txtWachtwoordHerhalen = binding.txtRegisterWachtwoordHerhalen.editText!!
    }

    private fun startObservers() {
        binding.viewModel?.registerResponse?.observe(this, Observer { httpCode: Int? ->
            if (httpCode != null) {
                when (httpCode) {
                    200 -> {
                        activity?.setResult(Activity.RESULT_OK)
                        activity?.finish()
                    }
                    400 -> Snackbar.make(
                        binding.btnRegistreren,
                        R.string.error_register_failed,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    504 -> Snackbar.make(
                        binding.btnRegistreren,
                        R.string.httperror_504,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    else -> Snackbar.make(
                        binding.btnRegistreren,
                        R.string.httperror_400,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                isLoadingProgress.hide()
            }
        })
    }

    private fun registerListeners() {
        // binding.imgRegisterProfile.setOnClickListener { pickUserPicture() }
        binding.btnRegistreren.setOnClickListener { this.validator.validate() }
        /*binding.btnRegistrerenClearPicture.setOnClickListener {
            binding.imgRegisterProfile.setImageResource(R.drawable.profile)
            binding.viewModel?.changePicture(null)
        }*/
    }

    /*private fun pickUserPicture() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                binding.viewModel?.changePicture(data.data)
                binding.imgRegisterProfile.setImageBitmap(binding.viewModel?.picture)
            }
        }
    }
    */*/

    private fun registerUser() {
        val voornaam = binding.txtRegisterVoornaam.editText?.text.toString()
        val familienaam = binding.txtRegisterFamilienaam.editText?.text.toString()
        val email = binding.txtRegisterEmail.editText?.text.toString()
        val telnr = binding.txtRegisterTelnr.editText?.text.toString()

        val straat = binding.txtRegisterStraatnaam.editText?.text.toString()
        val huisnr = binding.txtRegisterHuisnr.editText?.text.toString()
        val postcode = binding.txtRegisterPostcode.editText?.text.toString()
        val stad = binding.txtRegisterStad.editText?.text.toString()
        val land = binding.txtRegisterLand.editText?.text.toString()
        val adres = Adres(
            straatNaam = straat,
            huisNr = huisnr,
            postcode = postcode,
            stad = stad,
            land = land
        )

        val wachtwoord = binding.txtRegisterWachtwoord.editText?.text.toString()
        val wachtwoordHerhaling = binding.txtRegisterWachtwoordHerhalen.editText?.text.toString()

        isLoadingProgress = ProgressDialog(requireContext())
        isLoadingProgress.setMessage(resources.getString(R.string.title_registreren))
        isLoadingProgress.setCanceledOnTouchOutside(false)
        isLoadingProgress.setCancelable(false)
        isLoadingProgress.show()

        binding.viewModel?.registerUser(voornaam, familienaam, email, telnr, adres, wachtwoord, wachtwoordHerhaling)
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        for (error in errors!!) {
            val view = error.view
            val message = error.getCollatedErrorMessage(requireContext())

            if (view is EditText) {
                view.error = message
            }
        }
    }

    override fun onValidationSucceeded() {
        registerUser()
    }
}
