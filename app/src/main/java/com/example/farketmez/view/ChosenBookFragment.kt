package com.example.farketmez.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.farketmez.BaseFragment
import com.example.farketmez.R
import com.example.farketmez.databinding.FragmentChosenBookBinding
import com.example.farketmez.viewmodel.ChosenBookViewModel


class ChosenBookFragment : BaseFragment<FragmentChosenBookBinding,ChosenBookViewModel>(FragmentChosenBookBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dontShowAgainCheck()
        viewModel.getRandomFilm(binding, view)
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



        //DontShowAgain Dialog
        val dialogBinding = layoutInflater.inflate(R.layout.custom_dontshowagain_dialog, null)
        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogBinding)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogYesBtn = dialogBinding.findViewById<Button>(R.id.yesBTN)
        val dialogNoBtn = dialogBinding.findViewById<Button>(R.id.noBTN)

        binding.dontShowThisAgainTxt.setOnClickListener {
            dialog.show()
        }
        dialogYesBtn.setOnClickListener {
            viewModel.dontShowAgainAdd()
            dialog.dismiss()
            Toast.makeText(context, "Bu seçenek bir daha karşınıza gelmeyecek!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_chosenBookFragment_to_mainFragment)
        }
        dialogNoBtn.setOnClickListener {
            dialog.dismiss()
        }

    }



    override fun getViewModel(): Class<ChosenBookViewModel> = ChosenBookViewModel::class.java

}