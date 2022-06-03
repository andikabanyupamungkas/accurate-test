package com.example.accuratetask.util

object Const {

    interface Global {
        companion object {
            const val ACTION_MODE = "machine_action_mode"
            const val EXTRA_DATA = "machine_extra_data"

            const val MODE_VIEW = "mode_view"
            const val MODE_ADD = "mode_add"
            const val MODE_EDIT = "mode_editable"
        }
    }

    interface Permissions {
        companion object {
            const val RC_STORAGE_PERMS = 1002
        }
    }

}