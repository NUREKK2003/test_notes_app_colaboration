package com.example.testnotesapp.views

import android.widget.Toast
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.testnotesapp.ui.theme.TestNotesAppTheme

@Composable
fun MainScreen(){
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Open))
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {Text("Notes App")}
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(onClick = {Toast.makeText(context,"Dodaj notatkę",Toast.LENGTH_SHORT).show() }) {
                Icon(Icons.Default.Add,"")
            }
        },
        drawerContent = {Text("Nawigacja")},
        content = { Text(text = "Miejsce na listę notatek")},
        bottomBar = { BottomAppBar {
            Text("Dolna nawigacja")
        }}
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TestNotesAppTheme {
        MainScreen()
    }
}