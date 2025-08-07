package com.andpro.android.noteapp.Image

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.andpro.android.noteapp.databinding.FragmentImageViewBinding

class ImageViewFragment : Fragment() {

    private val args: ImageViewFragmentArgs by navArgs()
    private var _binding: FragmentImageViewBinding? = null
    private val binding: FragmentImageViewBinding
        get() {
            return _binding!!
        }
    lateinit private var IVM: ImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IVM = ViewModelProvider(this).get(ImageViewModel::class.java)
        IVM.getImage(args.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageViewBinding.inflate(inflater, container, false)
        binding.title.setText(IVM.clicked_image.value?.title)
        binding.comment.setText((IVM.clicked_image.value?.comment))
        binding.image.setImageBitmap(IVM.clicked_image.value?.image)
        val rootView = binding.root
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}