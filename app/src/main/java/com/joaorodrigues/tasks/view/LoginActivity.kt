package com.joaorodrigues.tasks.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.joaorodrigues.tasks.R
import com.joaorodrigues.tasks.databinding.ActivityLoginBinding
import com.joaorodrigues.tasks.service.helper.BiometricHelper
import com.joaorodrigues.tasks.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener(this)
        binding.textRegister.setOnClickListener(this)

        viewModel.verifyAuthentication()

        observe()
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.buttonLogin.id -> {
                handleLogin()
            }

            binding.textRegister.id -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

    private fun observe() {
        viewModel.login.observe(this) {
            if (it.success()) {
                biometricAuthentication()
            } else {
                Toast.makeText(this, it.message(), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loggedUser.observe(this) {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

    }

    private fun handleLogin() {
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()
        viewModel.doLogin(email, password)
    }

    private fun biometricAuthentication() {

        if (BiometricHelper.isBiometricAvailable(this)) {
            val executor = ContextCompat.getMainExecutor(this)
            val biometric =androidx.biometric.BiometricPrompt(this, executor, object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                }

            })

            val promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.biometric_login_for_my_app))
                .setSubtitle(getString(R.string.log_in_using_your_biometric_credential))
                .setDescription(getString(R.string.touch_your_finger_on_the_sensor_to_login))
                .setNegativeButtonText(getString(R.string.cancel))
                .build()

            biometric.authenticate(promptInfo)
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


    }

}