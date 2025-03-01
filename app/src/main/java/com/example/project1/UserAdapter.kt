package com.example.project1

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project1.databinding.UserItemBinding

class UserAdapter(
    //Context
    var  context : Context, var arraylist : ArrayList<Users>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

        //ViewHolder
    inner class UserViewHolder(val adapterBinding : UserItemBinding) : RecyclerView.ViewHolder(adapterBinding.root)

    //Adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arraylist.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.adapterBinding.Sname.text = arraylist[position].name
        holder.adapterBinding.Semail.text = arraylist[position].email
        holder.adapterBinding.Snumber.text = arraylist[position].phone
    }
}