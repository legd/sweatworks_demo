package org.legd.sweatworksdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
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
    private lateinit var referenceGuideLine: Guideline
    private lateinit var favoriteUsersContainer: LinearLayout
    private lateinit var searchBar: SearchView

    private val usersModel: UsersViewModel by viewModels {
        UserViewModelFactory((application as SweatworksApplication).userRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)
        apiUserList = findViewById(R.id.user_list)
        favoriteUserList = findViewById(R.id.favorite_user_list)
        referenceGuideLine = findViewById(R.id.bottom_guideline)
        favoriteUsersContainer = findViewById(R.id.favorite_user_container)
        searchBar = findViewById(R.id.search_bar)
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                fillFavoriteUserList(newText)
                return true
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                fillFavoriteUserList(query)
                return true
            }
        })

        initApiUserRecyclerView()
        initFavoriteUserRecyclerView()

        fillApiUserList()
        fillFavoriteUserList()
    }

    private fun fillApiUserList() {
        usersModel.apiUsers.observe(this, Observer<List<ApiUser>>{ users ->
            apiUserAdapter.updateList(users)
        })
    }

    private fun fillFavoriteUserList(searchText: String = "") {

        if(searchText.isEmpty()) {
            usersModel.favoriteUsers.observe(this) { it
                it.let { favoriteUserAdapter.updateList(it) }
                if (it.isNotEmpty()) {
                    referenceGuideLine.setGuidelinePercent(0.30f)
                    favoriteUsersContainer.visibility = View.VISIBLE
                } else {
                    referenceGuideLine.setGuidelinePercent(0.0f)
                    favoriteUsersContainer.visibility = View.GONE
                }
            }
        } else {
            usersModel.searchUser("%$searchText%").observe(this) { it
                it.let { favoriteUserAdapter.searchResult(it) }
//                if (it.isNotEmpty()) {
//                    referenceGuideLine.setGuidelinePercent(0.30f)
//                    favoriteUsersContainer.visibility = View.VISIBLE
//                } else {
//                    referenceGuideLine.setGuidelinePercent(0.0f)
//                    favoriteUsersContainer.visibility = View.GONE
//                }
            }
        }
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

                    fillApiUserList()
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