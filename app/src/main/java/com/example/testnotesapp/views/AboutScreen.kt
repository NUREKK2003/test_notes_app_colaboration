package com.example.testnotesapp.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AboutScreen(){
    Column {
        Title(title = "About app",Modifier.padding(top = 10.dp, start = 5.dp))
        Divider(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp))
        Autors(Modifier.padding(start = 10.dp))
    }
}

@Composable
fun Title(
    title:String,
    modifier: Modifier=Modifier
){
    Text(
        text = title,
        fontStyle = MaterialTheme.typography.h1.fontStyle,
        modifier = modifier
    )
}

@Composable
fun Autors(
    modifier: Modifier=Modifier
){
    Column() {
        Text(text = "Programmers:")
        Text(text = "Nurek")
        Text(text = "Oskar")
        Text(text = "Montas")
    }
}