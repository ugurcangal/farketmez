package com.example.farketmez.viewmodel

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.farketmez.MainActivity
import com.example.farketmez.view.RegisterActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    fun signUpFirebase(
        name: String,
        surname: String,
        email: String,
        password: String,
        passwordConfirm: String,
        activity: RegisterActivity
    ) {

        if (name.isNotEmpty() && surname.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && passwordConfirm.isNotEmpty()){
            if (password == passwordConfirm){
                auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                    Toast.makeText(activity, "Başarıyla Kayıt Olundu!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(activity, MainActivity::class.java)
                    activity.startActivity(intent)
                    activity.finishAffinity()
                    activity.finish()
                    addUser(name, surname, email)
                }.addOnFailureListener {
                    Toast.makeText(activity, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(activity,"Şifreler birbiri ile uyuşmuyor!" ,Toast.LENGTH_SHORT).show()

            }

        }

    }

    private fun addUser(name: String, surname: String, email: String) {
        val userMap = hashMapOf<String, Any>()
        userMap.put("name", name)
        userMap.put("surname", surname)
        userMap.put("email", email)
        firestore.collection("Users").document(email).set(userMap)
    }

}