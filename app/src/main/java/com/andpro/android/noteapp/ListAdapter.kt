package com.andpro.android.noteapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andpro.android.noteapp.databinding.NotelistitemBinding


class Help() {
    companion object {
        var VIEW_TYPE_NOTE = 0
        var VIEW_TYPE_IMAGE = 1
        var VIEW_TYPE_UNKNOWN = 404
    }
}

class NoteListHolder(val binding: NotelistitemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(note: Item.Note) {
        binding.title.setText(note.title)
    }
}

class ListAdapter(private val items: List<Item>, viewType: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        var holder: RecyclerView.ViewHolder? = null
        when (viewType) {
            Help.VIEW_TYPE_NOTE -> {
                val binding: NotelistitemBinding =
                    NotelistitemBinding.inflate(layoutInflater, parent, false)
                holder = NoteListHolder(binding)
            }
        }
        return holder!!
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = items[position]
        when (item) {
            is Item.Note -> {
                (holder as NoteListHolder).bind(item)
            }

            is Item.Image -> TODO()
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