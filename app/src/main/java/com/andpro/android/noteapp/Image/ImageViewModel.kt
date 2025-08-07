package com.andpro.android.noteapp.Image

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andpro.android.noteapp.Item
import com.andpro.android.noteapp.database.ItemRepository
import java.util.UUID

class ImageViewModel : ViewModel() {
    private val itemRepo = ItemRepository.getInstance()
    private var _clicked_image = MutableLiveData<Item.Image>(null)
    val clicked_image: MutableLiveData<Item.Image>
        get() {
            return _clicked_image
        }

    fun getImage(id: UUID) {
        _clicked_image.value = itemRepo.getImage(id)
    }
}