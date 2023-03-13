package com.example.farketmez.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.farketmez.BaseFragment
import com.example.farketmez.R
import com.example.farketmez.databinding.FragmentChosenCategoryBinding
import com.example.farketmez.viewmodel.ChosenCategoryViewModel
import java.util.Timer
import java.util.TimerTask


class ChosenCategoryFragment : BaseFragment<FragmentChosenCategoryBinding,ChosenCategoryViewModel>(FragmentChosenCategoryBinding::inflate) {

    private lateinit var name: String

    override fun getViewModel(): Class<ChosenCategoryViewModel> = ChosenCategoryViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            name = ChosenCategoryFragmentArgs.fromBundle(it).chosenCategoryName
            binding.choosenText.text = name
        }

        //Main Image
        when(name){
            "Yemek" -> binding.choosenBG.setImageResource(R.drawable.food2)
            "Tatlı" -> binding.choosenBG.setImageResource(R.drawable.tatli)
            "Film" -> binding.choosenBG.setImageResource(R.drawable.film2)
            "Dizi" -> binding.choosenBG.setImageResource(R.drawable.series2)
            "Kitap" -> binding.choosenBG.setImageResource(R.drawable.books2)
        }

        //Background
        when(name){
            "Yemek" -> binding.constraintLayout.setBackgroundResource(R.drawable.chosen_food_bg)
            "Tatlı" -> binding.constraintLayout.setBackgroundResource(R.drawable.chosen_dessert_bg)
            "Film" -> binding.constraintLayout.setBackgroundResource(R.drawable.chosen_film_bg)
            "Dizi" -> binding.constraintLayout.setBackgroundResource(R.drawable.chosen_series_bg)
            "Kitap" -> binding.constraintLayout.setBackgroundResource(R.drawable.chosen_book_bg)
        }


        //Dialog
        val dialogBinding = layoutInflater.inflate(R.layout.custom_chosen_dialog, null)
        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogBinding)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogLottie = dialogBinding.findViewById<LottieAnimationView>(R.id.lottie)
        val dialogText = dialogBinding.findViewById<TextView>(R.id.chosenNameTxt)

        //Dialog Animation
        when(name){
            "Yemek" -> dialogLottie.setAnimation(R.raw.food_animation)
            "Dizi" -> dialogLottie.setAnimation(R.raw.series_animation)
            "Film" -> dialogLottie.setAnimation(R.raw.film_animation)
            "Tatlı" -> dialogLottie.setAnimation(R.raw.tatli_animation)
            "Kitap" -> dialogLottie.setAnimation(R.raw.book_animation)
        }

        //Dialog Text
        when(name){
            "Yemek" -> dialogText.text = "Rastgele Yemek Seçiliyor!"
            "Tatlı" -> dialogText.text = "Rastgele Tatlı Seçiliyor!"
            "Film" -> dialogText.text = "Rastgele Film Seçiliyor!"
            "Dizi" -> dialogText.text = "Rastgele Dizi Seçiliyor!"
            "Kitap" -> dialogText.text = "Rastgele Kitap Seçiliyor!"
        }



        binding.farketmezBtn.setOnClickListener {
            dialog.show()
            val timer = Timer()
            timer.schedule(object : TimerTask(){
                override fun run() {
                    dialog.dismiss()
                }

            }, 3000)
        }
        dialog.setOnDismissListener {
            when(name){
                "Yemek" -> findNavController().navigate(R.id.action_chosenCategoryFragment_to_chosenFoodFragment)
                "Tatlı" -> findNavController().navigate(R.id.action_chosenCategoryFragment_to_chosenDessertFragment)
                "Film" -> findNavController().navigate(R.id.action_chosenCategoryFragment_to_chosenFilmFragment)
                "Kitap" -> findNavController().navigate(R.id.action_chosenCategoryFragment_to_chosenBookFragment)
                "Dizi" -> findNavController().navigate(R.id.action_chosenCategoryFragment_to_chosenSeriesFragment)

            }

        }




        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }


    }

}