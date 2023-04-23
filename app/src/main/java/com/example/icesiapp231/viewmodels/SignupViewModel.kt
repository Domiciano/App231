package com.example.icesiapp231.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.icesiapp231.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SignupViewModel : ViewModel() {

    val status:MutableLiveData<Int> = MutableLiveData(0)

    fun signup(user: User, password:String){
        status.value = 0
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
                withContext(Dispatchers.Main){status.value = 1}
            }catch (e:Exception){
                e.printStackTrace()
                Log.d(">>>", e.message.toString())
                withContext(Dispatchers.Main){status.value = -1}
            }
        }

    }


}