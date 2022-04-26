package com.app.mobile.social

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