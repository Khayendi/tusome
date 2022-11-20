package com.code.tusome.ui.fragments.auth.frags

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Images.Media
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.code.tusome.databinding.FragmentSignUpBinding
import com.code.tusome.ui.viewmodels.MainViewModel


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileIv.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestMediaPermissions()
            } else {
                Intent(Intent.ACTION_PICK).apply {
                    startActivityForResult(this, 200)
                }
            }
        }
        binding.registerBtn.setOnClickListener {
            val username = binding.usernameEt.text.toString().trim()
            val email = binding.emailEt.text.toString().trim()
            val password = binding.passwordEt.text.toString().trim()
            val cPassword = binding.confirmPasswordEt.text.toString().trim()
        }
    }

    private fun requestMediaPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 300
        )
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(Manifest.permission.CAMERA), 400
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 300 && permissions.equals(Manifest.permission.READ_EXTERNAL_STORAGE)
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            Intent(Intent.ACTION_PICK).apply {
                startActivityForResult(this, 200)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200 && requestCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data!!
            val bitmap = Media.getBitmap(requireActivity().contentResolver,imageUri)
            binding.profileIv.setImageBitmap(bitmap)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        val TAG = SignUpFragment::class.java.simpleName
    }
}