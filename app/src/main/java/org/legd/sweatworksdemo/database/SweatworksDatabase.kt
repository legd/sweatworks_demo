package org.legd.sweatworksdemo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.legd.sweatworksdemo.database.dao.UserDao
import org.legd.sweatworksdemo.database.models.User

@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)
abstract class SweatworksDatabase : RoomDatabase() {

    abstract fun getUSerDao() : UserDao

    companion object {

        @Volatile
        private var INSTANCE: SweatworksDatabase? = null

        fun getDatabase(context: Context): SweatworksDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        SweatworksDatabase::class.java,
                        "sweatworks"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}