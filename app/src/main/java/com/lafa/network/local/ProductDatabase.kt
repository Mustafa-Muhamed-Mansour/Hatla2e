package com.lafa.network.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lafa.common.BannerConverter
import com.lafa.common.Constant
import com.lafa.model.ProductModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Database(entities = [ProductModel::class], version = 1)
@TypeConverters(BannerConverter::class)

abstract class ProductDatabase : RoomDatabase() {

    abstract fun getProductDao(): ProductDao

    companion object {

        @Volatile
        private var instance: ProductDatabase ?= null
        private val lock = Any()


        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also { instance = it }
        }

        fun createDatabase(context: Context): ProductDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ProductDatabase::class.java,
                Constant.PRODUCT_TABLE
            ).fallbackToDestructiveMigration().build()
        }

    }
}