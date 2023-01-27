package com.example.workinghours.infrastructure.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workinghours.infrastructure.database.entities.WorkDataEntity
import org.joda.time.LocalDate

@Dao
interface WorkDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWorkData(vararg workData: WorkDataEntity)
    @Query("SELECT * FROM work_data WHERE user_id =:userId")
    suspend fun getAllDate(vararg userId: Int): List<WorkDataEntity>

    @Query("SELECT * FROM work_data WHERE user_work_date =:userWorkDate")
    suspend fun getDateFromOneDay(vararg userWorkDate: LocalDate): List<WorkDataEntity>

    @Query("SELECT * FROM work_data WHERE year =:year AND month_number =:month")
    suspend fun getDateFromOneMonth (year: Int, month: Int): List<WorkDataEntity>
}