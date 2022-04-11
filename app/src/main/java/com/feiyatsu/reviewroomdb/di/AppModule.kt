package com.feiyatsu.reviewroomdb.di

import android.app.Application
import androidx.room.Room
import com.feiyatsu.reviewroomdb.data.UserDao
import com.feiyatsu.reviewroomdb.data.UserDatabase
import com.feiyatsu.reviewroomdb.data.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(
        app: Application
    ) = Room.databaseBuilder(app, UserDatabase::class.java, "user_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providesUserDao(db: UserDatabase) = db.userDao()

    @Provides
    @Singleton
    fun providesRepository(userDao: UserDao) = UserRepository(userDao)
}