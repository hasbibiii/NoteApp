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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.addButton.setOnClickListener {

        }
        val rootView = binding.root
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}