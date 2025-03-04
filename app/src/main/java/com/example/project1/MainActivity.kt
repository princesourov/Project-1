package com.example.project1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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
    // OnBackPressedCallback
    var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainbinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainbinding.root
        setContentView(view)

        // Firebase initialization
        database = FirebaseDatabase.getInstance()
        ref = database.reference.child("Users")

        // OnBackPressedCallback
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backPressedTime + 3000 > System.currentTimeMillis()) {
                    finish()
                } else {
                    Toast.makeText(this@MainActivity, "Press back again to leave the app.", Toast.LENGTH_LONG).show()
                }
                backPressedTime = System.currentTimeMillis()
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)

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