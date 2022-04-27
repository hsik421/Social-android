package com.app.mobile.social.di

import com.app.mobile.social.data.SocialRepository
import com.app.mobile.social.data.SocialRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModules {
    @Singleton
    @Provides
    fun providerSocialRepository() : SocialRepository{
        return SocialRepositoryImpl()
    }
}