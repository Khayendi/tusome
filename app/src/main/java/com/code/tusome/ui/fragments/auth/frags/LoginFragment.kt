package com.code.tusome.ui.fragments.auth.frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.code.tusome.R
import com.code.tusome.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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