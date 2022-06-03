package com.example.accuratetask.util

import com.example.accuratetask.App
import com.example.accuratetask.R

object Validation {

    fun errorRequired(s: String?): String {
        return String.format(App.context.getString(R.string.error_field_required), s)
    }
}