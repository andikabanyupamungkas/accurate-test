package com.example.accuratetask.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User(
    var name: String? = null,
    var address: String? = null,
    var email: String? = null,
    var phoneNumber: String? = null,
    var city: String? = null,
//    @PrimaryKey(autoGenerate = true) var _id: Long? = null,
    @PrimaryKey var id: String = ""
) : Parcelable {
    override fun toString(): String {
        return "User(name=$name, address=$address, email=$email, phoneNumber=$phoneNumber, city=$city, id=$id)"
    }
}
