package com.andpro.android.noteapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.createBitmap
import androidx.navigation.fragment.findNavController
import com.andpro.android.noteapp.databinding.FragmentImageAddBinding

class ImageAddFragment : Fragment() {
    private var REQUEST_IMAGE_CODE = 0
    lateinit private var bitmap: Bitmap
    private var _binding: FragmentImageAddBinding? = null
    private val binding: FragmentImageAddBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageAddBinding.inflate(inflater, container, false)
        bitmap = createBitmap(300, 300, Bitmap.Config.ARGB_4444)
        binding.oldImage.setOnClickListener {
            var imageIntent: Intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            var chooserIntent = Intent.createChooser(imageIntent, "Pick an image")
            startActivityForResult(chooserIntent, REQUEST_IMAGE_CODE)
        }
        binding.commit.setOnClickListener {
            if (MultipleArgs.isNotEmpty(
                    MultipleArgs.Image(
                        binding.title.text.toString(),
                        binding.comment.text.toString(),
                        bitmap
                    )
                )
            ) {
                findNavController().navigate(
                    ImageAddFragmentDirections.actionImageAddFragmentToListFragment(
                        MultipleArgs.Image(
                            binding.title.text.toString(),
                            binding.comment.text.toString(),
                            bitmap
                        )
                    )
                )
            } else {
                Log.d("IAF", "This fool didn't type in anything")
            }

        }
        val rootView = binding.root
        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === Activity.RESULT_OK && requestCode === REQUEST_IMAGE_CODE) {
            val imageUri = requireNotNull(data?.data)
            bitmap = MediaStore.Images.Media.getBitmap(
                context?.contentResolver,
                imageUri
            )
            binding.image.setImageBitmap(bitmap)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}