package com.lafa.di

import com.lafa.common.Validation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ValidationModule {

    @Provides
    @Singleton
    fun providesCheckValidation(): Validation {
        return Validation()
    }

}