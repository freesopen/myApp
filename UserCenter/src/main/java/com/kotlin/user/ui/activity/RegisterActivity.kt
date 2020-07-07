package com.kotlin.user.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import com.kotlin.base.common.AppManager
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.user.R
import com.kotlin.user.injection.component.DaggerUserComponent
import com.kotlin.user.injection.module.UserModule
import com.kotlin.user.presenter.RegisterPresenter
import com.kotlin.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast
class RegisterActivity : BaseMvpActivity<RegisterPresenter>(),RegisterView {
private var pressTime:Long=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mRegisterBtn.onClick{

            mPresenter.register(
                mMobileEt.text.toString(),
                mVerifyCodeEt.text.toString(), mPwdEt.text.toString()
            );
        }

    }

    override fun onReisterResult(result: String) {
            toast(result);
    }
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activityComponent).userModule(UserModule()).build()
            .inject(this);
        mPresenter.mView=this;
    }

    override fun onBackPressed() {

        var time = System.currentTimeMillis();
        if(  time-pressTime>2000){
           toast("再按一次推出程序")
            pressTime=time
        }else{
            AppManager.instance.exitApp(this);
        }
    }
}