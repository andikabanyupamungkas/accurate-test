package com.example.accuratetask.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.accuratetask.model.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user " +
            "WHERE CASE :filter WHEN 'filter' THEN name LIKE :filterValue " +
            "OR city LIKE :filterValue ELSE 1 END " +
            "ORDER BY " +
            "CASE :sort WHEN 'name' THEN name ELSE id END ASC")
    fun getUserList(sort: String?, filter: String?, filterValue: String?): Flow<List<User>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: List<User>)

}