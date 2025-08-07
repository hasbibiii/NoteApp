package com.andpro.android.noteapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.andpro.android.noteapp.Image.ImageAddFragment
import com.andpro.android.noteapp.Note.TextAddFragment
import com.andpro.android.noteapp.databinding.FragmentListBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.UUID

class Alert() {
    companion object {
        private var INSTANCE: Alert? = null
        fun getInstance(): Alert {
            return requireNotNull(INSTANCE)
        }

        fun initialize() {
            if (INSTANCE == null) {
                INSTANCE = Alert()
            }
        }
    }

    lateinit var _alertDialog: AlertDialog

    fun showAlert(context: Context, note: () -> Unit, image: () -> Unit) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.pick_type, null)
        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
        dialogView.findViewById<ImageButton>(R.id.add_text).setOnClickListener {
            note()
        }
        dialogView.findViewById<ImageButton>(R.id.add_image).setOnClickListener {
            image()
        }
        val alertDialog = builder.show()
        _alertDialog = alertDialog
    }
}

class scripts() {
    companion object {

    }
}

class ListFragment : Fragment() {
    private val args: ListFragmentArgs by navArgs()
    lateinit private var LVM: ListViewModel
    private var job: Job? = null
    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LVM = ViewModelProvider(this)[ListViewModel::class.java]
        Alert.initialize()
    }

    fun loadAdapter() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(1500)
            binding.recycler.adapter = ListAdapter(constructList(), imageroute = { image_title ->
                findNavController().navigate(
                    ListFragmentDirections.actionListFragmentToImageViewFragment(
                        image_title
                    )
                )
            }, noteroute = { note_title ->
                findNavController().navigate(
                    ListFragmentDirections.actionListFragmentToNoteViewFragment(
                        note_title
                    )
                )
            })
        }
    }

    fun constructList(): MutableList<Item> {
        val items: MutableList<Item> = mutableListOf()
        for (i in requireNotNull(LVM.Notes.value)) {
            items.add(i)
        }
        for (t in requireNotNull(LVM.Images.value)) {
            items.add(t)
        }
        items.shuffle()
        return items
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        LVM.getImages()
        LVM.getNotes()
        fun loadItems(acc: () -> Any) {
            job = viewLifecycleOwner.lifecycleScope.launch {
                runBlocking {
                    acc()
                    delay(1000)
                }
            }
        }

        setFragmentResultListener(TextAddFragment.FROM_KEY) { _, bundle ->
            LVM.getImages()
            LVM.getNotes()
            if (bundle.getBoolean(TextAddFragment.FROM_TEXT_KEY)) {
                val new_note =
                    Item.Note(
                        content = (args.Args as MultipleArgs.Text).content,
                        title = (args.Args as MultipleArgs.Text).title
                    )
                loadItems {
                    LVM.addNote(new_note)
                }

            } else if (bundle.getBoolean(ImageAddFragment.FROM_IMAGE_KEY)) {
                val new_image = Item.Image(
                    title = (args.Args as MultipleArgs.Image).title,
                    comment = (args.Args as MultipleArgs.Image).comment,
                    image = (args.Args as MultipleArgs.Image).image
                )
                loadItems {
                    LVM.addImage(new_image)
                }
            }
        }
        binding.addButton.setOnClickListener {
            Alert.getInstance().showAlert(requireContext(), {
                Alert.getInstance()._alertDialog.dismiss()
                findNavController().navigate(ListFragmentDirections.actionListFragmentToTextAddFragment())
            }, {
                Alert.getInstance()._alertDialog.dismiss()
                findNavController().navigate(ListFragmentDirections.actionListFragmentToImageAddFragment())
            })
        }

        LVM.Notes.observe(viewLifecycleOwner) {
            loadAdapter()
            Log.v("List Fragment", "something changed with the notes")
        }
        LVM.Images.observe(viewLifecycleOwner) {
            loadAdapter()
            Log.v("List Fragment", "something changed with the images")
        }

        val rootView = binding.root
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job?.cancel()
        _binding = null
    }
}