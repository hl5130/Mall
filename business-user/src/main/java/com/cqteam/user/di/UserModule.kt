package com.cqteam.user.di

import com.cqteam.user.data.source.UserDataSource
import com.cqteam.user.data.source.UserLocalDataSource
import com.cqteam.user.data.source.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME
/**
 * Author： 洪亮
 * Time： 2021/9/17 - 5:28 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Qualifier
    @Retention(RUNTIME)
    annotation class LocalUserDataSource

    @Qualifier
    @Retention(RUNTIME)
    annotation class RemoteUserDataSource

    @Singleton
    @LocalUserDataSource
    @Provides
    fun provideUserLocalDataSource(): UserDataSource {
        return UserLocalDataSource()
    }


    @Singleton
    @RemoteUserDataSource
    @Provides
    fun provideUserRemoteDataSource(): UserDataSource {
        return UserRemoteDataSource()
    }
}

