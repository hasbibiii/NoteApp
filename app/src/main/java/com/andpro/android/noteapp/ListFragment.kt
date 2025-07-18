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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.andpro.android.noteapp.databinding.FragmentListBinding
import io.grpc.NameResolver

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
        setFragmentResultListener(TextAddFragment.FROM_TEXT_KEY) { _, bundle ->
            if (bundle.getBoolean(TextAddFragment.FROM_TEXT_KEY)) {
                Log.v("LFFFFFFFF", "We are hereeeeeeeeeeeeeeeeeeeeeeee")
                var new_note =
                    Item.Note(
                        content = (args.Args as MultipleArgs.Text).content,
                        title = (args.Args as MultipleArgs.Text).title,
                        checked = false
                    )
                Toast.makeText(
                    (activity as AppCompatActivity),
                    "${new_note.title}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.addButton.setOnClickListener {
            Alert.getInstance().showAlert(requireContext(), {
                Alert.getInstance()._alertDialog.dismiss()
                findNavController().navigate(ListFragmentDirections.actionListFragmentToTextAddFragment())

            }, {
                findNavController().navigate(ListFragmentDirections.actionListFragmentToImageAddFragment())
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