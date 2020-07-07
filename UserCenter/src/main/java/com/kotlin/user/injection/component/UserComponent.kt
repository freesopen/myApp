package com.kotlin.user.injection.component

import com.kotlin.base.injection.ActivityScope
import com.kotlin.base.injection.PreComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.user.injection.module.UserModule
import com.kotlin.user.ui.activity.RegisterActivity
import dagger.Component
@PreComponentScope
@Component(modules = [UserModule::class],dependencies = [ActivityComponent::class])
interface UserComponent {

 fun inject(activity: RegisterActivity)

}