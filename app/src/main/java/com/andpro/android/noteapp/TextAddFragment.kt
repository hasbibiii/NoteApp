package com.andpro.android.noteapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.andpro.android.noteapp.databinding.FragmentTextAddBinding


class TextAddFragment : Fragment() {
    private var _binding: FragmentTextAddBinding? = null
    private val binding: FragmentTextAddBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentTextAddBinding.inflate(inflater, container, false)
        binding.commit.setOnClickListener {
            TextAddFragmentDirections.actionTextAddFragmentToListFragment(
                binding.title.text.toString(),
                binding.content.text.toString()
            )
        }
        val rootView = binding.root
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}