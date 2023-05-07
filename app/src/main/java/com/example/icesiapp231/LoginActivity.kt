package com.example.icesiapp231

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.icesiapp231.databinding.ActivityLoginBinding
import com.example.icesiapp231.viewmodels.AuthState
import com.example.icesiapp231.viewmodels.AuthViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    val authviewmodel:AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        authviewmodel.status.observe(this){
            when(it){
                AuthState.LOADING->{}
                AuthState.AUTH->{
                    startActivity(
                        Intent(this@LoginActivity, MainActivity::class.java)
                    )
                }
                AuthState.AUTH_ERROR_LOGIN->{
                    Toast.makeText(this@LoginActivity,
                        "Error de login", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.loginBtn.setOnClickListener {
            authviewmodel.login(
                binding.emailInput.editText?.text.toString(),
                binding.passwordInput.editText?.text.toString()
            )
        }
    }
}