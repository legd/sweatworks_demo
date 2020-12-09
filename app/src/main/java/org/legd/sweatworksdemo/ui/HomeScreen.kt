package org.legd.sweatworksdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.legd.sweatworksdemo.R
import org.legd.sweatworksdemo.api.model.ApiUser
import org.legd.sweatworksdemo.ui.adapters.ApiUserAdapter
import org.legd.sweatworksdemo.viewmodel.UsersViewModel

class HomeScreen : AppCompatActivity() {

    private lateinit var apiUserList: RecyclerView
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var userAdapter: ApiUserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)
        apiUserList = findViewById(R.id.user_list)

        val apiUsersModel: UsersViewModel by viewModels()
        apiUsersModel.getUsers().observe(this, Observer<List<ApiUser>>{ users ->
            // TODO = check null list, pagination
            userAdapter.updateList(users)
        })

        gridLayoutManager = GridLayoutManager(applicationContext, 6)
        apiUserList.layoutManager = gridLayoutManager
        userAdapter = ApiUserAdapter(applicationContext)
        apiUserList.adapter = userAdapter
    }
}