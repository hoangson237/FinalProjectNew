package com.example.project2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {

    lateinit var userTitle: TextView
    private lateinit var db: FirebaseFirestore
    lateinit var testImage: ImageView
    lateinit var recyclerView: RecyclerView

    var Manager: LinearLayoutManager? = null
    var adapter: NewAdapter? = null


// https://images.unsplash.com/photo-1519389950473-47ba0277781c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80
// https://images.unsplash.com/photo-1496065187959-7f07b8353c55?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80
    // https://images.unsplash.com/photo-1517404215738-15263e9f9178?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        userTitle = findViewById(R.id.tvName);
        recyclerView = findViewById(R.id.recyclerview)

        val sharedPref=this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin=sharedPref.getString("Email","1")

        var email =intent.getStringExtra("email")

        setText(email)

        val arrayList = ArrayList<ItemNew>()//Creating an empty arraylist
        val item1 = ItemNew("https://images.unsplash.com/photo-1519389950473-47ba0277781c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80\n", "The thao 24744")
        val item2 = ItemNew("https://images.unsplash.com/photo-1496065187959-7f07b8353c55?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80\n", "The thao 2474")
        val item3 = ItemNew("https://images.unsplash.com/photo-1517404215738-15263e9f9178?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80\n", "The thao 2475")

        arrayList.add(item1)
        arrayList.add(item2)
        arrayList.add(item3)


        Manager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = Manager
        adapter = NewAdapter(arrayList,this)
        recyclerView!!.adapter = adapter


    }

    private fun setText(email:String?)
    {
        db= FirebaseFirestore.getInstance()
        if (email != null) {
            db.collection("USERS").document(email).get()
                .addOnSuccessListener {
                        tasks->
                    userTitle.text= "Xin chao "+ tasks.get("Name").toString()

                }
        }



    }
}