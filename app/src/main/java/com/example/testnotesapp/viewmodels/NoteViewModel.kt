package com.example.testnotesapp.viewmodels

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.ParcelFileDescriptor
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.lifecycle.*
import com.example.testnotesapp.data.db.structures.NoteEntity
import com.example.testnotesapp.data.repository.NoteRepository
import com.example.testnotesapp.data.structures.Note
import com.example.testnotesapp.objects.Constants
import com.example.testnotesapp.state.NoteUiState
import com.example.testnotesapp.utils.MediaUtils
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import java.nio.file.Files
import java.time.Instant
import java.time.format.DateTimeFormatter

class NoteViewModel(app: Application):AndroidViewModel(app) {
    private val repository = NoteRepository(app.applicationContext)

    private val _uiState = MutableStateFlow(NoteUiState(loading = true))

    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()

    var selectedNote:Note = Note("test","test",Color.White,Constants.DEFAULT_ID)

    // lista notatek
    var listOfNotes = mutableListOf<Note>()

    val notesHandler = getAllNotes()






    init {
        // wczytanie listy notatek

            listInitialization()

    }

    @SuppressLint("SuspiciousIndentation")
    fun listInitialization(){

        viewModelScope.launch {
        var notesEntities = mutableListOf<NoteEntity>()
            notesHandler.collect{
                _uiState.update {
                    it.copy(loading = true)
                }
                notesEntities.clear()
                it.forEach { item->
                    notesEntities.add(item)
                    Log.d("TEST1234",item.toString())
                }
                listOfNotes.clear()
                notesEntities.forEach { noteEntity ->
                    listOfNotes.add(Note(noteEntity.title, noteEntity.description, Color(noteEntity.color),noteEntity.id))
                }
                _uiState.update {
                    it.copy(notesList = listOfNotes, false)
                }

            }

        }
    }
    fun getAllNotes():Flow<List<NoteEntity>>{

        return repository.getAllNotes()
    }

    fun getNoteById(id:Int):Note{
        listOfNotes.forEach{note->
            if(note.id==id){
                selectedNote = note
                return selectedNote
            }
        }
        return selectedNote
    }
    fun selectColorByIndex(index:Int){
        when(index){
            0 ->{
                selectedNote.color = Color.Red
            }
            1 ->{
                selectedNote.color = Color.Green
            }
            2 ->{
                selectedNote.color = Color.Blue
            }
            else ->{
                selectedNote.color = Constants.DEFAULT_NOTE_COLOR
            }
        }
    }
    fun chooseNoteById(id:Int){
        listOfNotes.forEach{note->
            if(note.id==id){
                selectedNote = note
                return
            }
        }
    }

    fun addNote(note: NoteEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)
        }
    }

    fun updateNote(note: NoteEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }
    fun deleteNote(note: NoteEntity){
        Log.d("TEST1234",listOfNotes.toString())
        _uiState.value = NoteUiState(loading = true)
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
            Log.d("TEST1234",listOfNotes.toString())

            listInitialization()
            Log.d("TEST1234",listOfNotes.toString())

        }
    }
    fun deleteAllNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllNotes()
        }
    }

    // do wywalenie do innego pliku

    private fun generateFile(context:Context,fileName: String): File?{
        val csvFile = File(context.filesDir,fileName)
        csvFile.createNewFile()

        return if(csvFile.exists()){
            csvFile
        }else{
            null
        }
    }


    fun exportNotesToCSVFile(context:Context,fileName:String = DateTimeFormatter.ISO_INSTANT.format(Instant.now()).toString()+".csv"){
        val csvFile = generateFile(context,fileName)
        if(csvFile!=null){
            csvWriter().open(csvFile, append=false){
                // Nagłówki
                writeRow(listOf("[id]","title","description","color"))
                listOfNotes.forEachIndexed{index, note ->
                    writeRow(listOf(note.id,note.title,note.description,note.color.toArgb()))
                }
            }
            Toast.makeText(context,"Succesfully exported!",Toast.LENGTH_SHORT).show()

            val intent = goToFile(context,csvFile)
            context.startActivity(intent)
        }else{
            Toast.makeText(context,"Not exported!",Toast.LENGTH_SHORT).show()
        }
    }

    fun goToFile(context: Context,file:File): Intent{
        val intent = Intent(Intent.ACTION_VIEW)
        val contentUri = FileProvider.getUriForFile(context,"${context.packageName}.fileprovider",file)
        val mimeType = context.contentResolver.getType(contentUri)
        intent.setDataAndType(contentUri,mimeType)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION

        return intent
    }

    // do wczytywania plików


    @RequiresApi(Build.VERSION_CODES.Q)
    fun loadDataFromCsvFile(uri: Uri, context: Context){
        val file = File(MediaUtils.getFilePath(context,uri).toString())
        //val file = context.contentResolver.openFile(uri,"r",null)
        //val file
        //val file2 = context.contentResolver.openInputStream(uri)
        //val inputStream = context.contentResolver.openInputStream(uri)
        val rows= csvReader().readAll(file)
//        csvReader().open(file){
//
//        }
        //Log.d("FILESIMPORT", Files.size(file.toPath()).toString())
        //Log.d("FILESIMPORT", )
    }

}