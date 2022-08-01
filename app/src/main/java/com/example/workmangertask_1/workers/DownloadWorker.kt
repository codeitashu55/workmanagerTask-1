package com.example.workmangertask_1.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.workmangertask_1.R
import com.example.workmangertask_1.model.Posts
import com.example.workmangertask_1.retrofitApi.RetrofitInstance
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.random.Random

class DownloadWorker(private val appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    private val CHANNEL_ID = "1"
    override suspend fun doWork(): Result {

       showNotification("downloading...",appContext)
        withContext(Dispatchers.IO){
            val response = RetrofitInstance.api.getPost()
            response.body()?.let{
               list .addAll(it)
                return@let Result.success(
                    workDataOf(
                        "response" to "sucess"
                    ))
            }
            if(!response.isSuccessful){
                Result.failure(
                    workDataOf(
                        "error" to "not success"
                    )
                )
            }else{
                Result.failure(
                    workDataOf(
                        "server" to "failed"
                    )
                )
            }
        }
        return Result.success()
    }

    private fun showNotification(text : String, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel_name"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .setContentTitle(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        NotificationManagerCompat.from(context).notify(1,builder.build())
    }

    companion object{
        var list = arrayListOf<Posts>()
    }
}