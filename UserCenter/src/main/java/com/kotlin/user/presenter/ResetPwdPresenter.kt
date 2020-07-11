package com.kotlin.user.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.user.presenter.view.ResetPwdView
import com.kotlin.user.service.UserService
import javax.inject.Inject

/**
 * 忘记密码
 */
class ResetPwdPresenter @Inject constructor()
    : BasePresenter<ResetPwdView>() {
    @Inject
//    @field:[Named ("service")]
    lateinit var userService: UserService


    fun resetPwd(
        mobile: String, pwd: String) {
        if(!checkNetWork()){
            return
        }
        mView.showLoading();
       userService.resetPwd(mobile, pwd)
            .execute(object : BaseSubscriber<Boolean>(mView) {
                override fun onNext(t: Boolean) {
                    if (t) {
                        mView.onResetPwdResult("重置密码成功");
                    }
                }
            },lifecycleProvider)

    }

}