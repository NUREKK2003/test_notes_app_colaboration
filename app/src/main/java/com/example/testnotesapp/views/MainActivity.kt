package com.example.testnotesapp.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testnotesapp.ui.theme.TestNotesAppTheme
import kotlinx.coroutines.launch
import com.example.testnotesapp.R

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
        val scaffoldState = rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
        val coroutineScope = rememberCoroutineScope()

        // Przełącznik do ciemnego motywu
        val darkThemeSwitchState = remember{ mutableStateOf(false) }
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = {Text("Notes App")},
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                FloatingActionButton(onClick = { Toast.makeText(context,"Dodaj notatkę", Toast.LENGTH_SHORT).show() }) {
                    Icon(Icons.Default.Add,"")
                }
            },
            drawerContent = {
                Column() {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "User Icon",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(50.dp)
                        )
                        Text(
                            text = "Username",
                            fontSize = MaterialTheme.typography.h5.fontSize,
                            modifier = Modifier
                                .padding(start = 10.dp)
                        )
                    }
                    Divider(
                        color = Color.Black,
                        thickness = 1.dp,
                        modifier = Modifier.padding(top = 20.dp, bottom = 20.dp, start = 10.dp, end = 10.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_nightlight_round_24),
                            contentDescription = "User Icon",
                            modifier = Modifier
                                .size(50.dp)
                        )
                        Text(
                            text = "Tryb nocny",
                            fontSize = MaterialTheme.typography.h5.fontSize,
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp)
                        )
                        Switch(checked = darkThemeSwitchState.value, onCheckedChange = {darkThemeSwitchState.value=it})
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable { Toast.makeText(context,"idź do strony o aplikacji", Toast.LENGTH_SHORT).show() }
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "O aplikacji",
                            fontSize = MaterialTheme.typography.h5.fontSize,
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp)
                        )
                    }

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
        NotesApp()
    }
}
// test 2