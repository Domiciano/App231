package com.example.icesiapp231.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.icesiapp231.MainActivity
import com.example.icesiapp231.databinding.FragmentProfileBinding
import com.example.icesiapp231.databinding.FragmentWorldBinding
import com.example.icesiapp231.viewmodels.ProfileViewModel

class WorldFragment:Fragment() {

    val profileViewModel:ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = FragmentWorldBinding.inflate(inflater, container, false)

        return view.root
    }

    companion object{
        fun newInstance() = WorldFragment()
    }

}