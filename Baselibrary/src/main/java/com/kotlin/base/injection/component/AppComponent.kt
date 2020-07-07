package com.kotlin.base.injection.component

import android.content.Context
import com.kotlin.base.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton
// @Singleton 标记
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun context():Context;
}