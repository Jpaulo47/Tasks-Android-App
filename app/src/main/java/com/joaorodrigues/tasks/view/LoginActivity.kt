package com.joaorodrigues.tasks.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.joaorodrigues.tasks.viewmodel.LoginViewModel
import com.joaorodrigues.tasks.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener(this)
        binding.textRegister.setOnClickListener(this)

        observe()
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.buttonLogin.id -> {
                handleLogin()
            }

            binding.textRegister.id -> {
                val email = binding.editEmail.text.toString()
                val password = binding.editPassword.text.toString()
                //viewModel.create(email, password)
            }
        }
    }

    private fun observe() {
    }

    private fun handleLogin() {
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()
        viewModel.doLogin(email, password)
    }
}