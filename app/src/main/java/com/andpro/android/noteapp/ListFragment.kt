package com.andpro.android.noteapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.andpro.android.noteapp.database.ItemRepository
import com.andpro.android.noteapp.databinding.FragmentListBinding
import io.grpc.NameResolver
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        var dialogView = LayoutInflater.from(context).inflate(R.layout.pick_type, null)
        var builder = AlertDialog.Builder(context)
            .setView(dialogView)
        dialogView.findViewById<ImageButton>(R.id.add_text).setOnClickListener {
            note()
        }
        dialogView.findViewById<ImageButton>(R.id.add_image).setOnClickListener {
            image()
        }
        var alertDialog = builder.show()
        _alertDialog = alertDialog
    }
}

class ListFragment : Fragment() {
    private val args: ListFragmentArgs by navArgs()
    lateinit private var LVM: ListViewModel
    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LVM = ViewModelProvider(this).get(ListViewModel::class.java)
        Alert.initialize()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        setFragmentResultListener(TextAddFragment.FROM_KEY) { _, bundle ->
            if (bundle.getBoolean(TextAddFragment.FROM_TEXT_KEY)) {
                var new_note =
                    ItemEntity.NoteEntity(
                        content = (args.Args as MultipleArgs.Text).content,
                        title = (args.Args as MultipleArgs.Text).title,
                        checked = false
                    )
                LVM.addNote(new_note)
                Toast.makeText(
                    (activity as AppCompatActivity),
                    "Note: ${new_note.title}",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (bundle.getBoolean(ImageAddFragment.FROM_IMAGE_KEY)) {
                var new_image = ItemEntity.ImageEntity(
                    title = (args.Args as MultipleArgs.Image).title,
                    comment = (args.Args as MultipleArgs.Image).comment,
                    image = (args.Args as MultipleArgs.Image).image
                )
                LVM.addImage(new_image)

                Toast.makeText(
                    (activity as AppCompatActivity),
                    "Image:${new_image.title}",
                    Toast.LENGTH_SHORT
                ).show()
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
        fun constructList(): MutableList<ItemEntity> {
            val notes = LVM.getNotes()
            val images = LVM.getImages()
            val ItemList: MutableList<ItemEntity> = mutableListOf()
            for(i in notes) {
                ItemList.add(i)
            }
            for(t in images) {
                ItemList.add(t)
            }

            return ItemList
        }

        LVM.notes.observe(viewLifecycleOwner) {newValue ->
            binding.recycler.adapter = ListAdapter(constructList())
        }
        LVM.images.observe(viewLifecycleOwner) {newValue ->
            binding.recycler.adapter = ListAdapter(constructList())
        }
        val rootView = binding.root
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}