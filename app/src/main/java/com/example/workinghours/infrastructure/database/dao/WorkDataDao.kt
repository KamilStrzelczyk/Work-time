package com.example.workinghours.infrastructure.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.infrastructure.database.entities.WorkDataEntity

@Dao
interface WorkDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWorkData(vararg workData: WorkDataEntity)

    @Query("SELECT * FROM work_data WHERE id =:id")
    suspend fun getAllDate(id: Int): List<WorkDataEntity>
}