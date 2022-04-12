package com.feiyatsu.reviewroomdb.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.feiyatsu.reviewroomdb.data.UserDao
import com.feiyatsu.reviewroomdb.data.UserDatabase
import com.feiyatsu.reviewroomdb.data.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, UserDatabase::class.java, "user_database")
        .build()

    @Provides
    @Singleton
    fun providesUserDao(db: UserDatabase) = db.userDao()

    @Provides
    @Singleton
    fun providesRepository(userDao: UserDao) = UserRepository(userDao)
}