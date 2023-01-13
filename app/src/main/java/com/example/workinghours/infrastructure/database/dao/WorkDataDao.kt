package com.example.workinghours.infrastructure.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.workinghours.infrastructure.database.entities.WorkDataEntity

@Dao
interface WorkDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWordData(vararg workData: WorkDataEntity)

}