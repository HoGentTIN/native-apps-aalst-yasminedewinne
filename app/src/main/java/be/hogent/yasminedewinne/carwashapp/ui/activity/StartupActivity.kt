package be.hogent.yasminedewinne.carwashapp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import be.hogent.yasminedewinne.carwashapp.data.UserHelper
import be.hogent.yasminedewinne.carwashapp.ui.dialog.SetupNoInternetPopupFragment

private const val LOGININ_USER: Int = 0
private const val LOAD_USER_DETAILS: Int = 1

class StartupActivity : AppCompatActivity() {
    private lateinit var userHelper: UserHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userHelper = UserHelper(this)
        runStartup()
    }

    private fun runStartup() {
        if (!userHelper.isSignedIn()) {
            startActivityForResult(Intent(this, AuthActivity::class.java), LOGININ_USER)
            return
        }

        startActivityForResult(Intent(this, LoadingActivity::class.java), LOAD_USER_DETAILS)
    }

    private fun startApp() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LOGININ_USER && resultCode == Activity.RESULT_OK) {
            runStartup()
        } else if (requestCode == LOAD_USER_DETAILS) {
            if (resultCode == Activity.RESULT_OK)
                startApp()
            else {
                SetupNoInternetPopupFragment
                    .newInstance()
                    .show(supportFragmentManager, "dialog")
            }
        }
    }
}
