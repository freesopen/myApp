package com.kotlin.user.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.TResult
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.DateUtils
import com.kotlin.user.R
import com.kotlin.user.injection.component.DaggerUserComponent
import com.kotlin.user.injection.module.UserModule
import com.kotlin.user.presenter.UserInfoPresenter
import com.kotlin.user.presenter.view.UserInfoView
import kotlinx.android.synthetic.main.activity_user_info.*
import java.io.File

/**
 * 注册界面
 */
class UserInfoActivity :
    BaseMvpActivity<UserInfoPresenter>(),
    UserInfoView, TakePhoto.TakeResultListener {
    private lateinit var mTakePhoto: TakePhoto;
    private lateinit var mTempFile: File;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        mTakePhoto = TakePhotoImpl(this, this)
        initView();
        mTakePhoto.onCreate(savedInstanceState)

    }

    /**
     * 初始化视图
     */
    private fun initView() {
        mUserIconIv.onClick {
            showAlertView()
        }
    }

    private fun showAlertView() {
        AlertView("选择图片", "", "取消",
            null, arrayOf("拍照", "相册"), this,
            AlertView.Style.ActionSheet, object : OnItemClickListener {
                override fun onItemClick(o: Any?, position: Int) {
                    mTakePhoto.onEnableCompress(
                        CompressConfig.ofDefaultConfig(),
                        false)
                    when (position) {
                        0 -> {
                            createTempFile();
                            mTakePhoto.onPickFromCapture(Uri.fromFile
                                (mTempFile));
                        }
                        1 -> mTakePhoto.onPickFromGallery()
                    }
                }
            }
        ).show();
    }


    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activityComponent)
            .userModule(UserModule())
            .build()
            .inject(this);
        mPresenter.mView = this;
    }

    override fun takeSuccess(result: TResult?) {
        Log.d("TakePhoto", result?.image?.originalPath)
        Log.d("TakePhoto", result?.image?.compressPath)
    }

    override fun takeCancel() {

    }

    override fun takeFail(result: TResult?, msg: String?) {
        Log.e("TakePhoto",msg)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhoto.onActivityResult(requestCode, resultCode, data)
    }

    fun createTempFile() {
        val tempFileName = "${DateUtils.curTime}.png";
        //系统挂在目录
        if (Environment.MEDIA_MOUNTED .equals( Environment.getExternalStorageState())) {
            this.mTempFile = File(Environment.getExternalStorageDirectory
                (), tempFileName)
            return
        }
        this.mTempFile = File(filesDir, tempFileName)
    }
}