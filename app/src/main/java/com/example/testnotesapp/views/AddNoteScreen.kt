package com.example.testnotesapp.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testnotesapp.data.db.structures.NoteEntity
import com.example.testnotesapp.objects.Constants
import com.example.testnotesapp.ui.theme.TestNotesAppTheme
import com.example.testnotesapp.viewmodels.NoteViewModel


@Composable
fun AddNoteScreen(
    onClickSave: () -> Unit = {},
    notesViewModel: NoteViewModel = viewModel(),
    noteId:Int = Constants.DEFAULT_ID
){
    val selectedNote = notesViewModel.getNoteById(noteId)
    var textTit = remember { mutableStateOf(selectedNote.title) }

    var textDesc = remember { mutableStateOf(selectedNote.description) }


    Column() {
        InputTitle(textTit)
        Box(modifier = Modifier.weight(1f)){
            InputDescription(textDesc)
        }
        SaveButton(onClickSave,{
            if(selectedNote.id==Constants.DEFAULT_ID){
                notesViewModel.addNote(NoteEntity(textTit.value,textDesc.value, color = selectedNote.color.toString()))
            }else{
                notesViewModel.addNote(NoteEntity(textTit.value,textDesc.value,selectedNote.id, color = selectedNote.color.toString()))
            }
            })
    }
}



@Composable
fun InputTitle(
    text:MutableState<String>,
    modifier: Modifier=Modifier
){
    TextField(
        value = text.value,
        onValueChange = { text.value = it },
        placeholder = {Text("title...")},
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(10.dp)
    )
}
@Composable
fun InputDescription(
    text:MutableState<String>,
    modifier: Modifier=Modifier
){
    TextField(
        value = text.value,
        onValueChange = { text.value = it },
        placeholder = {Text("description...")},
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .padding(10.dp)
            .fillMaxHeight()
            .fillMaxWidth()
    )
}

@Composable
fun SaveButton(
    onClickGotoMainScreen: () -> Unit = {},
    onClickSave: () -> Unit = {},
    modifier: Modifier=Modifier
){
    Button(
        onClick = {
            onClickSave.invoke()
            onClickGotoMainScreen.invoke()
        },
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text = "Save")
    }
}

@Preview(showBackground = true)
@Composable
fun AddNotePreview(){
    TestNotesAppTheme {
        AddNoteScreen()
    }
}