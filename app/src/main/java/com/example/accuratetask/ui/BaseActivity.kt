package com.example.accuratetask.ui

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.accuratetask.model.entity.User
import com.example.accuratetask.util.Event

abstract class BaseActivity : AppCompatActivity(), Event.MachineEvent,
    ActivityCompat.OnRequestPermissionsResultCallback {

    override fun saveMachine(user: User) {
        //do nothing
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun goBack() {
        onBackPressed()
    }

}