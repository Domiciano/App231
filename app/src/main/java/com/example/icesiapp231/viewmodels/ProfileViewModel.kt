package com.example.icesiapp231.viewmodels

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.*
import com.example.icesiapp231.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class ProfileViewModel:ViewModel() {

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user:LiveData<User> get() = _user

    fun getUser(){
        viewModelScope.launch(Dispatchers.Main) {
            val res = Firebase.firestore.collection("users").document(
                Firebase.auth.currentUser!!.uid
            ).get().await()
            val me = res.toObject(User::class.java)!!
            withContext(Dispatchers.Main){
                _user.value = me
            }
            val url = Firebase.storage.reference.child(me.id!!).child(me.photoID!!).downloadUrl.await()
            me.photoURL = url.toString()
            withContext(Dispatchers.Main){
                _user.value = me
            }

        }
    }

    fun updateProfile(image:Uri) {
        viewModelScope.launch (Dispatchers.IO){
            val uid = UUID.randomUUID().toString()
            Firebase.storage.reference.child(
                Firebase.auth.currentUser!!.uid
            ).child(
                uid
            ).putFile(image).await()
            Firebase.firestore
                .collection("users")
                .document(Firebase.auth.currentUser!!.uid)
                .update("photoID", uid).await()
            getUser()
        }
    }

}