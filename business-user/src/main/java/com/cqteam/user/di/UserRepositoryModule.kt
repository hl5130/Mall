package com.cqteam.user.di

import com.cqteam.user.data.repository.DefaultUserRepository
import com.cqteam.user.data.repository.UserRepository
import com.cqteam.user.data.source.UserDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Author： 洪亮
 * Time： 2021/9/17 - 5:35 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
@Module
@InstallIn(SingletonComponent::class)
object UserRepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        @UserModule.LocalUserDataSource userLocalDataSource: UserDataSource,
        @UserModule.RemoteUserDataSource userRemoteDataSource: UserDataSource
    ): UserRepository {
        return DefaultUserRepository(userLocalDataSource, userRemoteDataSource)
    }
}