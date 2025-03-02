package com.example.project1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project1.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {
    //Binding
    lateinit var mainbinding: ActivityMainBinding
    //Firebase
    lateinit var database: FirebaseDatabase
    lateinit var ref: DatabaseReference
    //ArrayList
    val arraylist = ArrayList<Users>()
    //Adapter
    lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainbinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainbinding.root
        setContentView(view)

        // Firebase initialization
        database = FirebaseDatabase.getInstance()
        ref = database.reference.child("Users")


        //Move to DataEnter
        mainbinding.addbutton.setOnClickListener {
            val intent = Intent(this, DataEnter::class.java)
            startActivity(intent)
        }
        //Get Data
        userdatagate()

    }

    //Get Data from Firebase
    fun userdatagate(){
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                arraylist.clear()
                for(userdata in snapshot.children){
                    val user = userdata.getValue(Users::class.java)
                    if (user != null) {
                        println("User Id: ${user.id}")
                        arraylist.add(user)
                    }
                    //Add to arraylist
                    adapter = UserAdapter(this@MainActivity,arraylist)
                    mainbinding.Recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
                    mainbinding.Recyclerview.adapter = adapter
                }

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}