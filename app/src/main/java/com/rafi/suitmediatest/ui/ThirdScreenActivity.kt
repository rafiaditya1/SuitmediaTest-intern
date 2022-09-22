package com.rafi.suitmediatest.ui

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rafi.suitmediatest.R
import com.rafi.suitmediatest.api.DataItem
import com.rafi.suitmediatest.databinding.ActivityThirdScreenBinding
import com.rafi.suitmediatest.utils.UserPreference
import com.rafi.suitmediatest.viewmodel.UserViewModel

class ThirdScreenActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: ActivityThirdScreenBinding
    private val viewModel by viewModels<UserViewModel>()
    private lateinit var adapter: UserAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var page = 1
    private var totalPage = 1
    private var isLoading = false
    private lateinit var  userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userPreference = UserPreference(this)

        layoutManager = LinearLayoutManager(this)
        binding.swipeRefreshLayout.setOnRefreshListener(this)

//        viewModel.listUser.observe(this) {
//            setUserList(it)
//        }
        getUsers(false)

        binding.rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = adapter.itemCount
                if (!isLoading && page < totalPage) {
                    if (visibleItemCount + pastVisibleItem >= total) {
                        page++
                        getUsers(false)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun getUsers(isOnRefresh: Boolean) {
        isLoading = true
        if (!isOnRefresh) binding.progressBar.visibility = View.VISIBLE
        val parameters = HashMap<String, String>()
        parameters["page"] = page.toString()
        viewModel.listUser.observe(this) {
            setUserList(it)
            binding.progressBar.visibility = View.INVISIBLE
            isLoading = false
            binding.swipeRefreshLayout.isRefreshing = false

        }
    }

    private fun setUserList(user: List<DataItem>) {
        adapter = UserAdapter(user)
        binding.rvUser.adapter = adapter

        val listUser = ArrayList<DataItem>()
        for (users in user) {
            listUser.clear()
            listUser.addAll(user)
        }

        layoutManager = LinearLayoutManager(this)
        binding.swipeRefreshLayout.setOnRefreshListener(this)
        binding.rvUser.layoutManager = layoutManager
        binding.rvUser.setHasFixedSize(true)

        adapter = UserAdapter(listUser)
        binding.rvUser.adapter = adapter

        val name = intent.getStringExtra(NAME).toString()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataItem) {
                userPreference.setSelectedName(data.firstName + " " + data.lastName)
                val intent = Intent(this@ThirdScreenActivity, SecondScreenActivity::class.java)
                startActivity(intent)
            }
        })

    }

    companion object {
        const val NAME = "name"
    }

    override fun onRefresh() {
//        fun setUserList(user: List<DataItem>) {
//            adapter = UserAdapter(user)
//
//            val listUser = ArrayList<DataItem>()
//            for (users in user) {
//                listUser.clear()
//                page = 1
//                getUsers(true)
//            }
            page = 1
            getUsers(true)
    }
}