package com.code.tusome.ui.viewmodels

import android.app.Application
import android.net.Uri
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.code.tusome.Tusome
import com.code.tusome.db.TusomeDao
import com.code.tusome.models.User
import com.code.tusome.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var loginStatus:MutableLiveData<Boolean> = MutableLiveData()
    private var registerStatus:MutableLiveData<Boolean> = MutableLiveData()

    @Inject
    lateinit var tusomeDao: TusomeDao
//    @Inject
//    lateinit var firebaseDatabase: FirebaseDatabase

    init {
        (application as Tusome).getRoomComponent().injectMainViewModel(this)
    }

    /**
     * This guy performs login functionality for the current user
     */
    fun login(email: String, password: String): LiveData<Boolean> {
        viewModelScope.launch {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    loginStatus.postValue(true)
                }.addOnFailureListener {
                    loginStatus.postValue(false)
                }
        }
        return loginStatus
    }

    /**
     * This guy send image to firebase storage bucket and the stores the user data in the realtime db
     */
    fun register(
        username: String,
        email: String,
        password: String,
//        imageUri: Uri,
        view: View
    ): LiveData<Boolean> {
        viewModelScope.launch {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val uid = FirebaseAuth.getInstance().currentUser!!.uid
                    val user = User(uid, username, email)
                    FirebaseDatabase.getInstance().getReference("users/$uid")
                        .setValue(user)
                        .addOnSuccessListener {
                            Utils.snackbar(view, "Registration successful, Login")
                            registerStatus.postValue(true)
                        }
                }.addOnFailureListener { e ->
                    registerStatus.postValue(false)
                    Utils.snackbar(view, e.message!!)
                }
        }
//        viewModelScope.launch {
//            val ref = FirebaseStorage.getInstance().getReference("images")
//            ref.putFile(imageUri)
//                .addOnSuccessListener {
//                    ref.downloadUrl.addOnSuccessListener {
//                        val imageUrl = it.toString()
//                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
//                            .addOnSuccessListener {
//                                val uid = FirebaseAuth.getInstance().currentUser!!.uid
//                                val user = User(0,uid, username, email, imageUrl)
//                                FirebaseDatabase.getInstance().getReference("users/$uid")
//                                    .setValue(user)
//                                    .addOnSuccessListener {
//                                        Utils.snackbar(view,"Registration successful, Login")
//                                        registerStatus = true
//                                    }
//                            }.addOnFailureListener { e ->
//                                Utils.snackbar(view, e.message!!)
//                            }
//                    }
//                }
//        }
        return registerStatus
    }

}