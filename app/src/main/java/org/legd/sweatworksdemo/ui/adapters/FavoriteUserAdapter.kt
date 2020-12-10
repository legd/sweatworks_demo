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
import org.legd.sweatworksdemo.database.models.User
import org.legd.sweatworksdemo.ui.UserDetails

class FavoriteUserAdapter(private val context: Context) : RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserViewHolder> ()  {

    private var favoriteUserList: MutableList<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {

        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.favorite_user, parent, false)
        return FavoriteUserViewHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        val user = this.favoriteUserList[position]
        Glide.with(context)
            .asBitmap()
            .load(user.thumbnail)
            .into(holder.thumbnail)

        holder.userCard.setOnClickListener(View.OnClickListener {
            val userDetailsIntent = Intent(this.context, UserDetails::class.java)
            userDetailsIntent.putExtra("favorite_user", this.favoriteUserList[position])
            this.context.startActivity(userDetailsIntent)
        })
    }

    override fun getItemCount(): Int {
        return this.favoriteUserList.size
    }

    fun updateList(userList: List<User>) {
        this.favoriteUserList.addAll(userList)
        notifyDataSetChanged()
    }

    class FavoriteUserViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val thumbnail = item.findViewById<ImageView>(R.id.favorite_user_thumbnail)
        val userCard = item.findViewById<CardView>(R.id.favorite_user_card)
    }
}