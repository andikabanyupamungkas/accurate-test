package com.example.accuratetask.util

import com.example.accuratetask.model.entity.User

object Event {

    interface MachineEvent {

        fun showToast(message: String)

        fun goBack()

        fun saveMachine(user: User)

    }


}