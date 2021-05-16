package com.example.mydb

import androidx.annotation.NonNull
import androidx.room.*

@Entity
data class UserModel (
    @PrimaryKey val userid: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "age") val age: String?
)

@Dao
interface UserDao {
    @Query("SELECT * FROM usermodel")
    fun readAllUsers(): List<UserModel>

    @Insert
    fun insertUser(vararg users: UserModel)

    @Delete
    fun deleteUser(user: UserModel)
}

@Database(entities = arrayOf(UserModel::class), version = 1)
abstract class MyRoom : RoomDatabase() {
    abstract fun userDao(): UserDao
}