package com.code.tusome.ui.fragments.auth.frags

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Images.Media
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.code.tusome.databinding.FragmentSignUpBinding
import com.code.tusome.ui.fragments.auth.AuthFragment
import com.code.tusome.ui.viewmodels.MainViewModel
import com.code.tusome.utils.Utils


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
            if (checkGalleryPermission()) {
                /**
                 * This is an external service - Implicit intent
                 */
                Intent(Intent.ACTION_PICK,Media.EXTERNAL_CONTENT_URI).apply {
                    type = "image/*"
                    startActivityForResult(this, GALLERY_CODE)
                }
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_CODE
                )
            }
        }
        binding.registerBtn.setOnClickListener {
            val username = binding.usernameEt.text.toString().trim()
            val email = binding.emailEt.text.toString().trim()
            val password = binding.passwordEt.text.toString().trim()
            val cPassword = binding.confirmPasswordEt.text.toString().trim()
            if (username.isBlank()) {
                binding.usernameEtl.error = "Username is required"
                return@setOnClickListener
            }
            if (email.isBlank()) {
                binding.emailEtl.error = "Email is required"
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailEtl.error = "Invalid email address"
                return@setOnClickListener
            }
            if (password.isBlank()) {
                binding.passwordEtl.error = "Password is required"
                return@setOnClickListener
            }
            if (password.length < 8) {
                binding.passwordEtl.error = "Password is too short"
                return@setOnClickListener
            }
            if (cPassword.isBlank()) {
                binding.confirmPasswordEtl.error = "Cannot be empty"
                return@setOnClickListener
            }
            if (password != cPassword) {
                binding.passwordEtl.error = "Passwords do not match"
                binding.confirmPasswordEtl.error = "Passwords do not match"
                return@setOnClickListener
            }
//            if (imageUri==null){
//                Utils.snackbar(binding.root,"N profile image selected")
//                return@setOnClickListener
//            }
//            val status = viewModel.register(username, email, password, imageUri, binding.root)
            val status = viewModel.register(username, email, password, binding.root)
            if (status) {
                AuthFragment().setCurrentFrag(1)
            }
        }
    }

    /**
     * This checks storage permission of the application
     */
    private fun checkGalleryPermission(): Boolean = ActivityCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE &&
            permissions.contentEquals(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)) &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            /**
             * This is an external service - Implicit Intent
             */
            Intent(Intent.ACTION_PICK,Media.EXTERNAL_CONTENT_URI).apply {
                type = "image/*"
                startActivityForResult(this, GALLERY_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_CODE && requestCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data!!
            try {
                val bitmap: Bitmap = Media.getBitmap(requireActivity().contentResolver, imageUri)
                Log.i(TAG, "onActivityResult: ${data.data.toString()}")
                binding.profileIv.setImageBitmap(bitmap)
            } catch (e: Exception) {
                Log.e(TAG, "onActivityResult: ${e.message}")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        val TAG: String = SignUpFragment::class.java.simpleName
        const val PERMISSION_CODE = 200
        const val GALLERY_CODE = 300
    }
}