package com.example.farketmez.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.navigation.fragment.findNavController
import com.example.farketmez.BaseFragment
import com.example.farketmez.R
import com.example.farketmez.databinding.FragmentChosenFoodBinding
import com.example.farketmez.viewmodel.ChosenFoodViewModel


class ChosenFoodFragment : BaseFragment<FragmentChosenFoodBinding, ChosenFoodViewModel>(FragmentChosenFoodBinding::inflate) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dontShowAgainCheck()
        viewModel.getRandomFood(binding, view)
        viewModel.favoriteControl(binding)


        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.favoriteButton.setOnClickListener {
            viewModel.addFavorite(binding)
        }
        binding.notFavoriteButton.setOnClickListener {
            viewModel.deleteFavorite(binding)
        }
        binding.dontShowThisAgainTxt.setOnClickListener {
            viewModel.dontShowAgainAdd()
        }

    }



    override fun getViewModel(): Class<ChosenFoodViewModel> = ChosenFoodViewModel::class.java

}