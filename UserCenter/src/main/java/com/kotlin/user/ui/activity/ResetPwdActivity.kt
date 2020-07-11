package com.kotlin.user.ui.activity

import android.os.Bundle
import com.kotlin.base.ext.enable
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.user.R
import com.kotlin.user.injection.component.DaggerUserComponent
import com.kotlin.user.injection.module.UserModule
import com.kotlin.user.presenter.ResetPwdPresenter
import com.kotlin.user.presenter.view.ResetPwdView
import kotlinx.android.synthetic.main.activity_reset_pwd.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.toast

/**
 * 重置密码界面
 */
class ResetPwdActivity :
    BaseMvpActivity<ResetPwdPresenter>(),
    ResetPwdView {
    private var pressTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pwd)
        initView();

    }

    /**
     * 初始化视图
     */
    private fun initView() {

        mConfirmBtn.enable(mPwdEt) { isBtnEnable() };
        mConfirmBtn.enable(mPwdConfirmEt) { isBtnEnable() };
        mConfirmBtn.onClick {
            if (mPwdEt.text.toString() != mPwdConfirmEt.text.toString()) {
                toast("密码不一致")
                return@onClick;
            }
            mPresenter.resetPwd(
                intent.getStringExtra("mobile"),
                mPwdEt.text.toString())
        }
    }

    override fun onResetPwdResult(result: String) {
        toast(result);
        //返回登录页面
        startActivity(intentFor<LoginActivity>().singleTop().clearTop());
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activityComponent).userModule(UserModule())
            .build()
            .inject(this);
        mPresenter.mView = this;
    }

    private fun isBtnEnable(): Boolean {
        return mPwdEt.text.isNullOrEmpty().not() and
                mPwdConfirmEt.text.isNullOrEmpty().not()
    }
}