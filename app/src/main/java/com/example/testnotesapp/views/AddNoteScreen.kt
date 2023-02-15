package com.example.testnotesapp.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
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


    Column {
        InputTitle(textTit)
        Box(modifier = Modifier.weight(1f)){
            InputDescription(textDesc)
        }
        DropDownMenu(notesViewModel)
        SaveButton(onClickSave,{
            if(selectedNote.id==Constants.DEFAULT_ID){
                notesViewModel.addNote(NoteEntity(textTit.value,textDesc.value, color = selectedNote.color.toArgb()))
            }else{
                notesViewModel.addNote(NoteEntity(textTit.value,textDesc.value,selectedNote.id, color = selectedNote.color.toArgb()))
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
fun DropDownMenu(
    notesViewModel: NoteViewModel = viewModel()
){
    var expanded by remember{ mutableStateOf(false) }
    val list = listOf("Red","Green","Blue")
    var selectedItem by remember{ mutableStateOf("") }

    var textFilledSize by remember{
        mutableStateOf(Size.Zero)
    }

    val icon = if(expanded){
        Icons.Filled.KeyboardArrowDown
    }else{
        Icons.Filled.KeyboardArrowUp
    }

    Column(modifier = Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {selectedItem=it},
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFilledSize = coordinates.size.toSize()
                },
            label = {Text(text = "Select Color")},
            trailingIcon = {
                Icon(icon,"",Modifier.clickable { expanded = !expanded })
            },
            enabled = false
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false},
            modifier = Modifier
                .width(with(LocalDensity.current){textFilledSize.width.toDp()})
        ){
            list.forEach { label->
                DropdownMenuItem(onClick = {
                    selectedItem = label
                    expanded = false
                    notesViewModel.selectColorByIndex(list.indexOf(label))
                }) {
                    Text(text = label)
                }
            }
        }

    }
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