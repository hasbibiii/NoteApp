package com.andpro.android.noteapp.Note

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.andpro.android.noteapp.MultipleArgs
import com.andpro.android.noteapp.databinding.FragmentTextAddBinding

class TextAddFragment : Fragment() {
    companion object {
        var FROM_TEXT_KEY = "FROM_TEXT_KEY"
        var FROM_KEY = "FROM_KEY"
    }

    private var _binding: FragmentTextAddBinding? = null
    private val binding: FragmentTextAddBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTextAddBinding.inflate(inflater, container, false)
        binding.commit.setOnClickListener {
            if (!MultipleArgs.isEmpty(
                    MultipleArgs.Text(
                        binding.title.text.toString(),
                        binding.content.text.toString()
                    )
                )
            ) {
                setFragmentResult(FROM_KEY, bundleOf(FROM_TEXT_KEY to true))
                findNavController().navigate(
                    TextAddFragmentDirections.actionTextAddFragmentToListFragment(
                        MultipleArgs.Text(
                            binding.title.text.toString(),
                            binding.content.text.toString()
                        )
                    )
                )
            } else {
                Log.d("TAF", "This fool didn't type in anything")
            }
        }
        val rootView = binding.root
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}