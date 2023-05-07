package com.example.icesiapp231

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.icesiapp231.databinding.ActivityLoginBinding
import com.example.icesiapp231.databinding.ActivityMainBinding
import com.example.icesiapp231.fragments.ProfileFragment
import com.example.icesiapp231.model.User
import com.example.icesiapp231.viewmodels.AuthState
import com.example.icesiapp231.viewmodels.AuthViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    val profileFragment = ProfileFragment.newInstance()

    val authViewModel:AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        authViewModel.status.observe(this){
            when(it){
                AuthState.NO_AUTH->{
                    startActivity(
                        Intent(this, LoginActivity::class.java)
                    )
                    finish()
                }
                AuthState.AUTH->{
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainFragmentContainer, profileFragment)
                        .commit()
                }
            }
        }
        authViewModel.getAuthStatus()



    }
}