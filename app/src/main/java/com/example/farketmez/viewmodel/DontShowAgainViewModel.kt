package com.example.farketmez.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.farketmez.BaseViewModel
import com.example.farketmez.databinding.FragmentDontShowAgainBinding
import com.example.farketmez.model.Random

class DontShowAgainViewModel : BaseViewModel() {

    var dontShowAgainList = MutableLiveData<ArrayList<Random>>()
    var dontShowAgainListString = ArrayList<String>()
    var randomList = ArrayList<Random>()


    fun getAllDontShowAgain(binding: FragmentDontShowAgainBinding){

        firestore.collection("Users").document(auth.currentUser!!.email.toString()).addSnapshotListener { value, error ->
            value?.let {
                if (it.data?.get("dontShowAgain") != null){
                    dontShowAgainListString = it.get("dontShowAgain") as ArrayList<String>
                }
                if (dontShowAgainListString.size == 0){
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
                    if (dontShowAgainListString.contains(document.id)){
                        val id = document.id
                        val name = document.get("foodName") as String
                        val imgLink = document.get("imgLink") as String
                        val random = Random(id,name, imgLink)
                        randomList.add(random)
                    }
                }

            }
        }

        firestore.collection("Books").addSnapshotListener { value, error ->
            value?.let {
                val documents = value.documents
                for (document in documents){
                    if (dontShowAgainListString.contains(document.id)){
                        val id = document.id
                        val name = document.get("bookName") as String
                        val imgLink = document.get("imgLink") as String

                        val random = Random(id,name, imgLink)
                        randomList.add(random)
                    }
                }

            }
        }

        firestore.collection("Desserts").addSnapshotListener { value, error ->
            value?.let {
                val documents = value.documents
                for (document in documents){
                    if (dontShowAgainListString.contains(document.id)){
                        val id = document.id
                        val name = document.get("dessertName") as String
                        val imgLink = document.get("imgLink") as String

                        val random = Random(id,name, imgLink)
                        randomList.add(random)
                    }
                }

            }
        }

        firestore.collection("Films").addSnapshotListener { value, error ->
            value?.let {
                val documents = value.documents
                for (document in documents){
                    if (dontShowAgainListString.contains(document.id)){
                        val id = document.id
                        val name = document.get("filmName") as String
                        val imgLink = document.get("imgLink") as String

                        val random = Random(id,name, imgLink)
                        randomList.add(random)
                    }
                }

            }
        }

        firestore.collection("Series").addSnapshotListener { value, error ->
            value?.let {
                val documents = value.documents
                for (document in documents){
                    if (dontShowAgainListString.contains(document.id)){
                        val id = document.id
                        val name = document.get("seriesName") as String
                        val imgLink = document.get("imgLink") as String

                        val random = Random(id,name, imgLink)
                        randomList.add(random)

                    }
                }

            }
        }
        dontShowAgainList.postValue(randomList)


    }

    fun observeDontShowAgainList() : LiveData<ArrayList<Random>> {
        return dontShowAgainList
    }
}