package com.kotlin.base.ui.activity

import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.presenter.view.BaseView

open class BaseMvpActivity<T:BasePresenter<*>>:BaseActivity(),BaseView {
    override fun showLoading() {
        TODO("Not yet implemented")

    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun onError() {
        TODO("Not yet implemented")
    }
    lateinit var mPresenter:T
}
