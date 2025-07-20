package com.andpro.android.noteapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andpro.android.noteapp.database.ItemRepository
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {

    private var itemRepo = ItemRepository.getInstance()
    lateinit private var Items: List<ItemEntity>
    private var _notes: MutableLiveData<List<ItemEntity.NoteEntity>> = MutableLiveData()
    val notes: MutableLiveData<List<ItemEntity.NoteEntity>>
        get() {
            return _notes
        }

    private var _images: MutableLiveData<List<ItemEntity.ImageEntity>> = MutableLiveData()
    val images: MutableLiveData<List<ItemEntity.ImageEntity>>
        get() {
            return _images
        }

    fun addNote(_note: ItemEntity.NoteEntity) {
        viewModelScope.launch {
            itemRepo.addNote(_note)
        }
    }

    fun addImage(_image: ItemEntity.ImageEntity) {
        viewModelScope.launch {
            itemRepo.addImage(_image)
        }
    }

    fun getNotes(): List<ItemEntity.NoteEntity> {
        var returnvalue: List<ItemEntity.NoteEntity>? = null
        viewModelScope.launch {
            _notes.value = itemRepo.getNotes()
            returnvalue = _notes.value
        }
        return returnvalue!!
    }

    fun getImages(): List<ItemEntity.ImageEntity> {
        var returnvalue: List<ItemEntity.ImageEntity>? = null
        viewModelScope.launch {
            _images.value = itemRepo.getImages()
            returnvalue = _images.value
        }
        return returnvalue!!
    }
}