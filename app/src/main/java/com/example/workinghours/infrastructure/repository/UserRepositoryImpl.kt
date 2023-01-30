package com.example.workinghours.infrastructure.repository

import com.example.workinghours.domain.model.User
import com.example.workinghours.domain.repository.UserRepository
import com.example.workinghours.infrastructure.database.dao.UserDao
import com.example.workinghours.infrastructure.mapper.UserMapper
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val mapper: UserMapper,
    private val userDao: UserDao,
) : UserRepository {
    override suspend fun saveToFireStore(user: User) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Users")
        val empId = dbRef.push().key!!
        dbRef.child(empId).setValue(mapper.toEntityModel(user))
    }

    override suspend fun getAll() = userDao.getAllUsers().map {
        mapper.toDomainModel(it)
    }

    override suspend fun save(user: User) {
        userDao.saveNewUser(mapper.toEntityModel(user))
    }

    override suspend fun delete(user: User) {
        userDao.deleteUser(mapper.toEntityModel(user))
    }
}
