package com.example.farketmez.viewmodel

import android.view.View
import com.bumptech.glide.Glide
import com.example.farketmez.BaseViewModel
import com.example.farketmez.databinding.FragmentChosenBookBinding

class ChosenBookViewModel : BaseViewModel() {

    private var favoritesList = ArrayList<Any>()
    private val favoritesMap = HashMap<String, Any>()
    private var dontShowAgainList = ArrayList<Any>()
    private val dontShowAgainMap = HashMap<String, Any>()
    private lateinit var  bookID : String


    fun getRandomBook(binding: FragmentChosenBookBinding, view: View){
        firestore.collection("Books").addSnapshotListener { value, error ->
            value?.let {
                dontShowAgainCheck()
                val books = value.documents
                var book = books.random()
                bookID = book.id
                println(dontShowAgainList.size)
                while (dontShowAgainList.contains(book.id)){
                    books.remove(book)
                    book = books.random()
                    bookID = book.id
                }
                if (books.isNotEmpty()){
                    binding.bookNameTxt.text = book.get("bookName") as String
                    Glide.with(view).load(book.get("imgLink").toString()).into(binding.bookIV)
                    binding.writerText.text = "Yazar: " + book.get("writer").toString()
                    binding.bookCategoryText.text = "Kategori: " + book.get("category").toString()
                    binding.subjectText.text = "Konu: " + book.get("subject").toString()
                }
            }
        }
    }

    fun favoriteControl(binding: FragmentChosenBookBinding){
        firestore.collection("Users").document(auth.currentUser!!.email.toString()).addSnapshotListener { value, error ->
            value?.let {
                if (it.data?.get("favorites") != null){
                    favoritesList = it.get("favorites") as ArrayList<Any>
                    if (favoritesList.contains(bookID)){
                        binding.favoriteButton.visibility = View.GONE
                        binding.notFavoriteButton.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    fun addFavorite(binding: FragmentChosenBookBinding){
        favoritesList.add(bookID)
        favoritesMap.put("favorites", favoritesList)
        firestore.collection("Users").document(auth.currentUser!!.email.toString()).update(favoritesMap)
        binding.favoriteButton.visibility = View.GONE
        binding.notFavoriteButton.visibility = View.VISIBLE
    }

    fun deleteFavorite(binding: FragmentChosenBookBinding){
        favoritesList.remove(bookID)
        favoritesMap.put("favorites", favoritesList)
        firestore.collection("Users").document(auth.currentUser!!.email.toString()).update(favoritesMap)
        binding.notFavoriteButton.visibility = View.GONE
        binding.favoriteButton.visibility = View.VISIBLE
    }

    fun dontShowAgainAdd(){
        dontShowAgainList.add(bookID)
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