package com.example.workmangertask_1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostDataBase(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)