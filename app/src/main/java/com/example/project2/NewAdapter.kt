package com.example.project2

import android.content.Context
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
        val textItemData = itemNew.text

        // sets the image to the imageview from our itemHolder class
        Glide.with(context).load(imgItemData).override(800, 800).into(viewHolder.imgNew)


        // sets the text to the textview from our itemHolder class
        viewHolder.tvTitle.text = textItemData

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
