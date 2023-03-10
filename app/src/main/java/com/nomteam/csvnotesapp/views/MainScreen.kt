package com.nomteam.csvnotesapp.views

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nomteam.csvnotesapp.data.db.structures.NoteEntity
import com.nomteam.csvnotesapp.objects.Constants
import com.nomteam.csvnotesapp.ui.theme.TestNotesAppTheme
import com.nomteam.csvnotesapp.viewmodels.NoteViewModel

@Composable
fun MainScreen(
    onClickEditNote: () -> Unit = {},
    notesViewModel: NoteViewModel = viewModel()
){
    val refresher by notesViewModel.getAllNotes().collectAsState(initial = emptyList())

    notesViewModel.listOfNotes.clear()

//    refresher.forEach {item->
//        notesViewModel.listOfNotes.add(Note(item.title, item.description, Color(item.color),item.id))
//    }
    notesViewModel.loadList(refresher)


    val notesUiState by notesViewModel.uiState.collectAsState()
    if(notesUiState.loading){
        Text(text = "Loading...")
    }else{
        //Text(text = notesViewModel.listOfNotes.toString())
        if(!refresher.isEmpty()){
            NotesColumn(notesList = refresher,onClickEditNote,notesViewModel)
        }

    }

}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesColumn(
    notesList:List<NoteEntity>,
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
    }else{
        Spacer(modifier = Modifier.padding(0.dp))
    }
}

@Composable
fun NoteCardItem(
    noteItem:NoteEntity,
    onClickEditNote: () -> Unit = {},
    modifier: Modifier=Modifier,
    notesViewModel: NoteViewModel = viewModel()
){
    // widoczno???? do animacji:
    var isVisible by remember { mutableStateOf(false) }


    // do droplisty
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Edit", "Delete")
    var selectedIndex by remember { mutableStateOf(0) }



    val showRemoveOneDialog by notesViewModel.showRemoveOneDialog.collectAsState(initial = false)

    val noteToDelete by notesViewModel.noteToDelete.collectAsState(initial = Constants.DEFAULT_NOTE_ENTITY)






    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(4.dp),
        backgroundColor = Color(noteItem.color),
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
                    color = Color.Black,
                    modifier=Modifier.fillMaxWidth(.8f)
                )
                // do wyr??wnania ostatniego elementu wiersza do prawej
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
                                    notesViewModel.setNoteToDelete(noteItem)
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

                    ConfirmAlertDialog(
                        show = showRemoveOneDialog,
                        title = "Confirm",
                        message = "Do you want to remove this note?",
                        confirmText = "YES",
                        denyText = "NO",
                        onConfirm = {
                            Log.d("COTU",noteItem.id.toString())
                            notesViewModel.deleteNote(noteToDelete)
                            notesViewModel.hideDialogRemoveOne()
                        },
                        onDimiss = {
                            notesViewModel.hideDialogRemoveOne()
                        }
                    )
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

