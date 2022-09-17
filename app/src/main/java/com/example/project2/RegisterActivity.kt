package com.example.project2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    lateinit var name: EditText
    lateinit var phone: EditText
    lateinit var emailRegis: EditText
    lateinit var passRegis: EditText
    lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        name = findViewById(R.id.Name)
        phone = findViewById(R.id.Phone)
        emailRegis = findViewById(R.id.EmailRegister)
        passRegis = findViewById(R.id.PasswordRegister)
        button = findViewById(R.id.Continue)

        auth = FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()
        button.setOnClickListener {
            if (checking()) {
                var email = emailRegis.text.toString()
                var password = passRegis.text.toString()
                var name = name.text.toString()
                var phone = phone.text.toString()
                val user = hashMapOf(
                    "Name" to name,
                    "Phone" to phone,
                    "email" to email
                )
                val Users = db.collection("USERS")
                val query = Users.whereEqualTo("email", email).get()
                    .addOnSuccessListener { tasks ->
                        if (tasks.isEmpty) {
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(this) { task ->
                                    if (task.isSuccessful) {
                                        Users.document(email).set(user)
                                        val intent = Intent(this, LoggedIn::class.java)
                                        intent.putExtra("email", email)
                                        startActivity(intent)
                                        finish()
                                        Toast.makeText(
                                            this,
                                            "Authentication Successed",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Authentication Failed",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                        } else {
                            Toast.makeText(this, "User Already Registered", Toast.LENGTH_LONG)
                                .show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
            } else {
                Toast.makeText(this, "Enter the Details", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun checking():Boolean{
        if(name.text.toString().trim{it<=' '}.isNotEmpty()
            && phone.text.toString().trim{it<=' '}.isNotEmpty()
            && emailRegis.text.toString().trim{it<=' '}.isNotEmpty()
            && passRegis.text.toString().trim{it<=' '}.isNotEmpty()
        )
        {
            return true
        }
        return false
    }
}

