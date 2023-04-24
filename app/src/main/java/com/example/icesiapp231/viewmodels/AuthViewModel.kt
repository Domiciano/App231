package com.example.icesiapp231.viewmodels

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.icesiapp231.MainActivity
import com.example.icesiapp231.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthViewModel : ViewModel() {

    val status: MutableLiveData<Int> = MutableLiveData()

    fun signup(user: User, password: String) {
        status.value = AuthState.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response = Firebase.auth.createUserWithEmailAndPassword(
                    user.email,
                    password
                ).await()
                user.id = response.user!!.uid
                Firebase.firestore
                    .collection("users")
                    .document(response.user!!.uid)
                    .set(user).await()
                withContext(Dispatchers.Main) { status.value = AuthState.AUTH }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(">>>", e.message.toString())
                withContext(Dispatchers.Main) { status.value = AuthState.NO_AUTH }
            }
        }

    }

    fun login(email: String, pass: String) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                Firebase.auth.signInWithEmailAndPassword(
                    email,
                    pass,
                ).await()
                withContext(Dispatchers.Main) {

                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {

                }
            }
        }
    }
}

object AuthState{
    const val NO_AUTH = 0;
    const val LOADING = 1;
    const val AUTH = 2;
}