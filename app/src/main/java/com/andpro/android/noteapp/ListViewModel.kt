package com.andpro.android.noteapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andpro.android.noteapp.database.ImageEntity
import com.andpro.android.noteapp.database.ItemRepository
import com.andpro.android.noteapp.database.NoteEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ListViewModel : ViewModel() {
    var itemRepo = ItemRepository.getInstance()

    private var _Notes: MutableLiveData<MutableList<Item.Note>> =
        MutableLiveData<MutableList<Item.Note>>(mutableListOf())
    private var _Images: MutableLiveData<MutableList<Item.Image>> =
        MutableLiveData<MutableList<Item.Image>>(mutableListOf())

    val Notes: MutableLiveData<MutableList<Item.Note>>
        get() {
            return _Notes
        }

    val Images: MutableLiveData<MutableList<Item.Image>>
        get() {
            return _Images
        }

    fun addImage(image: Item.Image) {
        viewModelScope.launch {
            itemRepo.addImage(image)
        }
    }

    fun addNote(note: Item.Note) {
        viewModelScope.launch {
            itemRepo.addNote(note)
        }
    }

    fun getImages() {
        viewModelScope.launch {
            runBlocking {
                _Images.value = itemRepo.getImages()
            }
        }
    }

    fun getNotes() {
        viewModelScope.launch {
            runBlocking {
                _Notes.value = itemRepo.getNotes()

            }
        }
    }
}