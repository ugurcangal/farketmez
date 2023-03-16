package com.example.farketmez.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.farketmez.BaseFragment
import com.example.farketmez.R
import com.example.farketmez.adapter.FavoritesAdapter
import com.example.farketmez.databinding.FragmentFavoritesBinding
import com.example.farketmez.viewmodel.FavoritesViewModel


class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding, FavoritesViewModel>(FragmentFavoritesBinding::inflate) {

    private lateinit var favoritesAdapter: FavoritesAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllFavorites(binding)
        observeFavoriteList()

        binding.closeBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun observeFavoriteList() {
        viewModel.observeFavoritesList().observe(viewLifecycleOwner) {
            favoritesAdapter = FavoritesAdapter(it)
            binding.favoritesRV.layoutManager = LinearLayoutManager(context)
            binding.favoritesRV.adapter = favoritesAdapter
            favoritesAdapter.notifyDataSetChanged()
        }

    }


    override fun getViewModel(): Class<FavoritesViewModel> = FavoritesViewModel::class.java

}