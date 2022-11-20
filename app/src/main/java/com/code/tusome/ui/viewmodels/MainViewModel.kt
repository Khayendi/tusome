package com.code.tusome.ui.viewmodels

import android.app.Application
import android.net.Uri
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.code.tusome.models.User
import com.code.tusome.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var loginStatus = false
    private var registerStatus = false
    fun login(email: String, password: String): Boolean {
        viewModelScope.launch {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    loginStatus = true
                }.addOnFailureListener {
                    loginStatus = false
                }
        }
        return loginStatus
    }

    fun register(
        username: String,
        email: String,
        password: String,
        imageUri: Uri,
        view: View
    ): Boolean {
        viewModelScope.launch {
            val ref = FirebaseStorage.getInstance().getReference("images")
            ref.putFile(imageUri)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        val imageUrl = it.toString()
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                            .addOnSuccessListener {
                                val uid = FirebaseAuth.getInstance().currentUser!!.uid
                                val user = User(uid, username, email, imageUrl)
                                FirebaseDatabase.getInstance().getReference("users/$uid")
                                    .setValue(user)
                                    .addOnSuccessListener {
                                        Utils.snackbar(view,"Registration successful, Login")
                                        registerStatus = true
                                    }
                            }.addOnFailureListener { e ->
                                Utils.snackbar(view, e.message!!)
                            }
                    }
                }
        }
        return registerStatus
    }

}