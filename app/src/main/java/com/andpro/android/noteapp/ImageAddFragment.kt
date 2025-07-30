package com.andpro.android.noteapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.graphics.createBitmap
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.andpro.android.noteapp.databinding.FragmentImageAddBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ImageAddFragment : Fragment() {
    companion object {
        var FROM_KEY = "FROM_KEY"
        var FROM_IMAGE_KEY = "FROM_IMAGE_KEY"
    }

    private var REQUEST_IMAGE_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    lateinit private var photoFile: File
    lateinit private var photoUri: Uri
    lateinit private var bitmap: Bitmap
    lateinit private var originalBitMapState: String
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
        originalBitMapState = bitmap.isMutable().toString()
        fun pick() {
            var imageIntent: Intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            var chooserIntent = Intent.createChooser(imageIntent, "Pick an image")
            startActivityForResult(chooserIntent, REQUEST_IMAGE_CODE)
        }
        binding.oldImage.setOnClickListener {
            pick()
        }
        binding.image.setOnClickListener {
            pick()
        }

        binding.newImage.setOnClickListener {
            var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (cameraIntent.resolveActivity(requireContext().packageManager) != null) {
                val timeStamp: String =
                    SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
                val storageDir: File =
                    requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
                photoFile = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
                photoUri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.andpro.android.noteapp.fileprovider",
                    photoFile
                )
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
            }
        }
        binding.commit.setOnClickListener {
            if (!MultipleArgs.isEmpty(
                    MultipleArgs.Image(
                        binding.title.text.toString(),
                        binding.comment.text.toString(),
                        bitmap
                    ), originalBitMapState
                )
            ) {
                setFragmentResult(FROM_KEY, bundleOf(FROM_IMAGE_KEY to true))
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
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_CODE) {
            val imageUri = requireNotNull(data?.data)
            bitmap = MediaStore.Images.Media.getBitmap(
                context?.contentResolver,
                imageUri
            )
            originalBitMapState = bitmap.isMutable().toString()
            binding.image.setImageBitmap(bitmap)
        } else if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAMERA_CODE) {
            bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
            binding.image.setImageBitmap(bitmap)
            originalBitMapState = bitmap.isMutable().toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}