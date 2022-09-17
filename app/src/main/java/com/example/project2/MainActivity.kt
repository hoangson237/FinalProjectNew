package com.example.project2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var login: Button
    lateinit var email: TextView
    lateinit var password: EditText
    // test

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login = findViewById(R.id.Login)
        email = findViewById(R.id.Email)
        password = findViewById(R.id.Password)
        auth= FirebaseAuth.getInstance()

        val register: TextView = findViewById(R.id.Register)
        register.setOnClickListener {
            var intent =Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        login.setOnClickListener{
            if(checking()){
                val email=email.text.toString()
                val password= password.text.toString()
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            var intent = Intent(this,LoggedIn::class.java)
                            intent.putExtra("email",email)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Wrong Details", Toast.LENGTH_LONG).show()
                        }
                    }
            }
            else{
                Toast.makeText(this,"Enter the Details",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checking():Boolean
    {
        if(email.text.toString().trim{it<=' '}.isNotEmpty()
            && password.text.toString().trim{it<=' '}.isNotEmpty())
        {
            return true
        }
        return false
    }
}