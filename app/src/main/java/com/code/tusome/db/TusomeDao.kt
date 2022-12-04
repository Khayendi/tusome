package com.code.tusome.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.code.tusome.models.User
import com.code.tusome.models.UserModel

@Dao
interface TusomeDao {
    @Insert
    suspend fun insert(user: UserModel)
//    @Query("select * from user")
//    suspend fun getAll()
}
