package com.example.project1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    //Binding
    lateinit var mainbinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainbinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainbinding.root
        setContentView(view)


        //Move to DataEnter
        mainbinding.addbutton.setOnClickListener {
            val intent = Intent(this, DataEnter::class.java)
            startActivity(intent)
        }

    }
}