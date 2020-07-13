package com.kotlin.user.injection.component

import com.kotlin.base.injection.PreComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.user.injection.module.UploadModule
import com.kotlin.user.injection.module.UserModule
import com.kotlin.user.ui.activity.*
import dagger.Component
@PreComponentScope
@Component(modules = [UserModule::class,UploadModule::class],
 dependencies = [ActivityComponent::class])
interface UserComponent {

 fun inject(activity: RegisterActivity)
 fun inject(activity: LoginActivity)
 fun inject(activity: ForgetPwdActivity)
 fun inject(activity: ResetPwdActivity)
 fun inject(activity: UserInfoActivity)

}