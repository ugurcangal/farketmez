package com.example.farketmez.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.farketmez.BaseViewModel
import com.example.farketmez.databinding.FragmentFavoritesBinding
import com.example.farketmez.model.Random

class FavoritesViewModel : BaseViewModel() {

    var favoritesList = MutableLiveData<ArrayList<Random>>()
    var favoritesListString = ArrayList<String>()


    fun getAllFavorites(binding: FragmentFavoritesBinding){

        var randomList = ArrayList<Random>()

        firestore.collection("Users").document(auth.currentUser!!.email.toString()).addSnapshotListener { value, error ->
            value?.let {
                if (it.data?.get("favorites") != null){
                    favoritesListString = it.get("favorites") as ArrayList<String>
                }
                if (favoritesListString.size == 0){
                    binding.emptyTV.visibility = View.VISIBLE
                }else{
                    binding.emptyTV.visibility = View.GONE
                }
            }
        }

        firestore.collection("Foods").addSnapshotListener { value, error ->
            value?.let {
                val documents = value.documents
                for (document in documents){
                    if (favoritesListString.contains(document.id)){
                        val id = document.id
                        val name = document.get("foodName") as String
                        val imgLink = document.get("imgLink") as String
                        val random = Random(id,name, imgLink)
                        randomList.add(random)
                        favoritesList.value = randomList
                    }
                }

            }
        }

        firestore.collection("Books").addSnapshotListener { value, error ->
            value?.let {
                val documents = value.documents
                for (document in documents){
                    if (favoritesListString.contains(document.id)){
                        val id = document.id
                        val name = document.get("bookName") as String
                        val imgLink = document.get("imgLink") as String

                        val random = Random(id,name, imgLink)
                        randomList.add(random)
                        favoritesList.value = randomList
                    }
                }

            }
        }

        firestore.collection("Desserts").addSnapshotListener { value, error ->
            value?.let {
                val documents = value.documents
                for (document in documents){
                    if (favoritesListString.contains(document.id)){
                        val id = document.id
                        val name = document.get("dessertName") as String
                        val imgLink = document.get("imgLink") as String

                        val random = Random(id,name, imgLink)
                        randomList.add(random)
                        favoritesList.value = randomList

                    }
                }

            }
        }

        firestore.collection("Films").addSnapshotListener { value, error ->
            value?.let {
                val documents = value.documents
                for (document in documents){
                    if (favoritesListString.contains(document.id)){
                        val id = document.id
                        val name = document.get("filmName") as String
                        val imgLink = document.get("imgLink") as String

                        val random = Random(id,name, imgLink)
                        randomList.add(random)
                        favoritesList.value = randomList
                    }
                }

            }
        }

        firestore.collection("Series").addSnapshotListener { value, error ->
            value?.let {
                val documents = value.documents
                for (document in documents){
                    if (favoritesListString.contains(document.id)){
                        val id = document.id
                        val name = document.get("seriesName") as String
                        val imgLink = document.get("imgLink") as String

                        val random = Random(id,name, imgLink)
                        randomList.add(random)
                        favoritesList.value = randomList
                    }
                }

            }
        }


    }

    fun observeFavoritesList() : LiveData<ArrayList<Random>> {
        return favoritesList
    }
}