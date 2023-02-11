package com.example.testnotesapp.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testnotesapp.data.example.notesList
import com.example.testnotesapp.ui.theme.TestNotesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesApp()
        }
    }
}

@Composable
fun NotesApp(){
    TestNotesAppTheme {
        val navController = rememberNavController()
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
                FloatingActionButton(onClick = { Toast.makeText(context,"Dodaj notatkę", Toast.LENGTH_SHORT).show() }) {
                    Icon(Icons.Default.Add,"")
                }
            },
            drawerContent = {Text("Nawigacja")},
            bottomBar = { BottomAppBar {
                Text("Dolna nawigacja")
            }
            }
        ){innerPadding->
            NotesNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }

    }
}

@Composable
fun NotesNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = "Main",
        modifier = modifier
    ){
        composable("Main"){
            MainScreen()
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TestNotesAppTheme {
    }
}
// test 2