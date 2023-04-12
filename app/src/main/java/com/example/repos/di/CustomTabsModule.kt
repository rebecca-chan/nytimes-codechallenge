package com.example.repos.di

import androidx.browser.customtabs.CustomTabsIntent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object CustomTabsModule {

    @Provides
    fun provideCustomTabsIntentBuilder(): CustomTabsIntent.Builder {
        return CustomTabsIntent.Builder()
    }

    @Provides
    fun provideCustomTabsIntent(
        builder: CustomTabsIntent.Builder
    ): CustomTabsIntent {
        return builder.build()
    }
}