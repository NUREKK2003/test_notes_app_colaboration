package com.example.testnotesapp.views

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.testnotesapp.ui.theme.TestNotesAppTheme
import kotlinx.coroutines.launch
import com.example.testnotesapp.R
import com.example.testnotesapp.data.db.structures.NoteEntity
import com.example.testnotesapp.objects.Constants
import com.example.testnotesapp.viewmodels.NoteViewModel




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

    // Przełącznik do ciemnego motywu
    val darkThemeSwitchState = remember{ mutableStateOf(false) }
    TestNotesAppTheme(darkTheme = darkThemeSwitchState.value) {
        val context = LocalContext.current

        // do otwarcia pliku
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type="*text/csv"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ){activityResult ->
            if(activityResult.resultCode == Activity.RESULT_OK){
                val uri: Uri? = activityResult.data?.data
                Toast.makeText(context,uri.toString(),Toast.LENGTH_SHORT).show()
            }
        }
        
        
        // viewModel
        val noteViewModel: NoteViewModel = viewModel()


        val navController = rememberNavController()
        val scaffoldState = rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
        val coroutineScope = rememberCoroutineScope()

        // aktualna pozycja w nawigacji
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        // czy lista na topAppBarze jest rozsunięta
        var dropDownTopBarMenuExpanded by remember {
            mutableStateOf(false)
        }
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
                    },
                    actions = {
                        TopAppBarButton(imageVector = Icons.Outlined.MoreVert, description = "options") {
                            dropDownTopBarMenuExpanded=true
                        }
                        DropdownMenu(
                            expanded = dropDownTopBarMenuExpanded,
                            onDismissRequest = {
                                dropDownTopBarMenuExpanded=false
                            }
                        ) {
                            DropdownMenuItem(onClick = {
                                dropDownTopBarMenuExpanded=false
                                noteViewModel.exportNotesToCSVFile(context)
                            }) {
                                Text(text = "Export Notes To CSV File")
                            }
                            DropdownMenuItem(onClick = {
                                dropDownTopBarMenuExpanded=false
                                //noteViewModel.openFileChooser(context)
                                launcher.launch(intent)
                            }) {
                                Text(text = "Import notes from CSV File")
                            }
                        }
                    }
                )
            },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                Log.d("NAVIGATIONDESC",navController.currentBackStackEntry?.destination?.route.toString())

                if(navBackStackEntry?.destination?.route.toString()=="Main"){
                    FloatingActionButton(onClick = {
                        navController.navigate("AddNote")
                        noteViewModel.selectedNote = Constants.DEFAULT_NOTE
                    }) {
                        Icon(Icons.Default.Add,"")
                    }
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
                            text = "Night theme",
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
                            .clickable {
                                navController.navigate("AboutScreen")
                            }
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "About app",
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
    modifier: Modifier = Modifier,
    notesViewModel: NoteViewModel = viewModel()
){
    NavHost(
        navController = navController,
        startDestination = "Main",
        modifier = modifier
    ){
        composable("Main"){
            MainScreen({navController.navigate("AddNote")},notesViewModel)
        }
        composable("AddNote"){
            AddNoteScreen ({ navController.navigate("Main")},notesViewModel)
        }
        composable("AboutScreen"){
            AboutScreen()
        }
    }
}

@Composable
fun TopAppBarButton(
    imageVector: ImageVector,
    description:String,
    onClick:()-> Unit
){
    IconButton(onClick = {onClick()}) {
        Icon(imageVector = imageVector, contentDescription = description)
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