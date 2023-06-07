package com.example.farketmez.viewmodel

import android.view.View
import com.bumptech.glide.Glide
import com.example.farketmez.BaseViewModel
import com.example.farketmez.R
import com.example.farketmez.databinding.FragmentChosenFoodBinding

class ChosenFoodViewModel : BaseViewModel() {

    private var favoritesList = ArrayList<Any>()
    private val favoritesMap = HashMap<String, Any>()
    private var dontShowAgainList = ArrayList<Any>()
    private val dontShowAgainMap = HashMap<String, Any>()
    private lateinit var  foodID : String

    fun getRandomFood(binding: FragmentChosenFoodBinding, view: View){
        firestore.collection("Foods").addSnapshotListener { value, error ->
            value?.let {
                dontShowAgainCheck()
                val foods = value.documents
                var food = foods.random()
                foodID = food.id
                println(dontShowAgainList.size)
                while (dontShowAgainList.contains(food.id)){
                    foods.remove(food)
                    food = foods.random()
                    foodID = food.id
                }
                if (foods.isNotEmpty()){
                    binding.foodNameTxt.text = food.get("foodName") as String
                    Glide.with(view).asBitmap().load(food.get("imgLink").toString()).into(binding.foodIV)
                }
            }
        }
    }

    fun favoriteControl(binding: FragmentChosenFoodBinding){
        firestore.collection("Users").document(auth.currentUser!!.email.toString()).addSnapshotListener { value, error ->
            value?.let {
                if (it.data?.get("favorites") != null){
                    favoritesList = it.get("favorites") as ArrayList<Any>
                    if (favoritesList.contains(foodID)){
                        binding.favoriteButton.visibility = View.GONE
                        binding.notFavoriteButton.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
    fun addFavorite(binding: FragmentChosenFoodBinding){
        favoritesList.add(foodID)
        favoritesMap.put("favorites", favoritesList)
        firestore.collection("Users").document(auth.currentUser!!.email.toString()).update(favoritesMap)
        binding.favoriteButton.visibility = View.GONE
        binding.notFavoriteButton.visibility = View.VISIBLE
    }

    fun deleteFavorite(binding: FragmentChosenFoodBinding){
        favoritesList.remove(foodID)
        favoritesMap.put("favorites", favoritesList)
        firestore.collection("Users").document(auth.currentUser!!.email.toString()).update(favoritesMap)
        binding.notFavoriteButton.visibility = View.GONE
        binding.favoriteButton.visibility = View.VISIBLE
    }

    fun dontShowAgainAdd(){
        dontShowAgainList.add(foodID)
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