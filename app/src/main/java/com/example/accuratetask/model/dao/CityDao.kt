package com.example.accuratetask.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.accuratetask.model.entity.City
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    @Query("SELECT * FROM city")
    fun getCityList(): Flow<List<City>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(user: List<City>)
}