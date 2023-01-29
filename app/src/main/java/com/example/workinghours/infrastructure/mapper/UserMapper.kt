package com.example.workinghours.infrastructure.mapper

import com.example.workinghours.domain.model.User
import com.example.workinghours.infrastructure.database.entity.UserEntity
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun toDomainModel(entity: UserEntity) = entity.run {
        User(
            id = entity.id,
            userName = entity.userName,
            userPassword = entity.userPassword,
        )
    }

    fun toEntityModel(user: User) = user.run {
        UserEntity(
            id = id,
            userName = userName,
            userPassword = userPassword,
        )
    }
}