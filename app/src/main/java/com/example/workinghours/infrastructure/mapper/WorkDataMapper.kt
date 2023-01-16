package com.example.workinghours.infrastructure.mapper

import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.infrastructure.database.entities.WorkDataEntity
import javax.inject.Inject

class WorkDataMapper @Inject constructor() {

    fun toDomainModel(entity: WorkDataEntity) = entity.run {
        WorkData(
            id = id,
            userId = userId,
            userWorkAmount = userWorkAmount,
            userWorkData = userWorkData,
        )

    }

    fun toEntityModel(workData: WorkData) = workData.run {
        WorkDataEntity(
            id = id,
            userId = userId,
            userWorkAmount = userWorkAmount,
            userWorkData = userWorkData,
        )
    }
}
