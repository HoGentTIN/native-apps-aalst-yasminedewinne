package be.hogent.yasminedewinne.carwashapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import be.hogent.yasminedewinne.carwashapp.R
import be.hogent.yasminedewinne.carwashapp.databinding.ActivityLoadingBinding
import be.hogent.yasminedewinne.carwashapp.viewmodels.LoadingViewModel

class LoadingActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoadingBinding

    private val viewModel: LoadingViewModel by lazy {
        ViewModelProviders
            .of(this, LoadingViewModel.Factory(application))
            .get(LoadingViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_loading)

        viewModel.isLoading.observe(this, Observer { loadingResult ->
            setResult(loadingResult)
            finish()
        })
    }
}