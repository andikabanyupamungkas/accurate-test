package com.example.accuratetask.ui.home

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.accuratetask.App
import com.example.accuratetask.R
import com.example.accuratetask.util.Validation
import com.example.accuratetask.databinding.ActivityAddUserBinding
import com.example.accuratetask.model.entity.City
import com.example.accuratetask.model.entity.User
import com.example.accuratetask.ui.BaseActivity
import com.example.accuratetask.viewmodel.UserViewModel
import com.example.accuratetask.viewmodel.UserViewModelFactory
import com.example.accuratetask.widget.SelectCityDialog

class AddUserActivity : BaseActivity() {

    private val mViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as App).repository)
    }

    private lateinit var mBinding: ActivityAddUserBinding
    private var user: User? = null
    private var mCity : List<City>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setSupportActionBar(mBinding.appbarDefault.toolbar)
        supportActionBar?.let {
            it.setTitle(R.string.app_name)
            it.setDisplayHomeAsUpEnabled(true)
        }

        getCityFromApi()
        mBinding.btnCity.setOnClickListener { selectCity() }
    }

    private fun saveUser(user: User) {
        val livedata = mViewModel.addUser(user)
        livedata.observe(this@AddUserActivity) {
            it?.let {
                livedata.removeObservers(this@AddUserActivity)
                this@AddUserActivity.finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_user_editable, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.menu_save -> {
                user = User()
                user?.let {
                    it.name = mBinding.etName.text.toString()
                    it.address = mBinding.etAddress.text.toString()
                    it.email = mBinding.etEmail.text.toString()
                    it.phoneNumber = mBinding.etNumber.text.toString()
                    it.city = mBinding.btnCity.text.toString()

                    var hasError = false

                    if (user!!.name.isNullOrBlank()) {
                        mBinding.etName.error =
                            Validation.errorRequired(getString(R.string.alert_name))
                        hasError = true
                    }

                    if (user!!.address.isNullOrBlank()) {
                        mBinding.etAddress.error = Validation.errorRequired(getString(R.string.alert_address))
                        hasError = true
                    }

                    if (user!!.email.isNullOrBlank()) {
                        mBinding.etEmail.error =
                            Validation.errorRequired(getString(R.string.alert_email))
                        hasError = true
                    }

                    if (user!!.phoneNumber.isNullOrBlank()) {
                        mBinding.etNumber.error = Validation.errorRequired(getString(R.string.alert_phone))
                        hasError = true
                    }

                    if (user!!.city.isNullOrBlank()) {
                        mBinding.btnCity.error = Validation.errorRequired(getString(R.string.alert_city))
                        hasError = true
                    }

                    if (!hasError) {
                        Log.d("appname", "Register : $it")
                        saveUser(it)
                    }
                }
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun selectCity() {
        val dialog = SelectCityDialog.newInstance(mCity)
        dialog.setCallback(object : SelectCityDialog.Callback {
            override fun onItemClicked(dialog: SelectCityDialog, s: String) {
                dialog.dismiss()
                mBinding.btnCity.text = s
            }
        })
        dialog.show(supportFragmentManager, "selectCityDialog")
    }

    private fun getCityFromApi() {
        val cityLiveData = mViewModel.cityList
        cityLiveData.observe(this@AddUserActivity) {
            it?.let {
                cityLiveData.removeObservers(this@AddUserActivity)
                mCity = it.data
            }
        }
    }

}