package com.example.icesiapp231.viewmodels

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.*
import com.example.icesiapp231.model.Post
import com.example.icesiapp231.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class PostViewModel:ViewModel() {


    private val data = arrayListOf<Post>()
    private val _posts = MutableLiveData(data)
    val posts get() = _posts

    fun getWorldPosts(){
        viewModelScope.launch(Dispatchers.Main) {
            val query = Firebase.firestore
                .collection("posts")
                .get().await()
            for (change in query.documentChanges){
                if(change.type == DocumentChange.Type.ADDED) {
                    val post = change.document.toObject(Post::class.java)
                    data.add(post)
                    val downloadURL = Firebase.storage.reference.child(post.userID).child(post.photoID).downloadUrl.await()
                    post.photoURL = downloadURL.toString()
                    withContext(Dispatchers.Main){
                        _posts.value = data
                    }
                }
            }

        }
    }



}