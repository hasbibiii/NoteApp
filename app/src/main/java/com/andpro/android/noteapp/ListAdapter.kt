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
    fun bind(note: Item.Note) {
        binding.title.setText(note.title)
        binding.content.setText(note.content)
    }
}

class ImageListHolder(val binding: ImageListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(_image: Item.Image) {
        binding.title.setText(_image.title)
//        binding.image.setImageBitmap(_image.image)
        binding.comment.setText(_image.comment)
    }
}

class ListAdapter(private val items: MutableList<Item>) :
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
        if (item is Item.Note) {
            (holder as NoteListHolder).bind(item)
        } else if (item is Item.Image) {
            (holder as ImageListHolder).bind(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        when (items[position]) {
            is Item.Note -> {
                return Help.VIEW_TYPE_NOTE
            }

            is Item.Image -> {
                return Help.VIEW_TYPE_IMAGE
            }

            else -> return Help.VIEW_TYPE_UNKNOWN
        }
    }
}