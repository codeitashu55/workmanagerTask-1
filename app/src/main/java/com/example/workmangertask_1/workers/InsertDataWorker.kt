package com.example.workmangertask_1.workers

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.room.RoomDatabase
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.services.ItemRoomDatabase
import com.example.workmangertask_1.R
import com.example.workmangertask_1.model.PostDataBase
import com.example.workmangertask_1.retrofitApi.RetrofitInstance
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class InsertDataWorker(private val appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {

        withContext(Dispatchers.IO){
           val db =  ItemRoomDatabase.getDatabase(context = appContext)
           val dao = db.postDao()

           val list = DownloadWorker.list

           for(i in list){
              val item =  PostDataBase(
                   i.id,
                   i.userId,
                   i.title,
                   i.body
               )
               dao.upSert(item)
           }
           return@withContext Result.success(workDataOf(
                "message" to "completed"
            ))
        }

        return Result.success()

    }


}