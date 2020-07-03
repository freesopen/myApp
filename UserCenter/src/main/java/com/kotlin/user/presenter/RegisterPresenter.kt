package com.kotlin.user.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.user.presenter.view.RegisterView
import com.kotlin.user.service.impl.UserServiceImpl
import rx.Scheduler
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class  RegisterPresenter : BasePresenter<RegisterView>() {

    fun register(mobile:String,verifyCode:String,
                 pwd:String){
        /**
         * 业务逻辑
         */
        val userService=UserServiceImpl();
     var s=   userService.register(mobile,pwd,verifyCode )
            .execute(object :BaseSubscriber<Boolean>(){
                override fun onNext(t: Boolean?) {
                    if (t != null) {
                        mView.onReisterResult(t)
                    }
                }

                override fun onError(e: Throwable?) {
                    if (e != null) {
                        println(e.message)

                    };
                }

                override fun onCompleted() {
                    println("")
                }
            })

    }


}