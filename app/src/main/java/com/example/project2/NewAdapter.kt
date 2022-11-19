package com.example.project2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class NewAdapter(private val dataSet: ArrayList<ItemNew> , val context: Context) :
    RecyclerView.Adapter<NewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView
        val imgNew: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            tvTitle = view.findViewById(R.id.tvTitle)
            imgNew = view.findViewById(R.id.imgNew)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_item_new, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {


        val itemNew = dataSet[position]
        val imgItemData = itemNew.image
        val textItemData = itemNew.title
//        val textAuthorData = itemNew.author
//        val textContextData = itemNew.content
//        val dateTimeI = itemNew.dateTimes


        // sets the image to the imageview from our itemHolder class
        Glide.with(context).load(imgItemData).override(800, 800).into(viewHolder.imgNew)


        // sets the text to the textview from our itemHolder class
        viewHolder.tvTitle.text = textItemData


        viewHolder.imgNew.setOnClickListener(View.OnClickListener { view ->
            Log.d("AAAA", "ssss")

            val intent = Intent(view.context, DetailNews::class.java)
            val bundle = Bundle()

            val title: String = itemNew.title
            val content: String = itemNew.content
            val heading: String = itemNew.heading
            val image: String = itemNew.image
            val author: String = itemNew.author
            val dateTime: String = itemNew.dateTimes


            Log.i("DDDD", "date: " + itemNew.dateTimes)


            bundle.putString("key_title", title)
            bundle.putString("key_content", content)
            bundle.putString("key_image", image)
            bundle.putString("key_author", author)
            bundle.putString("key_heading", heading)
            bundle.putString("key_date", dateTime)


            Log.i("aaa", "list: " + itemNew)

            intent.putExtras(bundle)
                view.context.startActivity(intent)

        })
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
