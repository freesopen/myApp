package com.kotlin.user.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.user.presenter.view.RegisterView
import com.kotlin.user.service.UserService
import javax.inject.Inject

class RegisterPresenter @Inject constructor() : BasePresenter<RegisterView>() {
    @Inject
//    @field:[Named ("service")]
    lateinit var userService: UserService


    fun register(
        mobile: String, verifyCode: String,
        pwd: String ) {
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){
            return
        }
        mView.showLoading();
       userService.register(mobile, pwd, verifyCode)
            .execute(object : BaseSubscriber<Boolean>(mView) {
                override fun onNext(t: Boolean) {
                    if (t) {
                        mView.onReisterResult("注册成功");
                    }
                }
            },lifecycleProvider)

    }

}