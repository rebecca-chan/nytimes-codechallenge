package com.example.repos.di

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
object CustomTabsModule {

        @Provides
        fun provideCustomTabsIntentBuilder(): CustomTabsIntent.Builder {
            return CustomTabsIntent.Builder()
        }

        @Provides
        fun provideCustomTabsIntent(
            builder: CustomTabsIntent.Builder,
            @ApplicationContext context: Context
        ): CustomTabsIntent {
            return builder.build()
        }
}