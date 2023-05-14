package com.example.icesiapp231.fragments

import android.app.Activity.RESULT_OK
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
import com.bumptech.glide.Glide
import com.example.icesiapp231.MainActivity
import com.example.icesiapp231.databinding.FragmentProfileBinding
import com.example.icesiapp231.viewmodels.ProfileViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

import java.util.UUID

class ProfileFragment:Fragment() {

    val profileViewModel:ProfileViewModel by activityViewModels()
    val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), ::onResult)

    private lateinit var view:FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = FragmentProfileBinding.inflate(inflater, container, false)
        profileViewModel.user.observe(viewLifecycleOwner){user ->
            view.nameTV.text = user.name
            user.photoURL?.let { url->
                Glide.with(requireContext()).load(url).into(view.profileImage)
            }
        }
        profileViewModel.getUser()


        view.profileImage.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/*"
            launcher.launch(i)
        }

        return view.root
    }

    fun onResult(result:ActivityResult){
        if(result.resultCode == RESULT_OK) {
            val image = result.data?.data!!
            profileViewModel.updateProfile(image)
        }
    }

    companion object{
        fun newInstance() = ProfileFragment()
    }

}