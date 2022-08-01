package com.example.workmangertask_1.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workmangertask_1.model.PostDataBase

@Dao
interface PostsDao {
     @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun upSert(item: PostDataBase)

     @Query("select * from  PostDataBase")
     fun getAllItem() : LiveData<List< PostDataBase>>

}