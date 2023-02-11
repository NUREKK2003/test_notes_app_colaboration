package com.example.testnotesapp.views

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testnotesapp.data.example.notesList
import com.example.testnotesapp.data.structures.Note
import com.example.testnotesapp.ui.theme.TestNotesAppTheme

@Composable
fun MainScreen(){
    NotesColumn(notesList = notesList)
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesColumn(
    notesList:List<Note>
){
    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ){
        items(notesList){ note->
            NoteCardItem(title = note.title, description = note.description)
        }
    }
}

@Composable
fun NoteCardItem(title:String,description:String){
    Card(
        elevation = 10.dp
    ) {
        Column {
            Text(text = title)
            Text(text = description)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TestNotesAppTheme {
        MainScreen()
    }
}

