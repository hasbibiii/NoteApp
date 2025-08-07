package com.andpro.android.noteapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andpro.android.noteapp.database.ItemRepository
import com.andpro.android.noteapp.databinding.ImageListItemBinding
import com.andpro.android.noteapp.databinding.NoteListItemBinding
import java.util.UUID


class Help() {
    companion object {
        var VIEW_TYPE_NOTE = 0
        var VIEW_TYPE_IMAGE = 1
        var VIEW_TYPE_UNKNOWN = 404
    }
}

private var itemRepo = ItemRepository.getInstance()

class NoteListHolder(val binding: NoteListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(note: Item.Note, noteroute: (id: UUID) -> Unit) {
        binding.title.setText(note.title)
        binding.root.setOnClickListener {
            noteroute(itemRepo.titleToID[note.title]!!)
        }
    }
}

class ImageListHolder(val binding: ImageListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(image: Item.Image, imageroute: (id: UUID) -> Unit) {
        binding.title.setText(image.title)
        binding.root.setOnClickListener {
            imageroute(itemRepo.titleToID[image.title]!!)
        }
    }
}

class ListAdapter(
    private val items: MutableList<Item>,
    private val noteroute: (id: UUID) -> Unit,
    val imageroute: (id: UUID) -> Unit
) :
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
            (holder as NoteListHolder).bind(item, noteroute)
        } else if (item is Item.Image) {
            (holder as ImageListHolder).bind(item, imageroute)
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