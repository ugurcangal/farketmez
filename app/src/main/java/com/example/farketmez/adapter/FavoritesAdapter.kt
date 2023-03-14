package com.example.farketmez.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.farketmez.databinding.FavoritesRecyclerRowBinding
import com.example.farketmez.model.Random
import com.example.farketmez.viewmodel.FavoritesViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.coroutineContext

class FavoritesAdapter(var favoritesList: ArrayList<Random>):
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    class FavoritesViewHolder(val binding : FavoritesRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding = FavoritesRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return favoritesList.size
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val favoriteItem = favoritesList[position]
        val item = holder.binding
        item.favoritesTV.text = favoriteItem.name
        Glide.with(holder.itemView.context).load(favoriteItem.imgLink).into(item.favoritesIV)

        var favorites = ArrayList<Any>()
        val favoritesMap = HashMap<String,Any>()
        val firestore = Firebase.firestore
        firestore.collection("Users").document(Firebase.auth.currentUser!!.email.toString()).addSnapshotListener { value, error ->
            value?.let {
                if (it.data?.get("favorites") != null) {
                    favorites = it.get("favorites") as ArrayList<Any>
                }
            }
        }

        

        item.deleteFavoriteButton.setOnClickListener {
            if (favoritesList.size == 1){
                favoritesList.clear()
            }
            if (favorites.contains(favoriteItem.id)){
                favorites.remove(favoriteItem.id)
                favoritesMap.put("favorites",favorites)
                firestore.collection("Users").document(Firebase.auth.currentUser!!.email.toString()).update(favoritesMap).addOnSuccessListener {
                    item.deleteFavoriteButton.visibility = View.GONE
                    item.favoriteButton.visibility = View.VISIBLE
                }
            }

        }

        item.favoriteButton.setOnClickListener {
            favorites.add(favoriteItem.id)
            favoritesMap.put("favorites",favorites)
            firestore.collection("Users").document(Firebase.auth.currentUser!!.email.toString()).update(favoritesMap).addOnSuccessListener {
                item.deleteFavoriteButton.visibility = View.VISIBLE
                item.favoriteButton.visibility = View.GONE
            }
        }


    }



}