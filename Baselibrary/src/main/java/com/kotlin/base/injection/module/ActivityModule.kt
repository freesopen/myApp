package com.kotlin.base.injection.module

import android.app.Activity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity:Activity) {

@Provides
fun providerActivity():Activity{
    return activity;
}
}