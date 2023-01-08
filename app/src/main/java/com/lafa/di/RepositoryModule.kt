package com.lafa.di

import com.lafa.network.local.ProductDatabase
import com.lafa.network.remote.ApiService
import com.lafa.repositories.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {



    @Provides
    @Singleton
    fun providesRegisterRepository(apiService: ApiService): RegisterRepository {
        return RegisterRepository(apiService)
    }


    @Provides
    @Singleton
    fun providesLoginRepository(apiService: ApiService): LoginRepository {
        return LoginRepository(apiService)
    }


    @Provides
    @Singleton
    fun providesHomeRepository(apiService: ApiService, db: ProductDatabase): HomeRepository {
        return HomeRepository(apiService, db)
    }


    @Provides
    @Singleton
    fun providesSettingRepository(apiService: ApiService): SettingRepository {
        return SettingRepository(apiService)
    }


    @Provides
    @Singleton
    fun providesQuestionRepository(apiService: ApiService): QuestionRepository {
        return QuestionRepository(apiService)
    }

    @Provides
    @Singleton
    fun providesSearchRepository(apiService: ApiService): SearchRepository {
        return SearchRepository(apiService)
    }


    @Provides
    @Singleton
    fun providesProfileRepository(apiService: ApiService): ProfileRepository {
        return ProfileRepository(apiService)
    }

    @Provides
    @Singleton
    fun providesFavoriteRepository(homeRepository: HomeRepository): FavoriteRepository {
        return FavoriteRepository(homeRepository)
    }

    @Provides
    @Singleton
    fun providesComplaintRepository(apiService: ApiService): ComplaintRepository {
        return ComplaintRepository(apiService)
    }


}