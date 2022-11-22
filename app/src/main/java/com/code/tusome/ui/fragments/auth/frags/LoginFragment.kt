package com.code.tusome.ui.fragments.auth.frags

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.code.tusome.R
import com.code.tusome.databinding.FragmentLoginBinding
import com.code.tusome.ui.viewmodels.MainViewModel
import com.code.tusome.utils.Utils


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            val email = binding.emailEt.text.toString().trim()
            val password = binding.passwordEt.text.toString().trim()
            if (email.isBlank()){
                binding.emailEtl.error="Email is required"
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.emailEtl.error="Invalid email address"
                return@setOnClickListener
            }
            if (password.isBlank()){
                binding.passwordEtl.error="Password is required"
                return@setOnClickListener
            }
            val status = viewModel.login(email, password)
            if (status){
                Utils.snackbar(binding.root,"Login successful")
                findNavController().navigate(R.id.action_authFragment_to_homeFragment)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        val TAG = SignUpFragment::class.java.simpleName
    }
}