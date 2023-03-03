package com.example.farketmez.viewmodel

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.farketmez.MainActivity
import com.example.farketmez.view.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    private val auth = Firebase.auth


    fun signInWithFirebase(email : String, password: String, activity: LoginActivity){
        if (email.isNotEmpty() && password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                val intent = Intent(activity, MainActivity::class.java)
                activity.startActivity(intent)
                activity.finish()
            }.addOnFailureListener {
                Toast.makeText(activity, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(activity, "Lütfen önce hesap bilgilerinizi girin!", Toast.LENGTH_SHORT).show()
        }
    }



}