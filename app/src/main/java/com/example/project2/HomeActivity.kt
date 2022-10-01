package com.example.project2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {

    lateinit var userTitle: TextView
    private lateinit var db: FirebaseFirestore
    lateinit var testImage: ImageView
    lateinit var recyclerView: RecyclerView

    var Manager: LinearLayoutManager? = null
    var adapter: NewAdapter? = null

    val firebaseDatabase = FirebaseDatabase.getInstance();
    val databaseReference = firebaseDatabase.getReference("News");


// https://images.unsplash.com/photo-1519389950473-47ba0277781c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80
// https://images.unsplash.com/photo-1496065187959-7f07b8353c55?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80
// https://images.unsplash.com/photo-1517404215738-15263e9f9178?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        userTitle = findViewById(R.id.tvName);
        recyclerView = findViewById(R.id.recyclerview)

        var email =intent.getStringExtra("email")
        setText(email)
    }

    //lay gia tri tu firebase
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

    override fun onResume() {
        super.onResume()

        val sharedPref=this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin=sharedPref.getString("Email","1")


        val arrayList = ArrayList<ItemNew>()//Creating an empty arraylist
        databaseReference.get().addOnSuccessListener {

            val listItem = it.children

            for (item in listItem){

                val content = item.child("content").value
                val author = item.child("author").value
                val title = item.child("title").value
                val image = item.child("image").value
                val dateT = item.child("dateTimes").value



                val itemNew = ItemNew(image.toString(), title.toString(), author.toString(), content.toString(), dateT.toString())
                arrayList.add(itemNew)

                Log.i("XXX","it.children: "+itemNew.content)
                Log.i("XXX","it.children: "+itemNew.image)
                Log.i("XXX","it.children: "+itemNew.author)
                Log.i("XXX","it.children: "+itemNew.title)

                Manager = LinearLayoutManager(this)
                recyclerView!!.layoutManager = Manager
                adapter = NewAdapter(arrayList,this)
                recyclerView!!.adapter = adapter

            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }


    }
}