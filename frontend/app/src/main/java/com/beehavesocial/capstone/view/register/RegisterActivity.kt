package com.beehavesocial.capstone.view.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.beehavesocial.capstone.databinding.ActivityRegisterBinding
import com.beehavesocial.capstone.view.login.LoginActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()



        binding.btnSignup.setOnClickListener {
            val name = binding.nameInput.text
            val email = binding.emailIput.text
            val password = binding.passwordInput.text

            registerViewModel.register(name.toString(),email.toString(),password.toString())
            registerViewModel.action.observe(this, {
                when(it){
                    RegisterViewModel.ACTION_DAFTAR_SUCCESS -> {
                        Snackbar.make(binding.root, "Akun Anda Berhasil Dibuat!", Snackbar.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    RegisterViewModel.ACTION_DAFTAR_ERROR -> daftarError()
                    RegisterViewModel.ACTION_DAFTAR_FAILED -> daftarFailed()
                }
            })
        }

    }
    private fun daftarFailed() {
        Snackbar.make(binding.root, "Daftar Error", Snackbar.LENGTH_SHORT).show()
    }

    private fun daftarError() {
        Snackbar.make(binding.root, "Daftar Gagal", Snackbar.LENGTH_SHORT).show()
    }
}