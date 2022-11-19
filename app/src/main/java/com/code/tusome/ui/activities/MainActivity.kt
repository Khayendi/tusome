package com.code.tusome.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.code.tusome.R
import com.code.tusome.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (FirebaseAuth.getInstance().uid!=null){
            findNavController(R.id.main_nav_container).navigate(R.id.action_splashFragment_to_homeFragment)
        }else{
            findNavController(R.id.main_nav_container).navigate(R.id.action_splashFragment_to_authFragment)
        }
    }
}