package com.example.farketmez.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.farketmez.BaseFragment
import com.example.farketmez.R
import com.example.farketmez.databinding.FragmentProfileBinding
import com.example.farketmez.viewmodel.ProfileViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ProfileFragment : BaseFragment<FragmentProfileBinding,ProfileViewModel>(FragmentProfileBinding::inflate){


    override fun getViewModel(): Class<ProfileViewModel> = ProfileViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserNameInfo(binding)

        binding.signoutBtn.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.favoriteBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_favoritesFragment)
        }

    }


}