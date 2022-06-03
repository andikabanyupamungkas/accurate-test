package com.example.accuratetask.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accuratetask.util.Const
import com.example.accuratetask.R
import com.example.accuratetask.databinding.ActivityHomeBinding
import com.example.accuratetask.ui.BaseActivity
import com.example.accuratetask.ui.home.adapter.UserAdapter
import com.example.accuratetask.viewmodel.UserViewModel
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import com.example.accuratetask.App
import com.example.accuratetask.model.entity.User
import com.example.accuratetask.util.Resource
import com.example.accuratetask.viewmodel.UserViewModelFactory
import com.example.accuratetask.widget.SortDialog

class HomeActivity : BaseActivity() {

    private val mViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as App).repository)
    }

    private lateinit var mLiveData : LiveData<Resource<List<User>>>

    private lateinit var mBinding: ActivityHomeBinding
    private lateinit var mAdapter: UserAdapter
    private var mSort: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportActionBar?.setTitle(R.string.home)

        mAdapter = UserAdapter()
        populateUI()
    }

    private fun populateUI() {
        mBinding.fabAdd.setOnClickListener {
            startActivity(Intent(this@HomeActivity, AddUserActivity::class.java).apply {
                putExtra(Const.Global.ACTION_MODE, Const.Global.MODE_ADD)
            })
        }

        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val rv = mBinding.rvMain
        rv.layoutManager = lm
        rv.isNestedScrollingEnabled = true
        rv.itemAnimator = DefaultItemAnimator()
        rv.adapter = mAdapter

        getUserFromApi(true, mSort, null)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                val query = "%$newText%"
                getUserFromApi(false, mSort, query)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_sort -> {
                openSortDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openSortDialog() {
        val dialog = SortDialog.newInstance(mSort)
        dialog.show(supportFragmentManager, "SortDialog")
        dialog.setCallback(object : SortDialog.Callback {
            override fun onChanged(sort: String) {
                mSort = sort.ifEmpty { null }
                getUserFromApi(false, mSort, null)
            }
        })
    }

    private fun getUserFromApi(initialLoad: Boolean, sort: String?, searchText: String?) {
        if (!initialLoad) {
            if (mLiveData.hasObservers()) mLiveData.removeObservers(this@HomeActivity)
        }

        mAdapter.submitList(null)

        mLiveData = mViewModel.getUserList(sort, searchText)
        mLiveData.observe(this@HomeActivity) {
            mAdapter.submitList(it.data)
        }
    }

}
