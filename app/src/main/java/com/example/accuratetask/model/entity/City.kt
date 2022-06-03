package com.example.accuratetask.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class City(
    @PrimaryKey var name : String = "",
    var id: String = ""
) : Parcelable {
    override fun toString(): String {
        return "City(name='$name', id='$id')"
    }
}