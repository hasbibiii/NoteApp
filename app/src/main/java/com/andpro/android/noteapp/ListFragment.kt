package com.andpro.android.noteapp

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.andpro.android.noteapp.databinding.FragmentListBinding

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
            .setTitle("What would you like to add?")
        dialogView.findViewById<Button>(R.id.add_text).setOnClickListener {
            note()
        }
        dialogView.findViewById<Button>(R.id.add_image).setOnClickListener {
            image()
        }
        var alertDialog = builder.show()
        _alertDialog = alertDialog
    }
}

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Alert.initialize()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.addButton.setOnClickListener {
            Alert.getInstance().showAlert(requireContext(), {
                findNavController().navigate(R.id.action_listFragment_to_textAddFragment)
                var new_note: Item.Note = Item.Note(
                    title = ListFragmentArgs.fromBundle(requireArguments()).Title,
                    content = ListFragmentArgs.fromBundle(requireArguments()).Content,
                    checked = false
                )

            }, {
                findNavController().navigate(R.id.action_imageAddFragment_to_listFragment)

            })
        }
        val rootView = binding.root
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}