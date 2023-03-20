package com.example.farketmez.viewmodel

import android.view.View
import com.bumptech.glide.Glide
import com.example.farketmez.BaseViewModel
import com.example.farketmez.databinding.FragmentChosenFilmBinding

class ChosenFilmViewModel : BaseViewModel() {

    private var favoritesList = ArrayList<Any>()
    private val favoritesMap = HashMap<String, Any>()
    private var dontShowAgainList = ArrayList<Any>()
    private val dontShowAgainMap = HashMap<String, Any>()
    private lateinit var filmID: String


    fun getRandomFilm(binding: FragmentChosenFilmBinding, view: View) {
        firestore.collection("Films").addSnapshotListener { value, error ->
            value?.let {
                dontShowAgainCheck()
                val films = value.documents
                var film = films.random()
                filmID = film.id
                println(dontShowAgainList.size)
                while (dontShowAgainList.contains(film.id)) {
                    films.remove(film)
                    film = films.random()
                    filmID = film.id
                }
                if (films.isNotEmpty()) {
                    binding.filmNameTxt.text = film.get("filmName") as String
                    Glide.with(view).load(film.get("imgLink").toString()).into(binding.filmIV)
                    binding.directorText.text = "Yönetmen: " + film.get("director") as String
                    binding.starsText.text = "Oyuncular: " + film.get("stars") as String
                    binding.filmCategoryText.text = "Kategori: " + film.get("category") as String
                    binding.subjectText.text = "Konu: " + film.get("subject") as String

                }
            }
        }
    }

    fun favoriteControl(binding: FragmentChosenFilmBinding) {
        firestore.collection("Users").document(auth.currentUser!!.email.toString())
            .addSnapshotListener { value, error ->
                value?.let {
                    if (it.data?.get("favorites") != null) {
                        favoritesList = it.get("favorites") as ArrayList<Any>
                        if (favoritesList.contains(filmID)) {
                            binding.favoriteButton.visibility = View.GONE
                            binding.notFavoriteButton.visibility = View.VISIBLE
                        }
                    }
                }
            }
    }

    fun addFavorite(binding: FragmentChosenFilmBinding) {
        favoritesList.add(filmID)
        favoritesMap.put("favorites", favoritesList)
        firestore.collection("Users").document(auth.currentUser!!.email.toString())
            .update(favoritesMap)
        binding.favoriteButton.visibility = View.GONE
        binding.notFavoriteButton.visibility = View.VISIBLE
    }

    fun deleteFavorite(binding: FragmentChosenFilmBinding) {
        favoritesList.remove(filmID)
        favoritesMap.put("favorites", favoritesList)
        firestore.collection("Users").document(auth.currentUser!!.email.toString())
            .update(favoritesMap)
        binding.notFavoriteButton.visibility = View.GONE
        binding.favoriteButton.visibility = View.VISIBLE
    }

    fun dontShowAgainAdd() {
        dontShowAgainList.add(filmID)
        dontShowAgainMap.put("dontShowAgain", dontShowAgainList)
        firestore.collection("Users").document(auth.currentUser!!.email.toString())
            .update(dontShowAgainMap)

    }

    fun dontShowAgainCheck() {
        firestore.collection("Users").document(auth.currentUser!!.email.toString())
            .addSnapshotListener { value, error ->
                value?.let {
                    if (it.data?.get("dontShowAgain") != null) {
                        dontShowAgainList = it.get("dontShowAgain") as ArrayList<Any>
                    }
                }
            }
    }

}