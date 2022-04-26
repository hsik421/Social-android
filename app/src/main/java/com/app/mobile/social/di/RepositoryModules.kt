package com.app.mobile.social.di

import com.app.mobile.social.data.SocialRepository
import com.app.mobile.social.data.SocialRepositoryImpl
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponentManager::class)
interface RepositoryModules {
    @Singleton
    fun providerSocialRepository(repository : SocialRepositoryImpl) : SocialRepository
}