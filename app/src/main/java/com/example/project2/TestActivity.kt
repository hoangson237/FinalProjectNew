package com.example.project2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class TestActivity : AppCompatActivity() {

    lateinit var userTitle2: TextView
    lateinit var title: TextView
    lateinit var tvdateTime: TextView
    lateinit var description2: TextView
    lateinit var content: TextView
    lateinit var imageTest: ImageView
    private lateinit var db: FirebaseFirestore
    lateinit var test: Button

    val firebaseDatabase = FirebaseDatabase.getInstance();
    val databaseReference = firebaseDatabase.getReference("News");

// https://images.unsplash.com/photo-1519389950473-47ba0277781c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80
// https://images.unsplash.com/photo-1496065187959-7f07b8353c55?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80
// https://images.unsplash.com/photo-1517404215738-15263e9f9178?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


        // gia tri cua item
        val titleN = intent.getStringExtra("key_title")
        val authorN = intent.getStringExtra("key_author")
        val imageN = intent.getStringExtra("key_image")
        val contentN = intent.getStringExtra("key_content")
        val datetimeN = intent.getStringExtra("key_date")


        Log.d("DDD", "Date: $datetimeN")



        userTitle2 = findViewById(R.id.tvName2);
        imageTest = findViewById(R.id.imageView);
        title = findViewById(R.id.textView4)
        tvdateTime = findViewById(R.id.textView5)
        description2 = findViewById(R.id.textView6)
        content = findViewById(R.id.textView7)
        test = findViewById(R.id.button2)


        title.text = titleN
        content.text = contentN
        userTitle2.text = authorN
        tvdateTime.text = datetimeN


        val sharedPref=this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin=sharedPref.getString("Email","1")

        var email =intent.getStringExtra("email")

        test.setOnClickListener {

//            val item1 = ItemNew("https://images.unsplash.com/photo-1519389950473-47ba0277781c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80\n", "The thao 24744", "Hoang", "ABCDEEEE")
//
//            saveNews(item1)

//            databaseReference.get().addOnSuccessListener {
//
//                val listItem = it.children
//
//                for (item in listItem){
//
//                    val content = item.child("content")
//                    val author = item.child("author")
//                    val title = item.child("title")
//                    val image = item.child("image")
//
//
//                    val item2 = ItemNew(image.toString(), title.toString(), author.toString(), content.toString())
//
//                    Log.i("XXX","it.children: "+item2.content)
//
//
//                }
//            }.addOnFailureListener{
//                Log.e("firebase", "Error getting data", it)
//            }

            databaseReference.child(titleN.toString()).removeValue()

            finish();

        }

        Glide.with(this).load(imageN).
        override(800, 800).into(imageTest)

        setText2(email)

    }

    fun saveNews(itemNew: ItemNew){


        databaseReference.child(itemNew.title).setValue(itemNew);
    }

    private fun setText2(email:String?)
    {
        db= FirebaseFirestore.getInstance()
        if (email != null) {
            db.collection("USERS").document(email).get()
                .addOnSuccessListener {
                        tasks->
                    userTitle2.text= "By "+ tasks.get("Name").toString()

                }
        }
    }




}