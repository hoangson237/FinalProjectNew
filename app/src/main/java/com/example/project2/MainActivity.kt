package com.example.project2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.util.Log
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var login: Button
    lateinit var email: TextInputEditText
    lateinit var password: TextInputEditText

    companion object {
        var isAdminAccount = false
    }

//    lateinit var test: Button
//    lateinit var test2: Button
    // test 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login = findViewById(R.id.Login)
        email = findViewById(R.id.Email)
        password = findViewById(R.id.Password)
//        test = findViewById(R.id.button4);
//        test2 = findViewById(R.id.button3);
        auth = FirebaseAuth.getInstance()



//        test.setOnClickListener {
//            if (checking()) {
//                val email = email.text.toString()
//                val password = password.text.toString()
//                auth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this) { task ->
//                        if (task.isSuccessful) {
//                            var intent = Intent(this, TestActivity::class.java)
//                            intent.putExtra("email", email)
//                            startActivity(intent)
//                            finish()
//                        } else {
//                            Toast.makeText(this, "Wrong Details", Toast.LENGTH_LONG).show()
//                        }
//                    }
//            } else {
//                Toast.makeText(this, "Enter the Details", Toast.LENGTH_LONG).show()
//            }
//        }

        val register: Button = findViewById(R.id.Register)
        register.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        login.setOnClickListener {
            if (checking()) {
                val email = email.text.toString()
                val password = password.text.toString()
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            Log.d("DDD","task.result.user?.email: "+task.result.user?.email)
                            if (task.result.user?.email == "admin@gmail.com"){
                                isAdminAccount = true
                            }
                            var intent = Intent(this, HomeActivity::class.java)
                            intent.putExtra("email", email)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Wrong Details", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Enter the Details", Toast.LENGTH_LONG).show()
            }
        }

//        test2.setOnClickListener {
//            if (checking()) {
//                val email = email.text.toString()
//                val password = password.text.toString()
//                auth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this) { task ->
//                        if (task.isSuccessful) {
//                            var intent = Intent(this, AddItemActivity::class.java)
//                            intent.putExtra("email", email)
//                            startActivity(intent)
//                            finish()
//                        } else {
//                            Toast.makeText(this, "Wrong Details", Toast.LENGTH_LONG).show()
//                        }
//                    }
//            } else {
//                Toast.makeText(this, "Enter the Details", Toast.LENGTH_LONG).show()
//            }
//        }

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