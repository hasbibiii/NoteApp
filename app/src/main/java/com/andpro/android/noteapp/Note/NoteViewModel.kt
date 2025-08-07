package com.andpro.android.noteapp.Note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andpro.android.noteapp.Item
import androidx.lifecycle.viewModelScope
import com.andpro.android.noteapp.database.ItemRepository
import kotlinx.coroutines.launch
import java.util.UUID

class NoteViewModel: ViewModel() {
    private val itemRepo = ItemRepository.getInstance()
    private var _clicked_note = MutableLiveData<Item.Note>(null)
    val clicked_note: MutableLiveData<Item.Note> get() {
        return _clicked_note
    }

    fun getNote(id: UUID) {
        _clicked_note.value = itemRepo.getNote(id)
    }
}