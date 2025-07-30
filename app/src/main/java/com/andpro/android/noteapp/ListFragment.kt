package com.andpro.android.noteapp

import android.content.Context
import android.graphics.Bitmap
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
import androidx.core.graphics.createBitmap
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.andpro.android.noteapp.database.ItemRepository
import com.andpro.android.noteapp.databinding.FragmentListBinding
import io.grpc.NameResolver
import kotlinx.coroutines.Job
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
        LVM = ViewModelProvider(this)[ListViewModel::class.java]
        LVM.getImages()
        LVM.getNotes()
        Alert.initialize()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        setFragmentResultListener(TextAddFragment.FROM_KEY) { _, bundle ->
            if (bundle.getBoolean(TextAddFragment.FROM_TEXT_KEY)) {
                val new_note =
                    Item.Note(
                        content = (args.Args as MultipleArgs.Text).content,
                        title = (args.Args as MultipleArgs.Text).title
                    )
                viewLifecycleOwner.lifecycleScope.launch {
                    LVM.addNote(new_note)
                }

            } else if (bundle.getBoolean(ImageAddFragment.FROM_IMAGE_KEY)) {
                val new_image = Item.Image(
                    title = (args.Args as MultipleArgs.Image).title,
                    comment = (args.Args as MultipleArgs.Image).comment
//                    image = (args.Args as MultipleArgs.Image).image
                )
                viewLifecycleOwner.lifecycleScope.launch {
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

//        fun constructList(): MutableList<Item> {
//            val items: MutableList<Item> = mutableListOf()
//            for (i in requireNotNull(LVM.Notes.value)) {
//                items.add(i)
//            }
//            for (t in requireNotNull(LVM.Images.value)) {
//                items.add(t)
//            }
//            return items
//        }

        LVM.Notes.observe(viewLifecycleOwner) {
            Log.d("LVM", "it changes!!!: ${LVM.Notes.value}")
        }
//        LVM.Notes.observe(viewLifecycleOwner) {
//            binding.recycler.adapter = ListAdapter( constructList())
//        }

        val rootView = binding.root
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}