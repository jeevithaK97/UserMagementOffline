package com.example.sampledemoapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name: String,
    val email: String,
    val password: String)

@Entity(tableName = "userinfo_table")
data class UserInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val phoneno: String,
    val email: String,
    val image: String
)
