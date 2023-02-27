package com.example.farketmez

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

abstract class BaseViewModel : ViewModel(){

    val firestore = Firebase.firestore
    val auth = Firebase.auth
    val storage = Firebase.storage


}