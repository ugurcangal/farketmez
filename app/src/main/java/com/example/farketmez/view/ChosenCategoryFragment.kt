package com.example.farketmez.view

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.farketmez.BaseFragment
import com.example.farketmez.R
import com.example.farketmez.databinding.FragmentChosenCategoryBinding
import com.example.farketmez.viewmodel.ChosenCategoryViewModel


class ChosenCategoryFragment : BaseFragment<FragmentChosenCategoryBinding,ChosenCategoryViewModel>(FragmentChosenCategoryBinding::inflate) {

    private lateinit var name: String

    override fun getViewModel(): Class<ChosenCategoryViewModel> = ChosenCategoryViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            name = ChosenCategoryFragmentArgs.fromBundle(it).chosenCategoryName
            binding.choosenText.text = name
        }

        when(name){
            "Yemek" -> binding.choosenBG.setImageResource(R.drawable.yemek)
            "İçecek" -> binding.choosenBG.setImageResource(R.drawable.icecek)
            "Film" -> binding.choosenBG.setImageResource(R.drawable.film)
            "Oyun" -> binding.choosenBG.setImageResource(R.drawable.oyun)
            "Kitap" -> binding.choosenBG.setImageResource(R.drawable.kitap)
        }

        when(name){
            "Yemek" -> binding.constraintLayout.setBackgroundResource(R.drawable.chosen_food_bg)
            "İçecek" -> binding.constraintLayout.setBackgroundResource(R.drawable.chosen_drink_bg)
            "Film" -> binding.constraintLayout.setBackgroundResource(R.drawable.chosen_film_bg)
            "Oyun" -> binding.constraintLayout.setBackgroundResource(R.drawable.chosen_game_bg)
            "Kitap" -> binding.constraintLayout.setBackgroundResource(R.drawable.chosen_book_bg)
        }



        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }


    }

}