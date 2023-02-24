package com.ranjan.openweather.view.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.ranjan.openweather.R
import com.ranjan.openweather.common.EncryptedSharedPreference
import com.ranjan.openweather.view.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var preferences: EncryptedSharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        setUpSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isUserLoggedIn = preferences.getBoolean("isLogin", false)
        if (isUserLoggedIn) {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setUpSplashScreen() {
        var keepScreenOn = true
        lifecycleScope.launch {
            delay(1500)
            keepScreenOn = false
        }
        installSplashScreen().setKeepOnScreenCondition {
            keepScreenOn
        }
    }
}