package com.example.workinghours.infrastructure.mapper

import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.infrastructure.database.entity.WorkDataEntity
import javax.inject.Inject

class WorkDataMapper @Inject constructor() {

    fun toDomainModel(entity: WorkDataEntity) = entity.run {
        WorkData(
            id = id,
            userId = userId,
            userWorkDate = userWorkDate,
            startWorkTime = startWorkTime,
            endWorkTime = endWorkTime,
            hygieneWorkTime = hygieneWorkTime,
            amountWorkTime = amountWorkTime,
            year = year,
            monthNumber = monthNumber,
            userName = userName,
        )

    }

    fun toEntityModel(workData: WorkData) = workData.run {
        WorkDataEntity(
            id = id,
            userId = userId,
            userWorkDate = userWorkDate,
            startWorkTime = startWorkTime,
            endWorkTime = endWorkTime,
            hygieneWorkTime = hygieneWorkTime,
            amountWorkTime = amountWorkTime,
            year = year,
            monthNumber = monthNumber,
            userName = userName,
        )
    }
}
