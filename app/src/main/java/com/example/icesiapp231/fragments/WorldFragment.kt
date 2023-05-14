package com.example.icesiapp231.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.icesiapp231.MainActivity
import com.example.icesiapp231.PostComposerActivity
import com.example.icesiapp231.add.PostAdapter
import com.example.icesiapp231.databinding.FragmentProfileBinding
import com.example.icesiapp231.databinding.FragmentWorldBinding
import com.example.icesiapp231.model.Post
import com.example.icesiapp231.viewmodels.PostViewModel
import com.example.icesiapp231.viewmodels.ProfileViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class WorldFragment : Fragment() {

    val postViewModel: PostViewModel by activityViewModels()
    val adapter = PostAdapter()
    val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), ::onResult)

    private fun onResult(activityResult: ActivityResult) {
        postViewModel.getWorldPosts()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = FragmentWorldBinding.inflate(inflater, container, false)
        view.postBtn.setOnClickListener {
            launcher.launch(Intent(activity, PostComposerActivity::class.java))
        }
        view.worldPostRV.setHasFixedSize(true)
        view.worldPostRV.layoutManager = LinearLayoutManager(context)


        view.worldPostRV.adapter = adapter

        postViewModel.posts.observe(viewLifecycleOwner){
            if(it.size>0){

                adapter.addPost(it.last())
                adapter.notifyItemInserted(it.lastIndex)
            }
        }

        postViewModel.getWorldPosts()
        return view.root
    }


    companion object {
        fun newInstance() = WorldFragment()
    }

}