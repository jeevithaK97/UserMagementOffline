package com.example.sampledemoapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLoginUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE id = :id")
    fun getuser(id: Int): LiveData<List<User>>

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUserInfo(userInfo: UserInfo)

    @Query("SELECT * FROM userinfo_table ORDER BY id ASC")
    fun readAllUserData(): LiveData<List<UserInfo>>

    @Query("SELECT * FROM userinfo_table WHERE id = :id")
    fun getuserInfo(id: Int): LiveData<List<UserInfo>>

    @Update
    suspend fun updateInfo(userInfo: UserInfo)

    @Delete
    suspend fun deleteInfo(userInfo: UserInfo)

}