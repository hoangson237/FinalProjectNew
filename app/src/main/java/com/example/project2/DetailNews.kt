package com.example.project2

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SwitchCompat
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class DetailNews : AppCompatActivity() {


    lateinit var heading: TextView
    lateinit var tvdateTime: TextView
    lateinit var author: TextView
    lateinit var content: TextView
    lateinit var imgViews: ImageView
    private lateinit var db: FirebaseFirestore
    lateinit var remove: Button
    lateinit var edit: Button
    lateinit var mBookMarkBtn: SwitchCompat
    var titleEdit = ""
    companion object {
        var authorName = ""
    }


    val firebaseDatabase = FirebaseDatabase.getInstance();
    val databaseReference = firebaseDatabase.getReference("News");

// https://images.unsplash.com/photo-1519389950473-47ba0277781c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80
// https://images.unsplash.com/photo-1496065187959-7f07b8353c55?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80
// https://images.unsplash.com/photo-1517404215738-15263e9f9178?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)


        // gia tri cua item
        val titleN = intent.getStringExtra("key_title")
        val headingN = intent.getStringExtra("key_heading")
        val authorN = intent.getStringExtra("key_author")
        val imageN = intent.getStringExtra("key_image")
        val contentN = intent.getStringExtra("key_content")
        val datetimeN = intent.getStringExtra("key_date")
        val isBookMark = intent.getStringExtra("key_book_mark")



        Log.d("HHH", "Title0: $titleN")

//        Log.d("HHHH", "Author0: $titleNew")


        author = findViewById(R.id.tvAuthor);
        imgViews = findViewById(R.id.imageView);
        tvdateTime = findViewById(R.id.tvDate)
        heading = findViewById(R.id.txtHeading)
        content = findViewById(R.id.tvContent)
        remove = findViewById(R.id.btnRemove)
        edit = findViewById(R.id.btnUpdate)
        mBookMarkBtn = findViewById(R.id.bookMarkBtn)
        //
        content.text = contentN
        tvdateTime.text = datetimeN
        heading.text = headingN
        author.text = authorN

        if (MainActivity.isAdminAccount){
            edit.visibility = View.VISIBLE
            remove.visibility = View.VISIBLE
        } else {
            edit.visibility = View.GONE
            remove.visibility = View.GONE
        }


        val sharedPref=this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin=sharedPref.getString("Email","1")

        var email =intent.getStringExtra("email")

        edit.setOnClickListener {
            var intent = Intent(this, EditItemActivity::class.java)
            intent.putExtra("key_title", titleN)
            Log.d("HHH", "Title1: $titleN")
            intent.putExtra("key_image", imageN)
            intent.putExtra("key_author", authorN)

            Log.d("HHHH", "Author1: $authorN")
            startActivity(intent)
        }

        remove.setOnClickListener {

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

        // Khi click nút Switch
        mBookMarkBtn.setOnClickListener {
            val titleN = intent.getStringExtra("key_title")
            if (isBookMark == "true"){
                bookMarkItem(titleN.toString(),"false")
            } else {
                bookMarkItem(titleN.toString(),"true")
            }
        }

        Glide.with(this).load(imageN).
        override(800, 800).into(imgViews)

//        setText2(email)
        author.text = "By " + HomeActivity.authorName
        Log.d("ZZZ", "isBookMark: $isBookMark")
        Log.d("ZZZ", "titleN: $titleN")

        // Tự bật nút khi biến isBookMark = true
        mBookMarkBtn.isChecked = isBookMark == "true"

    }

    fun bookMarkItem(title: String, isBookMark: String) {
        Log.d("ZZZ", "isBookMark: $isBookMark")
        databaseReference.child(title).child("isBookMark").setValue(isBookMark);
    }

    fun saveNews(itemNew: ItemNew){
        databaseReference.child(itemNew.title).child("isBookMark").setValue(true);
    }

    private fun setText(email:String?)
    {
        db= FirebaseFirestore.getInstance()
        if (email != null) {
            db.collection("USERS").document(email).get()
                .addOnSuccessListener {
                        tasks->
                    author.text= "By "+ tasks.get("Name").toString()
                    authorName = tasks.get("Name").toString()


                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottom_nav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.homeFragment -> {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
            R.id.settingsFragment -> {
                val intent = Intent(this, Settings::class.java)
                startActivity(intent)

            }
            else -> {}
        }
        return true
    }
}