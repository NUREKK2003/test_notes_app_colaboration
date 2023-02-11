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
import com.example.testnotesapp.ui.theme.TestNotesAppTheme

@Composable
fun AddNoteScreen(){
    Column() {
        InputTitle()
        InputDescription()
        SaveButton()
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
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .padding(10.dp)
            .height(600.dp)
            .fillMaxWidth()
    )
}

@Composable
fun SaveButton(modifier: Modifier=Modifier){
    Button(
        onClick = {},
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