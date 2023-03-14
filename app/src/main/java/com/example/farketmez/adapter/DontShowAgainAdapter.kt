package com.example.farketmez.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.farketmez.databinding.DontShowAgainRecyclerRowBinding
import com.example.farketmez.model.Random
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DontShowAgainAdapter (var dontShowAgainList: ArrayList<Random>):
    RecyclerView.Adapter<DontShowAgainAdapter.DontShowAgainViewHolder>() {

    class DontShowAgainViewHolder(val binding : DontShowAgainRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DontShowAgainViewHolder {
        val binding = DontShowAgainRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DontShowAgainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dontShowAgainList.size
    }

    override fun onBindViewHolder(holder: DontShowAgainViewHolder, position: Int) {
        val favoriteItem = dontShowAgainList[position]
        val item = holder.binding
        item.dontShowAgainTV.text = favoriteItem.name
        Glide.with(holder.itemView.context).load(favoriteItem.imgLink).into(item.dontShowAgainIV)

        var dontShowAgains = ArrayList<Any>()
        val dontShowAgainsMap = HashMap<String,Any>()
        val firestore = Firebase.firestore
        firestore.collection("Users").document(Firebase.auth.currentUser!!.email.toString()).addSnapshotListener { value, error ->
            value?.let {
                if (it.data?.get("dontShowAgain") != null) {
                    dontShowAgains = it.get("dontShowAgain") as ArrayList<Any>
                }
            }
        }



        item.deleteItemButton.setOnClickListener {
            if (dontShowAgainList.size == 1){
                dontShowAgainList.clear()
            }
            if (dontShowAgains.contains(favoriteItem.id)){
                dontShowAgains.remove(favoriteItem.id)
                dontShowAgainsMap.put("dontShowAgain",dontShowAgains)
                firestore.collection("Users").document(Firebase.auth.currentUser!!.email.toString()).update(dontShowAgainsMap)
            }

        }



    }



}