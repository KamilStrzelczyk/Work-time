package com.example.workinghours.infrastructure.mapper

import com.example.workinghours.domain.model.Admin
import com.example.workinghours.infrastructure.database.entities.AdminEntity
import javax.inject.Inject

class AdminMapper @Inject constructor() {

    fun toDomainModel(entity: AdminEntity) = Admin(
        id = entity.id,
        password = entity.password,
    )

    fun toEntityModel(admin: Admin) = AdminEntity(
        id = admin.id,
        password = admin.password,
    )
}
