package com.example.project2

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {


    lateinit var userTitle: TextView
    private lateinit var db: FirebaseFirestore
    private var searchView: SearchView? = null
    lateinit var recyclerView: RecyclerView

    companion object {
        var authorName = ""
    }

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

    fun onFabClick(view: View){
        val intent = Intent(this@HomeActivity, AddItemActivity::class.java)
        startActivity(intent)
    }

    //lay gia tri tu firebase
    private fun setText(email:String?)
    {
        db= FirebaseFirestore.getInstance()
        if (email != null) {
            Log.d("iii", "email: " + email)
            db.collection("USERS").document(email).get()
                .addOnSuccessListener {
                        tasks->
                    Log.d("iii", "email2: " + email)
                    userTitle.text= "Xin chao " + tasks.get("Name").toString()
                    authorName = tasks.get("Name").toString()

                    Log.d("iiiI", "email: " + tasks.get("Name").toString())

                }
        }
    }
    override fun onResume() {
        super.onResume()

        userTitle.text= "Xin chao $authorName"
        val sharedPref=this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin=sharedPref.getString("Email","1")

        val arrayList = ArrayList<ItemNew>()//Creating an empty arraylist
        databaseReference.get().addOnSuccessListener {

            val listItem = it.children

            for (item in listItem){

                val content = item.child("content").value
                val heading = item.child("heading").value
                val author = item.child("author").value
                val title = item.child("title").value
                val image = item.child("image").value
                val dateT = item.child("dateTimes").value



                val itemNew = ItemNew(image.toString(), title.toString(), heading.toString(), author.toString(), content.toString(), dateT.toString())
                arrayList.add(itemNew)

//                Log.i("XXX","it.children: "+itemNew.content)
//                Log.i("XXX","it.children: "+itemNew.image)
                Log.i("XXX","it.children: "+ author)


            }

            Manager = LinearLayoutManager(this)
            recyclerView!!.layoutManager = Manager
            adapter = NewAdapter(arrayList,this)
            recyclerView!!.adapter = adapter

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView!!.maxWidth = Int.MAX_VALUE
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
//                adapter.getFilter().filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
//                adapter.getFilter().filter(newText)
                return false
            }
        })
        return true
    }

    override fun onBackPressed() {
        if (!searchView?.isIconified()!!) {
            searchView?.setIconified(true)
            return
        }
        super.onBackPressed()
    }

}