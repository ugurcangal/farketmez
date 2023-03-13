package com.example.farketmez.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.farketmez.R
import com.example.farketmez.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_profileFragment)
        }

        binding.yemekIMG.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToChosenCategoryFragment("Yemek")
            Navigation.findNavController(it).navigate(action)
        }

        binding.tatliIMG.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToChosenCategoryFragment("Tatlı")
            Navigation.findNavController(it).navigate(action)
        }

        binding.filmIMG.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToChosenCategoryFragment("Film")
            Navigation.findNavController(it).navigate(action)
        }

        binding.diziIMG.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToChosenCategoryFragment("Dizi")
            Navigation.findNavController(it).navigate(action)
        }

        binding.kitapIMG.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToChosenCategoryFragment("Kitap")
            Navigation.findNavController(it).navigate(action)
        }

    }

}