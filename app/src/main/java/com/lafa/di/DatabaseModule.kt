package com.lafa.di

import android.app.Application
import android.content.Context
import com.lafa.network.local.ProductDao
import com.lafa.network.local.ProductDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {


    @Provides
    @Singleton
    fun providesDatabase(context: Application): ProductDatabase {
        return ProductDatabase.createDatabase(context)
    }

    @Provides
    @Singleton
    fun providesDao(database: ProductDatabase): ProductDao {
        return database.getProductDao()
    }

}