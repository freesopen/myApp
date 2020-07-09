package com.kotlin.base.ui.fragment

import android.os.Bundle
import android.os.PersistableBundle
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.component.DaggerActivityComponent
import com.kotlin.base.injection.module.ActivityModule
import com.kotlin.base.injection.module.LifecycleProviderModule
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.presenter.view.BaseView
import com.kotlin.base.ui.activity.BaseActivity
import org.jetbrains.anko.toast
import javax.inject.Inject

open abstract class BaseMvpFragment<T : BasePresenter<*>> : BaseFragment(), BaseView {
    override fun showLoading() {
        TODO("Not yet implemented")

    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun onError(text: String) {
        toast(text)
    }

    @Inject
    lateinit var mPresenter: T
    lateinit var activityComponent: ActivityComponent;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityInjection()
        injectComponent()
    }

    abstract fun injectComponent();
    private fun initActivityInjection() {
        activityComponent = DaggerActivityComponent.builder().appComponent(
            (activity.application as BaseApplication).appComponent
        )
            .activityModule(ActivityModule(activity))
            .lifecycleProviderModule(LifecycleProviderModule(this))
            .build();

    }
}
