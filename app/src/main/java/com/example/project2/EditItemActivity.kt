package com.example.project2

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.*


class EditItemActivity : AppCompatActivity() {

    private val SELECT_PICTURE = 100

    lateinit var spinner: Spinner
    lateinit var author: TextView
    lateinit var contents: EditText
    lateinit var btSave: Button
    lateinit var btnSelectImage: Button
    lateinit var headingN: EditText

    companion object {
        var authorName = ""
    }

    private lateinit var db: FirebaseFirestore

    val firebaseDatabase = FirebaseDatabase.getInstance();
    val databaseReference = firebaseDatabase.getReference("News");


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)


        val titleN = intent.getStringExtra("key_title")

        Log.d("HHH", "Title2: $titleN")
        val imageN = intent.getStringExtra("key_image")

        val authorNew = intent.getStringExtra("key_author")

        Log.d("HHHH", "Author2: $authorNew")



        author= findViewById(R.id.txtAuthor)
        contents = findViewById(R.id.edtContent)
        btSave = findViewById(R.id.btnSave)
        spinner = findViewById(R.id.spinner)
        headingN = findViewById(R.id.edtHeading)
        btnSelectImage = findViewById(R.id.btnImg);

//        askPermissions()


        val sharedPref=this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin=sharedPref.getString("Email","1")

        var email =intent.getStringExtra("email")



        val spinners = arrayOf("The Thao", "Xa Hoi", "Kinh Te")
        spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, spinners)

//        val item1 = ItemNew("https://images.unsplash.com/photo-1519389950473-47ba0277781c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80\n",
//            title.text.toString(),  author.text.toString(),  contents.text.toString())

        btSave.setOnClickListener {


            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            Log.d("ZZZ", "Title: $currentDate")


            val item1 = ItemNew(imageN.toString(),
                titleN.toString(),
                  HomeActivity.authorName, headingN.text.toString(), contents.text.toString(), currentDate.toString())
            Log.d("TTT", "Title: $titleN")
            Log.d("TTT", "Anh: $imageN")

            titleN?.let { it1 -> saveNews(item1, it1) }

            val mIntent = Intent(this, HomeActivity::class.java)
            startActivity(mIntent)

            // /storage/emulated/0/DCIM/Camera/20220219_231146.jpg

//            selectImage()

        }

        btnSelectImage.setOnClickListener {
//            selectImage()

        }

        author.text = "By " + HomeActivity.authorName
//        setText23(email)

    }

//    fun selectImage() {
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE)
//    }

    fun saveNews(itemNew: ItemNew, titleNew: String){

        databaseReference.child(titleNew).setValue(itemNew);
        Log.d("123", "IT: " + titleNew)
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

//    fun uploadImage(urlString: String) {
//
//        val storage = Firebase.storage
//
//        val storageRef = storage.reference
//
//        val mountainsRef = storageRef.child("img.jpg")
//
//        val stream = FileInputStream(File(urlString))
//        var uploadTask = mountainsRef.putStream(stream)
//        uploadTask.addOnFailureListener {
//            Log.i("CCC" ,"that bai: "+it.message)
//        }.addOnSuccessListener { taskSnapshot ->
//            Log.i("CCC" ,"thanh cong")
//        }
//
//    }
//
//    /* Get the real path from the URI */
//    fun getPathFromURI(contentUri: Uri?): String? {
//        var res: String? = null
//        val proj = arrayOf(MediaStore.Images.Media.DATA)
//        val cursor: Cursor? = contentResolver.query(contentUri!!, proj, null, null, null)
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//                res = cursor.getString(column_index)
//            }
//        }
//        if (cursor != null) {
//            cursor.close()
//        }
//        return res
//    }
//
//    protected fun askPermissions() {
//        val permissions = arrayOf(
//            "android.permission.READ_EXTERNAL_STORAGE",
//            "android.permission.WRITE_EXTERNAL_STORAGE"
//        )
//        val requestCode = 200
//        requestPermissions(permissions, requestCode)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        // code nay de lay image
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK) {
//            if (requestCode == SELECT_PICTURE) {
//                // Get the url from data
//                val selectedImageUri = data?.data
//                if (null != selectedImageUri) {
//                    // Get the path from the Uri
//                    val path = getPathFromURI(selectedImageUri)
//                    Log.i("MainActivity", "Image Path : $path")
//                    // Set the image in ImageView
////                    image.setImageURI(selectedImageUri)
//                    Log.i("AAAA", "IMG.path: " + path.toString())
//                    Log.i("AAAA", "IMG: " + selectedImageUri)
//                    uploadImage(path.toString())
//                }
//            }
//        }
//    }
}


//   /document/image:299
// /storage/emulated/0/Pictures/QHD Wallpapers/qhd_edbe129a478e0fa6215d332e67be84b7.jpeg













