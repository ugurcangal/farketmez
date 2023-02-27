package com.example.farketmez.viewmodel


import android.view.View
import com.example.farketmez.BaseViewModel
import com.example.farketmez.databinding.FragmentProfileBinding

class ProfileViewModel : BaseViewModel() {



    fun getUserNameInfo(binding: FragmentProfileBinding){
        firestore.collection("Users").document(auth.currentUser!!.email.toString()).addSnapshotListener { value, error ->
            value?.let {
                binding.nameTxt.setText(it.get("name") as String)
                if (it.data?.get("surname") != null){
                    binding.surnameTxt.setText(it.get("surname") as String)
                }else{
                    binding.surnameTxt.visibility = View.GONE
                }
            }
        }
    }


}