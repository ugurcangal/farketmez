package com.example.farketmez.viewmodel

import android.view.View
import com.bumptech.glide.Glide
import com.example.farketmez.BaseViewModel
import com.example.farketmez.databinding.FragmentChosenDessertBinding

class ChosenDessertViewModel : BaseViewModel() {

    private var favoritesList = ArrayList<Any>()
    private val favoritesMap = HashMap<String, Any>()
    private var dontShowAgainList = ArrayList<Any>()
    private val dontShowAgainMap = HashMap<String, Any>()
    private lateinit var  dessertID : String

    fun getRandomDessert(binding: FragmentChosenDessertBinding, view: View){
        firestore.collection("Desserts").addSnapshotListener { value, error ->
            value?.let {
                dontShowAgainCheck()
                val desserts = value.documents
                var dessert = desserts.random()
                dessertID = dessert.id
                println(dontShowAgainList.size)
                while (dontShowAgainList.contains(dessert.id)){
                    desserts.remove(dessert)
                    dessert = desserts.random()
                    dessertID = dessert.id
                }
                if (desserts.isNotEmpty()){
                    binding.dessertNameTxt.text = dessert.get("dessertName") as String
                    Glide.with(view).asBitmap().load(dessert.get("imgLink").toString()).into(binding.dessertIV)
                }
            }
        }
    }



    fun favoriteControl(binding: FragmentChosenDessertBinding){
        firestore.collection("Users").document(auth.currentUser!!.email.toString()).addSnapshotListener { value, error ->
            value?.let {
                if (it.data?.get("favorites") != null){
                    favoritesList = it.get("favorites") as ArrayList<Any>
                    if (favoritesList.contains(dessertID)){
                        binding.favoriteButton.visibility = View.GONE
                        binding.notFavoriteButton.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    fun addFavorite(binding: FragmentChosenDessertBinding){
        favoritesList.add(dessertID)
        favoritesMap.put("favorites", favoritesList)
        firestore.collection("Users").document(auth.currentUser!!.email.toString()).update(favoritesMap)
        binding.favoriteButton.visibility = View.GONE
        binding.notFavoriteButton.visibility = View.VISIBLE
    }

    fun deleteFavorite(binding: FragmentChosenDessertBinding){
        favoritesList.remove(dessertID)
        favoritesMap.put("favorites", favoritesList)
        firestore.collection("Users").document(auth.currentUser!!.email.toString()).update(favoritesMap)
        binding.notFavoriteButton.visibility = View.GONE
        binding.favoriteButton.visibility = View.VISIBLE
    }

    fun dontShowAgainAdd(){
        dontShowAgainList.add(dessertID)
        dontShowAgainMap.put("dontShowAgain", dontShowAgainList)
        firestore.collection("Users").document(auth.currentUser!!.email.toString()).update(dontShowAgainMap)

    }

    fun dontShowAgainCheck(){
        firestore.collection("Users").document(auth.currentUser!!.email.toString()).addSnapshotListener { value, error ->
            value?.let {
                if (it.data?.get("dontShowAgain") != null){
                    dontShowAgainList = it.get("dontShowAgain") as ArrayList<Any>
                }
            }
        }
    }
}