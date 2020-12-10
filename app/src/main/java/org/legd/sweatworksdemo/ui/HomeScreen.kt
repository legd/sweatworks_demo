package org.legd.sweatworksdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.constraintlayout.widget.Guideline
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.legd.sweatworksdemo.R
import org.legd.sweatworksdemo.api.model.ApiUser
import org.legd.sweatworksdemo.app.SweatworksApplication
import org.legd.sweatworksdemo.ui.adapters.ApiUserAdapter
import org.legd.sweatworksdemo.ui.adapters.FavoriteUserAdapter
import org.legd.sweatworksdemo.viewmodel.UserViewModelFactory
import org.legd.sweatworksdemo.viewmodel.UsersViewModel

class HomeScreen : AppCompatActivity() {

    private lateinit var apiUserList: RecyclerView
    private lateinit var favoriteUserList: RecyclerView
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var apiUserAdapter: ApiUserAdapter
    private lateinit var favoriteUserAdapter: FavoriteUserAdapter
    private val usersModel: UsersViewModel by viewModels {
        UserViewModelFactory((application as SweatworksApplication).userRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)
        apiUserList = findViewById(R.id.user_list)
        favoriteUserList = findViewById(R.id.favorite_user_list)
        val referenceGuideLine = findViewById<Guideline>(R.id.bottom_guideline)
        val favoriteUsersContainer = findViewById<LinearLayout>(R.id.favorite_user_container)

        fiilApiUserList()

        usersModel.favoriteUsers.observe(this) { it
            it.let { favoriteUserAdapter.updateList(it) }
            if (it.isNotEmpty()) {
                referenceGuideLine.setGuidelinePercent(0.25f)
                favoriteUsersContainer.visibility = View.VISIBLE
            } else {
                referenceGuideLine.setGuidelinePercent(0.0f)
                favoriteUsersContainer.visibility = View.GONE
            }
        }

        initApiUserRecyclerView()
        initFavoriteUserRecyclerView()
    }

    private fun fiilApiUserList() {
        usersModel.apiUsers.observe(this, Observer<List<ApiUser>>{ users ->
            // TODO = check null list, pagination
            apiUserAdapter.updateList(users)
        })
    }

    private fun initApiUserRecyclerView() {
        apiUserAdapter = ApiUserAdapter(applicationContext)
        gridLayoutManager = GridLayoutManager(applicationContext, 6)
        apiUserList.layoutManager = gridLayoutManager
        apiUserList.adapter = apiUserAdapter

        apiUserList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if ((apiUserList.layoutManager as GridLayoutManager)
                        .findLastCompletelyVisibleItemPosition() == apiUserAdapter.itemCount - 1) {

                    fiilApiUserList()
                }
            }
        })
    }

    private fun initFavoriteUserRecyclerView() {
        favoriteUserAdapter = FavoriteUserAdapter(this)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        favoriteUserList.layoutManager = layoutManager
        favoriteUserList.adapter = favoriteUserAdapter
    }
}