package com.example.project2

import android.widget.ImageView

data class ItemNew(val image: String, val title: String, val author: String, val heading: String, val content: String, val dateTimes: String, val isBookMark: Boolean? = false) {
}
