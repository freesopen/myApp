package com.kotlin.user.ui.activity

import android.os.Bundle
import android.view.View
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.user.R
import com.kotlin.user.presenter.RegisterPresenter
import com.kotlin.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast
class RegisterActivity : BaseMvpActivity<RegisterPresenter>(),RegisterView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mPresenter=RegisterPresenter();
        mPresenter.mView=this;
        mRegisterBtn.setOnClickListener(fun(_: View) {
            mPresenter.register(  mMobileEt.text.toString(), mVerifyCodeEt.text.toString(),mPwdEt.text.toString()
            );
        });
    }


    override fun onReisterResult(result: Boolean) {
        toast("注册成功");
    }
}