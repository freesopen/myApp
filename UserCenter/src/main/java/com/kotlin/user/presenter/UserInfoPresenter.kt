package com.kotlin.user.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.user.data.protocol.UserInfo
import com.kotlin.user.presenter.view.UserInfoView
import com.kotlin.user.service.UploadService
import com.kotlin.user.service.UserService
import javax.inject.Inject

class UserInfoPresenter @Inject constructor() : BasePresenter<UserInfoView>() {
    @Inject
//    @field:[Named ("service")]
    lateinit var userService: UserService

    @Inject
//    @field:[Named ("service")]
    lateinit var uploadService: UploadService
    fun getUploadToken() {
        if (!checkNetWork()) {
            return;
        }
        mView.showLoading();
        uploadService.getUploadToken().execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onGetUploadResult(t);
            }
        }, lifecycleProvider)
    }
    fun editUser(userIcon:String,userName:String,
                 userGender:String,userSign:String) {
        if (!checkNetWork()) {
            return;
        }
        mView.showLoading();
        userService.editUser(userIcon,userName,
            userGender,userSign).execute(object : BaseSubscriber<UserInfo>(mView) {
            override fun onNext(t: UserInfo) {
                mView.onEditUserResult(t);
            }
        }, lifecycleProvider)
    }
}