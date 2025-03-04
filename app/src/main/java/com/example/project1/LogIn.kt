package com.example.project1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.project1.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {
    // Binding
    lateinit var LoginBinding: ActivityLogInBinding
    // OnBackPressedCallback
    var backPressedTime: Long = 0
    // Firebase
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginBinding = ActivityLogInBinding.inflate(layoutInflater)
        val view = LoginBinding.root
        setContentView(view)

        // Firebase Auth instance
        firebaseAuth = FirebaseAuth.getInstance()

        // OnBackPressedCallback
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backPressedTime + 3000 > System.currentTimeMillis()) {
                    finish()
                } else {
                    Toast.makeText(this@LogIn, "Press back again to leave the app.", Toast.LENGTH_LONG).show()
                }
                backPressedTime = System.currentTimeMillis()
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)

        // Login Button click listener
        LoginBinding.LogInBtn.setOnClickListener {
            val email = LoginBinding.LoginUserEmail.text.toString().trim()
            val password = LoginBinding.LoginPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                // Correcting Firebase login call syntax
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)  // Navigate to MainActivity
                            startActivity(intent)
                            finish()  // Close the login activity
                        } else {
                            Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        // Move to SignUp activity
        LoginBinding.NewUser.setOnClickListener {
            val intent = Intent(this, SingUp::class.java)
            startActivity(intent)
        }
    }

    // Handle app background and lifecycle methods
    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }
}
