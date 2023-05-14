package com.example.icesiapp231

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.icesiapp231.databinding.ActivityMainBinding
import com.example.icesiapp231.fragments.ProfileFragment
import com.example.icesiapp231.fragments.WorldFragment
import com.example.icesiapp231.viewmodels.AuthState
import com.example.icesiapp231.viewmodels.AuthViewModel

class MainActivity : AppCompatActivity() {

    val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    val profileFragment = ProfileFragment.newInstance()
    val worldFragment = WorldFragment.newInstance()

    val authViewModel:AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        requestPermissions(
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
            ), 1
        )

        authViewModel.status.observe(this){
            when(it){
                AuthState.NO_AUTH->{
                    startActivity(
                        Intent(this, LoginActivity::class.java)
                    )
                    finish()
                }
                AuthState.AUTH->{
                    loadFragment(profileFragment)
                }
            }
        }
        authViewModel.getAuthStatus()

        binding.mainBottomBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.profile -> loadFragment(profileFragment)
                R.id.world -> loadFragment(worldFragment)
            }
            true
        }
    }
    fun loadFragment(fragment:Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFragmentContainer, fragment)
            .commit()
    }
}