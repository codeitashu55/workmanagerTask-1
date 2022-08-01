package com.example.workmangertask_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.example.services.ItemRoomDatabase
import com.example.workmangertask_1.databinding.ActivityMainBinding
import com.example.workmangertask_1.model.PostDataBase
import com.example.workmangertask_1.workers.DownloadWorker
import com.example.workmangertask_1.workers.InsertDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataBase: ItemRoomDatabase
    private lateinit var workManager : WorkManager
    private val TAG = "MAIN"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataBase = ItemRoomDatabase.getDatabase(applicationContext)
        workManager = WorkManager.getInstance(applicationContext)
        runBlocking {
            setObserver()
        }
        setClickListener()
    }

    private fun setClickListener() {
        binding.oneTimeRequest.setOnClickListener {
            getDataFromWorker()
        }

        binding.periodicRequest.setOnClickListener {
            doPeriodicWork()
        }
    }

    private suspend fun setObserver() {
        val list = arrayListOf<PostDataBase>()
        val dao = dataBase.postDao()
        var ldList : LiveData<List<PostDataBase>>? = null
       val job =  CoroutineScope(Dispatchers.IO).launch {
            ldList =  dao.getAllItem()
        }
        job.join()
        ldList?.observe(this){
            Log.d(TAG, "bindRecycler: changed")
            list.clear()
            list.addAll(it)
            if(binding.rvPosts.layoutManager == null){
                binding.rvPosts.layoutManager = LinearLayoutManager(this)
            }
            if(binding.rvPosts.adapter == null){
                binding.rvPosts.adapter = PostItemAdapter(list)
            }else{
                binding.rvPosts.adapter!!.notifyDataSetChanged()
            }
        }

    }

    private fun getDataFromWorker() {
        val downloadRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<DownloadWorker>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        ).build()

        val insertDataRequest = OneTimeWorkRequestBuilder<InsertDataWorker>().build()
        workManager.beginWith(downloadRequest).then(insertDataRequest).enqueue()

    }


    private fun doPeriodicWork(){
        val downloadRequest = PeriodicWorkRequestBuilder<DownloadWorker>(10000,TimeUnit.MILLISECONDS).setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        ).build()

        workManager.enqueue(downloadRequest)

    }
}