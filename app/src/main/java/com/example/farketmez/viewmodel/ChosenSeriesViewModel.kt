package com.example.farketmez.viewmodel

import android.view.View
import com.bumptech.glide.Glide
import com.example.farketmez.BaseViewModel
import com.example.farketmez.databinding.FragmentChosenSeriesBinding

class ChosenSeriesViewModel : BaseViewModel() {

    private var favoritesList = ArrayList<Any>()
    private val favoritesMap = HashMap<String, Any>()
    private var dontShowAgainList = ArrayList<Any>()
    private val dontShowAgainMap = HashMap<String, Any>()
    private lateinit var  seriesID : String


    fun getRandomSeries(binding: FragmentChosenSeriesBinding, view: View){
        firestore.collection("Series").addSnapshotListener { value, error ->
            value?.let {
                dontShowAgainCheck()
                val series = value.documents
                var oneSerie = series.random()
                seriesID = oneSerie.id
                println(dontShowAgainList.size)
                while (dontShowAgainList.contains(oneSerie.id)){
                    series.remove(oneSerie)
                    oneSerie = series.random()
                    seriesID = oneSerie.id
                }
                if (series.isNotEmpty()){
                    binding.seriesNameTxt.text = oneSerie.get("seriesName") as String
                    Glide.with(view).load(oneSerie.get("imgLink").toString()).into(binding.seriesIV)
                    binding.starsText.text = "Oyuncular: " + oneSerie.get("stars").toString()
                    binding.seriesCategoryText.text = "Kategori: " + oneSerie.get("category").toString()
                    binding.subjectText.text = "Konu: " + oneSerie.get("subject").toString()

                }
            }
        }
    }

    fun favoriteControl(binding: FragmentChosenSeriesBinding){
        firestore.collection("Users").document(auth.currentUser!!.email.toString()).addSnapshotListener { value, error ->
            value?.let {
                if (it.data?.get("favorites") != null){
                    favoritesList = it.get("favorites") as ArrayList<Any>
                    if (favoritesList.contains(seriesID)){
                        binding.favoriteButton.visibility = View.GONE
                        binding.notFavoriteButton.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    fun addFavorite(binding: FragmentChosenSeriesBinding){
        favoritesList.add(seriesID)
        favoritesMap.put("favorites", favoritesList)
        firestore.collection("Users").document(auth.currentUser!!.email.toString()).update(favoritesMap)
        binding.favoriteButton.visibility = View.GONE
        binding.notFavoriteButton.visibility = View.VISIBLE
    }

    fun deleteFavorite(binding: FragmentChosenSeriesBinding){
        favoritesList.remove(seriesID)
        favoritesMap.put("favorites", favoritesList)
        firestore.collection("Users").document(auth.currentUser!!.email.toString()).update(favoritesMap)
        binding.notFavoriteButton.visibility = View.GONE
        binding.favoriteButton.visibility = View.VISIBLE
    }

    fun dontShowAgainAdd(){
        dontShowAgainList.add(seriesID)
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