package com.example.testnotesapp.views

import android.util.Log
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
import com.example.testnotesapp.data.db.structures.NoteEntity
import com.example.testnotesapp.data.example.ExampleData
import com.example.testnotesapp.data.structures.Note
import com.example.testnotesapp.objects.Constants
import com.example.testnotesapp.ui.theme.TestNotesAppTheme
import com.example.testnotesapp.viewmodels.NoteViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    onClickEditNote: () -> Unit = {},
    notesViewModel: NoteViewModel = viewModel()
){
    val refresher by notesViewModel.getAllNotes().collectAsState(initial = emptyList())

    //notesViewModel.listOfNotes.clear()

//    refresher.forEach {item->
//        notesViewModel.listOfNotes.add(Note(item.title, item.description, Color(item.color),item.id))
//    }
    notesViewModel.loadList(refresher)


    val notesUiState by notesViewModel.uiState.collectAsState()
    if(notesUiState.loading){
        Text(text = "Loading...")
    }else{
        //Text(text = notesViewModel.listOfNotes.toString())
        NotesColumn(notesList = notesViewModel.listOfNotes,onClickEditNote,notesViewModel)

    }

}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesColumn(
    notesList:List<Note>,
    onClickEditNote: () -> Unit = {},
    notesViewModel: NoteViewModel = viewModel()
){


    if(notesList.isNotEmpty()){

        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            )
        ){
            items(notesList){note->
                NoteCardItem(
                    note,
                    onClickEditNote,
                    modifier = Modifier.animateItemPlacement(),
                    notesViewModel = notesViewModel
                )
            }

        }
    }
}

@Composable
fun NoteCardItem(
    noteItem:Note,
    onClickEditNote: () -> Unit = {},
    modifier: Modifier=Modifier,
    notesViewModel: NoteViewModel = viewModel()
){
    // widoczność do animacji:
    var isVisible by remember { mutableStateOf(false) }


    // do droplisty
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Edit", "Delete")
    var selectedIndex by remember { mutableStateOf(0) }



    val showRemoveOneDialog by notesViewModel.showRemoveOneDialog.collectAsState(initial = false)


    ConfirmAlertDialog(
        show = showRemoveOneDialog,
        title = "Confirm",
        message = "Do you want to remove this note?",
        confirmText = "YES",
        denyText = "NO",
        onConfirm = {
            notesViewModel.deleteNote(NoteEntity("","",noteItem.id))
            notesViewModel.hideDialogRemoveOne()
        },
        onDimiss = {
            notesViewModel.hideDialogRemoveOne()
        }
    )



        Card(
            elevation = 10.dp,
            shape = RoundedCornerShape(4.dp),
            backgroundColor = noteItem.color,
            modifier = modifier
                .padding(4.dp)
                .height(150.dp)
                .clickable {
                    onClickEditNote.invoke()
                    notesViewModel.chooseNoteById(noteItem.id)
                }
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
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        color = Color.Black
                    )
                    // do wyrównania ostatniego elementu wiersza do prawej
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = {expanded = true }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Notes Options", tint = Color.Black)

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

                                    if(s=="Edit"){
                                        onClickEditNote.invoke()
                                        notesViewModel.chooseNoteById(noteItem.id)
                                    }else if(s=="Delete"){
                                        notesViewModel.openDialogRemoveOne()

                                    }
                                }) {
                                    Text(
                                        text = s,
                                        color = Color.Black
                                    )
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
                    color = Color.Black,
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

