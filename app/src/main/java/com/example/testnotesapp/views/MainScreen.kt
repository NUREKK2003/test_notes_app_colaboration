package com.example.testnotesapp.views

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testnotesapp.data.example.ExampleData
import com.example.testnotesapp.data.structures.Note
import com.example.testnotesapp.ui.theme.TestNotesAppTheme
import com.example.testnotesapp.viewmodels.NoteViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    notesViewModel: NoteViewModel = viewModel()
){
    val notesUiState by notesViewModel.uiState.collectAsState()
    if(notesUiState.loading){
        Text(text = "Loading...")
        //Text(text = notesViewModel.getAllNotes().collectAsState(initial = emptyList()).value.toString())
    }else{
        NotesColumn(notesList = notesUiState.notesList)
    }

}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesColumn(
    notesList:List<Note>
){
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        )
    ){
        items(notesList){ note->
            NoteCardItem(
                note,
                Modifier.animateItemPlacement()
            )
        }
    }
}

@Composable
fun NoteCardItem(
    noteItem:Note,
    modifier: Modifier=Modifier
){
    // do droplisty
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Edit", "Delete")
    var selectedIndex by remember { mutableStateOf(0) }

    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(4.dp),
        backgroundColor = noteItem.color,
        modifier = modifier
            .padding(4.dp)
            .height(150.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = noteItem.title,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize
                )
                // do wyrównania ostatniego elementu wiersza do prawej
                Spacer(Modifier.weight(1f))
                IconButton(onClick = {expanded = true }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Notes Options")

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(100.dp)
                            .background(
                                Color.White
                            )
                    ) {
                        items.forEachIndexed { index, s ->
                            DropdownMenuItem(onClick = {
                                selectedIndex = index
                                expanded = false
                            }) {
                                Text(text = s)
                            }
                        }
                    }
                }
            }
            Text(
                text = noteItem.description,
                fontSize = MaterialTheme.typography.body1.fontSize,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 5.dp)
            )

        }
    }
}


//@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TestNotesAppTheme {
        MainScreen()
    }
}

