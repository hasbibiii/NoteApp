package com.andpro.android.noteapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andpro.android.noteapp.database.ItemRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.UUID

class ListViewModel : ViewModel() {
    var itemRepo = ItemRepository.getInstance()

    private var _Notes = MutableLiveData<MutableList<Item.Note>>(mutableListOf())
    private var _Images = MutableLiveData<MutableList<Item.Image>>(mutableListOf())

    val Notes: MutableLiveData<MutableList<Item.Note>>
        get() {
            return _Notes
        }

    val Images: MutableLiveData<MutableList<Item.Image>>
        get() {
            return _Images
        }

    fun addNote(note: Item.Note) {
        viewModelScope.launch {
            runBlocking {
                itemRepo.addNote(note)
            }
            Log.d("ListViewModel", "${Notes.value}")
        }
    }

    fun addImage(image: Item.Image) {
        viewModelScope.launch {
            runBlocking {
                itemRepo.addImage(image)
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

    fun getImages() {
        viewModelScope.launch {
            runBlocking {
                _Images.value = itemRepo.getImages()
            }
        }
    }
}