package com.example.sweatworksdemo.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sweatworksdemo.R
import com.example.sweatworksdemo.api.model.ApiUser

class UsersAdapter(var context: Context, var userList: List<ApiUser>) : RecyclerView.Adapter<UsersAdapter.GridItemHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.GridItemHolder {

        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.user_grid, parent, false)
        return GridItemHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: GridItemHolder, position: Int) {
        val user = userList.get(position)
//        holder.thumbnail.setImageURI(user.name)
//        holder.thumbnail.setImageResource(R.drawable.empty)
        Glide.with(context)
            .asBitmap()
            .load(user.picture.thumbnail)
            .into(holder.thumbnail)
    }

    override fun getItemCount(): Int {
       return userList.size
    }

    class GridItemHolder(item: View) : RecyclerView.ViewHolder(item) {
        var thumbnail = item.findViewById<ImageView>(R.id.user_thumbnail)
    }
}