package com.example.workmangertask_1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.workmangertask_1.databinding.ItemPostBinding
import com.example.workmangertask_1.model.PostDataBase

class PostItemAdapter(val list: ArrayList<PostDataBase>) :
    RecyclerView.Adapter<PostItemAdapter.ViewHolder>() {
    private val TAG = "Recycler"
    class ViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemPostBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_post,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.apply {
          userId.text = data.id.toString()
          title.text = data.title
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}