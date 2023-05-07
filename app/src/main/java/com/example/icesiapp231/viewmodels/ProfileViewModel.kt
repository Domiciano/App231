package com.example.icesiapp231.viewmodels

import android.widget.Toast
import androidx.lifecycle.*
import com.example.icesiapp231.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProfileViewModel:ViewModel() {

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user:LiveData<User> get() = _user

    fun getUser(){
        viewModelScope.launch(Dispatchers.Main) {
            val res = Firebase.firestore.collection("users").document(
                Firebase.auth.currentUser!!.uid
            ).get().await()
            val me = res.toObject(User::class.java)
            withContext(Dispatchers.Main){
                _user.value = me!!
            }
        }
    }

}