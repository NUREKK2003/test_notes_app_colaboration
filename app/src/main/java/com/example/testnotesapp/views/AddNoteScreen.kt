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
import com.example.testnotesapp.ui.theme.TestNotesAppTheme
import com.example.testnotesapp.viewmodels.NoteViewModel


@Composable
fun AddNoteScreen(
    onClickSave: () -> Unit = {}
){
    Column() {
        InputTitle()
        Box(modifier = Modifier.weight(1f)){
            InputDescription()
        }
        SaveButton(onClickSave)
    }
}



@Composable
fun InputTitle(
    modifier: Modifier=Modifier
){
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it },
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
    modifier: Modifier=Modifier
){
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it },
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
    onClickSave: () -> Unit = {},
    modifier: Modifier=Modifier
){
    Button(
        onClick = onClickSave,
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