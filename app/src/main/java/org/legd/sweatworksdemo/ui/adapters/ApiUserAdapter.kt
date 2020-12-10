package org.legd.sweatworksdemo.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.legd.sweatworksdemo.R
import org.legd.sweatworksdemo.api.model.ApiUser
import org.legd.sweatworksdemo.ui.UserDetails

class ApiUserAdapter(private val context: Context) : RecyclerView.Adapter<ApiUserAdapter.UserViewHolder> () {

    private var apiUserList: MutableList<ApiUser> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.user_grid, parent, false)
        return UserViewHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = this.apiUserList[position]
        Glide.with(context)
            .asBitmap()
            .load(user.picture.thumbnail)
            .into(holder.thumbnail)

        holder.userCard.setOnClickListener(View.OnClickListener {
            val userDetailsIntent = Intent(this.context, UserDetails::class.java)
            userDetailsIntent.putExtra("selected_user", this.apiUserList[position])
            this.context.startActivity(userDetailsIntent)
        })
    }

    override fun getItemCount(): Int {
       return this.apiUserList.size
    }

    fun updateList(userList: List<ApiUser>) {
        this.apiUserList.addAll(userList)
        notifyDataSetChanged()
    }

    class UserViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val thumbnail = item.findViewById<ImageView>(R.id.user_thumbnail)
        val userCard = item.findViewById<CardView>(R.id.user_card)
    }
}