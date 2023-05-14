package com.example.icesiapp231

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.icesiapp231.databinding.ActivityPostComposerBinding
import com.example.icesiapp231.model.Post
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

class PostComposerActivity : AppCompatActivity() {

    val binding by lazy{
        ActivityPostComposerBinding.inflate(layoutInflater)
    }

    val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), ::onResult)

    private var image: Uri? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.postComposerImage.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/*"
            launcher.launch(i)
        }

        binding.publishBtn.setOnClickListener {
            lifecycleScope.launch (Dispatchers.IO){
                val text = binding.postComposerText.text.toString()
                val id = UUID.randomUUID().toString()
                val photoID = UUID.randomUUID().toString()
                val post = Post(id,
                    text,
                    photoID,
                    Firebase.auth.uid!!
                )
                Firebase.storage.reference.child(Firebase.auth.uid!!).child(photoID).putFile(image!!).await()
                Firebase.firestore.collection("posts").document(post.id).set(post).await()
                withContext(Dispatchers.Main){
                    finish()
                }
            }

        }
    }

    fun onResult(result:ActivityResult){
        if(result.resultCode == RESULT_OK) {
            image = result.data?.data
            Glide
                .with(this)
                .load(image)
                .into(binding.postComposerImage)
        }
    }
}