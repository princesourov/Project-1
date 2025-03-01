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

        // Firebase initialization
        database = FirebaseDatabase.getInstance()
        ref = database.reference.child("Users")


        //Add User Data
        viewbinding.button.setOnClickListener {
            addUserData()
        }
    }

    private fun addUserData() {
        val name = viewbinding.Etname.text.toString().trim()
        val email = viewbinding.Etemail.text.toString().trim()
        val phone = viewbinding.Etphone.text.toString().trim()
        val id = ref.push().key ?: return

        //Firebase
        var user = Users(id,name,email,phone)
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill all details", Toast.LENGTH_SHORT).show()
        }
        else {
            ref.child(id).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show()
                //Clear
                viewbinding.Etname.text.clear()
                viewbinding.Etemail.text.clear()
                viewbinding.Etphone.text.clear()
                viewbinding.Etname.requestFocus()
            }

                .addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}