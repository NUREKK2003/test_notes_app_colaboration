package com.example.testnotesapp.data.structures

import com.example.testnotesapp.objects.Constants


data class Note(
    val title:String,
    val description:String,
    val color: androidx.compose.ui.graphics.Color,
    val id:Int=Constants.DEFAULT_ID
)
