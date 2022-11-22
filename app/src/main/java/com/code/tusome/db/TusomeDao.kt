package com.code.tusome.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.code.tusome.models.User

@Dao
interface TusomeDao {
    @Insert
    suspend fun insert(user: User)
//    @Query("select * from user")
//    suspend fun getAll()
}
