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
            "Yemek" -> binding.choosenBG.setImageResource(R.drawable.yemek)
            "İçecek" -> binding.choosenBG.setImageResource(R.drawable.icecek)
            "Film" -> binding.choosenBG.setImageResource(R.drawable.film)
            "Oyun" -> binding.choosenBG.setImageResource(R.drawable.oyun)
            "Kitap" -> binding.choosenBG.setImageResource(R.drawable.kitap)
        }

        //Background
        when(name){
            "Yemek" -> binding.constraintLayout.setBackgroundResource(R.drawable.chosen_food_bg)
            "İçecek" -> binding.constraintLayout.setBackgroundResource(R.drawable.chosen_drink_bg)
            "Film" -> binding.constraintLayout.setBackgroundResource(R.drawable.chosen_film_bg)
            "Oyun" -> binding.constraintLayout.setBackgroundResource(R.drawable.chosen_game_bg)
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
            "Oyun" -> dialogLottie.setAnimation(R.raw.game_animation)
            "Film" -> dialogLottie.setAnimation(R.raw.film_animation)
            "İçecek" -> dialogLottie.setAnimation(R.raw.drink_animation)
            "Kitap" -> dialogLottie.setAnimation(R.raw.book_animation)
        }

        //Dialog Text
        when(name){
            "Yemek" -> dialogText.text = "Rastgele Yemek Seçiliyor!"
            "İçecek" -> dialogText.text = "Rastgele İçecek Seçiliyor!"
            "Film" -> dialogText.text = "Rastgele Film Seçiliyor!"
            "Oyun" -> dialogText.text = "Rastgele Oyun Seçiliyor!"
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
            findNavController().navigate(R.id.action_chosenCategoryFragment_to_chosenFoodFragment)
        }




        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }


    }

}