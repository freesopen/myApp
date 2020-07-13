package com.kotlin.user.presenter.view

import com.kotlin.base.presenter.view.BaseView
import com.kotlin.user.data.protocol.UserInfo

interface  UserInfoView: BaseView {
   fun onGetUploadResult(result:String);
   fun onEditUserResult(userinfo:UserInfo)
}