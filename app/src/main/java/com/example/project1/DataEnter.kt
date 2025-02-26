package com.example.project1

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project1.databinding.ActivityDataEnterBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DataEnter : AppCompatActivity() {
    //Binding
    lateinit var viewbinding: ActivityDataEnterBinding
    //Firebase
    lateinit var database: FirebaseDatabase
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = ActivityDataEnterBinding.inflate(layoutInflater)
        val view = viewbinding.root
        setContentView(view)

        //Firebase
        database = FirebaseDatabase.getInstance()
        ref = database.reference.child("Users")


        //Add User Data
        viewbinding.button.setOnClickListener {
            addUserData()
        }
    }

    private fun addUserData() {
        var name = viewbinding.Etname.text.toString()
        var email = viewbinding.Etemail.text.toString()
        var phone = viewbinding.Etphone.text.toString()
        var id =ref.push().key.toString()

        //Firebase
        var user = Users(id,name,email,phone)
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill all details", Toast.LENGTH_SHORT).show()
            }
            else {
                ref.child(id).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show()
            viewbinding.Etname.text.clear()
            viewbinding.Etemail.text.clear()
            viewbinding.Etphone.text.clear()
                }
            .addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
            }
    }
}