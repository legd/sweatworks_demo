package com.example.sweatworksdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sweatworksdemo.R
import com.example.sweatworksdemo.api.model.ApiUser
import com.example.sweatworksdemo.ui.adapters.UsersAdapter
import com.example.sweatworksdemo.viewmodel.UsersViewModel

class HomeScreen : AppCompatActivity() {

    private lateinit var storeList: RecyclerView
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var userAdapter: UsersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)
        storeList = findViewById(R.id.user_grid)

        val usersModel: UsersViewModel by viewModels()
        usersModel.getUsers().observe(this, Observer<List<ApiUser>>{ users ->
            userAdapter.userList = users
        })
        gridLayoutManager = GridLayoutManager(applicationContext, 3)
        storeList.layoutManager = gridLayoutManager
        userAdapter = UsersAdapter(applicationContext, emptyList())
        storeList.adapter = userAdapter
    }
}