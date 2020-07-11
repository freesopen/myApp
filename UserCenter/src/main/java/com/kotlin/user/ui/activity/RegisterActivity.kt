package com.kotlin.user.ui.activity

import android.os.Bundle
import android.view.View
import com.kotlin.base.common.AppManager
import com.kotlin.base.ext.enable
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.widgets.VerifyButton
import com.kotlin.user.R
import com.kotlin.user.injection.component.DaggerUserComponent
import com.kotlin.user.injection.module.UserModule
import com.kotlin.user.presenter.RegisterPresenter
import com.kotlin.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

/**
 * 注册界面
 */
class RegisterActivity :
    BaseMvpActivity<RegisterPresenter>(),
    RegisterView, View.OnClickListener {
    private var pressTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView();

    }

    /**
     * 初始化视图
     */
    private fun initView() {
        mRegisterBtn.enable(mMobileEt) {isBtnEnable()};
        mRegisterBtn.enable(mVerifyCodeEt,{isBtnEnable()});
        mRegisterBtn.enable(mPwdEt) {isBtnEnable()};
        mRegisterBtn.enable(mPwdConfirmEt) {isBtnEnable()};
        mRegisterBtn.onClick(this);
        mVerifyCodeBtn.onClick(this);
        mVerifyCodeBtn.setOnVerifyBtnClick(object : VerifyButton.OnVerifyBtnClick {
            override fun onClick() {
                toast("获取验证码")
            }
        })
    }

    /**
     * 注册回调
     */
    override fun onReisterResult(result: String) {
        toast(result);
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activityComponent).userModule(UserModule())
            .build()
            .inject(this);
        mPresenter.mView = this;
    }

    override fun onBackPressed() {

        var time = System.currentTimeMillis();
        if (time - pressTime > 2000) {
            toast("再按一次推出程序")
            pressTime = time
        } else {
            AppManager.instance.exitApp(this);
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.mVerifyCodeBtn -> {
                mVerifyCodeBtn.requestSendVerifyNumber();
                toast("发送验证码成功")
            }
            R.id.mRegisterBtn -> {
                mPresenter.register(
                    mMobileEt.text.toString(),
                    mVerifyCodeEt.text.toString(), mPwdEt.text.toString()
                );
            }
        }
    }

    private fun isBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not() and
                mVerifyCodeEt.text.isNullOrEmpty().not() and
                mPwdEt.text.isNullOrEmpty().not() and
                mPwdConfirmEt.text.isNullOrEmpty().not()
    }
}