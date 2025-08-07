package com.andpro.android.noteapp.Note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.andpro.android.noteapp.databinding.FragmentNoteViewBinding

class NoteViewFragment : Fragment() {
    private val args: NoteViewFragmentArgs by navArgs()
    private var _binding: FragmentNoteViewBinding? = null
    private val binding: FragmentNoteViewBinding
        get() {
            return _binding!!
        }
    lateinit private var NVM: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NVM = ViewModelProvider(this).get(NoteViewModel::class.java)
        NVM.getNote(args.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteViewBinding.inflate(inflater, container, false)
        binding.title.setText(NVM.clicked_note.value?.title)
        binding.comment.setText(NVM.clicked_note.value?.content)
        val rootView = binding.root
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}