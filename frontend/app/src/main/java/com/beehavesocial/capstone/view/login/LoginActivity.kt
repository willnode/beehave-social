package com.beehavesocial.capstone.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels


import com.beehavesocial.capstone.databinding.ActivityLoginBinding
import com.beehavesocial.capstone.view.HomeActivity
import com.beehavesocial.capstone.view.register.RegisterActivity
import com.beehavesocial.capstone.view.register.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private val loginViewModel:LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnLogin.setOnClickListener {
            val username = binding.emailInput.text
            val password = binding.passwordInput.text

            loginViewModel.login(username.toString(),password.toString())
            loginViewModel.action.observe(this,{
                when(it){
                    LoginViewModel.ACTION_LOGIN_SUCCESS -> {
                        Snackbar.make(binding.root, "Berhasil Login", Snackbar.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    LoginViewModel.ACTION_LOGIN_ERROR -> loginError()
                    LoginViewModel.ACTION_LOGIN_FAILED -> loginFailed()
                }
            })

            }


        binding.btnSignup.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }



        }

    private fun loginFailed() {
        Snackbar.make(binding.root, "Login Error", Snackbar.LENGTH_SHORT).show()
    }

    private fun loginError() {
        Snackbar.make(binding.root, "Login Gagal", Snackbar.LENGTH_SHORT).show()
    }



    }



