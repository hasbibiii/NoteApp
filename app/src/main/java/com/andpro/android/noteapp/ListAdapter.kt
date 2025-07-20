package com.andpro.android.noteapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andpro.android.noteapp.databinding.ImageListItemBinding
import com.andpro.android.noteapp.databinding.NoteListItemBinding


class Help() {
    companion object {
        var VIEW_TYPE_NOTE = 0
        var VIEW_TYPE_IMAGE = 1
        var VIEW_TYPE_UNKNOWN = 404
    }
}

class NoteListHolder(val binding: NoteListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(note: ItemEntity.NoteEntity) {
        binding.title.setText(note.title)
    }
}

class ImageListHolder(val binding: ImageListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(_image: ItemEntity.ImageEntity) {
        binding.title.setText(_image.title)
        binding.image.setImageBitmap(_image.image)
        binding.comment.setText(_image.comment)
    }
}

class ListAdapter(private val items: MutableList<ItemEntity>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        var holder: RecyclerView.ViewHolder? = null
        when (viewType) {
            Help.VIEW_TYPE_NOTE -> {
                val binding: NoteListItemBinding =
                    NoteListItemBinding.inflate(layoutInflater, parent, false)
                holder = NoteListHolder(binding)
            }

            Help.VIEW_TYPE_IMAGE -> {
                val binding: ImageListItemBinding =
                    ImageListItemBinding.inflate(layoutInflater, parent, false)
                holder = ImageListHolder(binding)
            }
        }
        return holder!!
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = items[position]
        if (item is ItemEntity.NoteEntity) {
            (holder as NoteListHolder).bind(item)
        } else if (item is ItemEntity.ImageEntity) {
            (holder as ImageListHolder).bind(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        when (items[position]) {
            is ItemEntity.NoteEntity -> {
                return Help.VIEW_TYPE_NOTE
            }

            is ItemEntity.ImageEntity -> {
                return Help.VIEW_TYPE_IMAGE
            }

            else -> return Help.VIEW_TYPE_UNKNOWN
        }
    }
}