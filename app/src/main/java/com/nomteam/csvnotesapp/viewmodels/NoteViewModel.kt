package com.nomteam.csvnotesapp.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.FileProvider
import androidx.lifecycle.*
import com.nomteam.csvnotesapp.data.db.structures.NoteEntity
import com.nomteam.csvnotesapp.data.db.structures.Settings
import com.nomteam.csvnotesapp.data.repository.NoteRepository
import com.nomteam.csvnotesapp.data.repository.SettingsRepository
import com.nomteam.csvnotesapp.data.structures.Note
import com.nomteam.csvnotesapp.objects.Constants
import com.nomteam.csvnotesapp.state.NoteUiState
import com.nomteam.csvnotesapp.utils.MediaUtils
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import java.time.Instant
import java.time.format.DateTimeFormatter

class NoteViewModel(app: Application):AndroidViewModel(app) {
    private val repository = NoteRepository(app.applicationContext)

    private val settingsRepository = SettingsRepository(app.applicationContext)

    private val _uiState = MutableStateFlow(NoteUiState(loading = true))

    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()

    var selectedNote:Note = Note("test","test",Color.White,Constants.DEFAULT_ID)

    // lista notatek
    var listOfNotes = mutableListOf<Note>()

    val notesHandler = getAllNotes()

    // Timer

//    val MyTimer = object : CountDownTimer(Constants.SAVE_COOLDOWN_TIME, 1000) {
//        override fun onTick(millisUntilFinished: Long) {
//            // Do something on each tick (interval)
//        }
//
//        override fun onFinish() {
//            disableSaveCooldown()
//        }
//    }


    // alert Dialogi


    private val _showDialogRemoveAll = MutableStateFlow(false)
    val showRemoveAllDialog: StateFlow<Boolean> = _showDialogRemoveAll.asStateFlow()

    private val _showDialogRemoveOne = MutableStateFlow(false)
    val showRemoveOneDialog: StateFlow<Boolean> = _showDialogRemoveOne.asStateFlow()

    private val _saveCooldown = MutableStateFlow(false)
    val saveCooldown: StateFlow<Boolean> = _saveCooldown.asStateFlow()



    private val _noteToDelete = MutableStateFlow(Constants.DEFAULT_NOTE_ENTITY)
    val noteToDelete: StateFlow<NoteEntity> = _noteToDelete.asStateFlow()


    fun setNoteToDelete(note:NoteEntity){
        _noteToDelete.value = note
    }





    init {
        // wczytanie listy notatek

            //listInitialization()

    }

//    private fun disableSaveCooldown(){
//        _saveCooldown.value=false
//        MyTimer.cancel()
//    }
//    private fun enableSaveCooldown(){
//        _saveCooldown.value=true
//        MyTimer.start()
//    }

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
    fun getSettings():Flow<List<Settings>>{
        return settingsRepository.getAllSettings()
    }
    fun updateSettings(settings:Settings){
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.addSettings(settings)
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
        //Log.d("TEST1234",listOfNotes.toString())
        _uiState.value = NoteUiState(loading = true)
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
            //Log.d("TEST1234",listOfNotes.toString())

            //listInitialization()
            //Log.d("TEST1234",listOfNotes.toString())

        }
    }
    fun deleteAllNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            listOfNotes.clear()
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

    fun loadList(refresher:List<NoteEntity>){
        _uiState.value = NoteUiState(loading = true)
        if(refresher.isNotEmpty()){
            listOfNotes.clear()
        }

        refresher.forEach {item->
            listOfNotes.add(Note(item.title, item.description, Color(item.color),item.id))
        }

        _uiState.value = NoteUiState(loading = false, notesList = listOfNotes)
    }

    // do wczytywania plików


    fun loadDataFromCsvFile(uri: Uri, context: Context){
        val file = File(MediaUtils.getFilePath(context,uri).toString())

        _uiState.value = NoteUiState(loading = true)
        viewModelScope.launch {
            csvReader().open(file){
                readAllAsSequence().forEachIndexed { index,row: List<String> ->
                    if(index!=0){
                        addNote(NoteEntity(title = row[1], description = row[2], color = row[3].toInt()))
                    }
                    Log.d("FILESIMPORT", row.toString())
                }
            }
            //listInitialization()
        }
    }


    fun openDialogRemoveAll(){
        _showDialogRemoveAll.value = true
    }
    fun hideDialogRemoveAll(){
        _showDialogRemoveAll.value = false
    }
    fun openDialogRemoveOne(){
        _showDialogRemoveOne.value = true
    }
    fun hideDialogRemoveOne(){
        _showDialogRemoveOne.value = false
    }

}