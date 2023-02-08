package com.example.workinghours.infrastructure.repository

import com.example.Utils.FIREBASE_PATH_FOR_USER
import com.example.workinghours.domain.model.User
import com.example.workinghours.domain.repository.UserRepository
import com.example.workinghours.infrastructure.database.dao.UserDao
import com.example.workinghours.infrastructure.database.entities.UserEntity
import com.example.workinghours.infrastructure.mapper.UserMapper
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val mapper: UserMapper,
    private val userDao: UserDao,
    private val firebase: DatabaseReference,
) : UserRepository {
    init {
        firebase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                GlobalScope.launch(Dispatchers.IO) {
                    if (snapshot != null) {
                        snapshot.child(FIREBASE_PATH_FOR_USER).children.forEach {
                            val id = it.child("id").value.toString()
                            val userName = it.child("userName").value.toString()
                            val password = it.child("userPassword").value.toString()
                            val entity = UserEntity(
                                id = id,
                                userName = userName,
                                userPassword = password
                            )
                            userDao.saveNewUser(entity)
                        }
                    } else {
                        println("Error")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                error.message
            }
        })
    }

    override suspend fun getAll(): Flow<List<User>> =
        userDao.getAllUsers().map {
            it.map { userEntity ->
                mapper.toDomainModel(userEntity)
            }
        }

    override suspend fun save(userName: String, userPassword: String) {
        val userId = firebase.push().key!!
        val userEntity = mapper.toEntityModel(
            User(
                id = userId,
                userName = userName,
                userPassword = userPassword
            )
        )
        firebase.child(FIREBASE_PATH_FOR_USER).child(userId).setValue(userEntity)
        userDao.saveNewUser(userEntity)
    }

    override suspend fun delete(user: User) {
        firebase.child(FIREBASE_PATH_FOR_USER).child(user.id).removeValue()
        userDao.deleteUser(mapper.toEntityModel(user))
    }
}
