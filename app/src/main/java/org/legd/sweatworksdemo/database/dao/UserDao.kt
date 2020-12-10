package org.legd.sweatworksdemo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.legd.sweatworksdemo.database.models.User

/**
 * Dao class for the User database model.
 */
@Dao
interface UserDao {

    /**
     * Insert a single user.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    /**
     * Return all the saved users.
     */
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    /**
     * Return the users with matching name.
     */
    @Query("SELECT * FROM users WHERE full_name LIKE :name")
    fun getUsersByName(name: String): Flow<List<User>>
}