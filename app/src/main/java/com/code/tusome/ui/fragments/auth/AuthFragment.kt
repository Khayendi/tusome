package com.code.tusome.ui.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.code.tusome.R
import com.code.tusome.adapters.AuthAdapter
import com.code.tusome.databinding.FragmentAuthBinding
import com.code.tusome.models.FragObject
import com.code.tusome.ui.fragments.auth.frags.LoginFragment
import com.code.tusome.ui.fragments.auth.frags.SignUpFragment

class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding
    private var currentFrag:Int = 0

    fun setCurrentFrag(frag:Int){
        this.currentFrag = frag
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = ArrayList<FragObject>()
        list.add(FragObject("LOGIN",LoginFragment()))
        list.add(FragObject("REGISTER",SignUpFragment()))
        val adapter = AuthAdapter(list,requireActivity().supportFragmentManager)
        binding.viewPager.adapter = adapter
        binding.viewPager.currentItem = currentFrag
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    companion object {
        val TAG = AuthFragment::class.java.simpleName
    }
}