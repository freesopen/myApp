package com.kotlin.user.presenter

import com.kotlin.base.presenter.BasePresenter
import com.kotlin.user.presenter.view.UserInfoView
import com.kotlin.user.service.UserService
import javax.inject.Inject

class UserInfoPresenter @Inject constructor()
    : BasePresenter<UserInfoView>() {
    @Inject
//    @field:[Named ("service")]
    lateinit var userService: UserService



}