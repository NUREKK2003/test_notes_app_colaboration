package com.nomteam.csvnotesapp.data.structures

import com.nomteam.csvnotesapp.objects.Constants


data class Note(
    val title:String,
    val description:String,
    var color: androidx.compose.ui.graphics.Color,
    val id:Int=Constants.DEFAULT_ID
)
