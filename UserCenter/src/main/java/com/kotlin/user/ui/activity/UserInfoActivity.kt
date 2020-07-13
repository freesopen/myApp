package com.kotlin.user.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.TResult
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.base.utils.DateUtils
import com.kotlin.base.utils.GlideUtils
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.user.R
import com.kotlin.user.data.protocol.UserInfo
import com.kotlin.user.injection.component.DaggerUserComponent
import com.kotlin.user.injection.module.UserModule
import com.kotlin.user.presenter.UserInfoPresenter
import com.kotlin.user.presenter.view.UserInfoView
import com.kotlin.user.utils.UserPrefsUtils
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UploadManager
import kotlinx.android.synthetic.main.activity_user_info.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.io.File

/**
 * 注册界面
 */
class UserInfoActivity :
    BaseMvpActivity<UserInfoPresenter>(),
    UserInfoView, TakePhoto.TakeResultListener {
    private lateinit var mTakePhoto: TakePhoto;

    private lateinit var mTempFile: File;

    private var mLocalFileUrl: String? = null;
    private var mRemoteFileUrl: String? = null;

    private var mUserIcon: String? = null;
    private var mUserName: String? = null;
    private var mUserMobile: String? = null;
    private var mUserGender: String? = null;
    private var mUserSign: String? = null;

    private val mUploadManager: UploadManager by lazy {
        UploadManager();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        mTakePhoto = TakePhotoImpl(this, this)
        initView();
        mTakePhoto.onCreate(savedInstanceState)
        checkPermission(Manifest.permission.CAMERA) { }
        initData();
    }


    /**
     * 初始化视图
     */
    private fun initView() {
        mUserIconIv.onClick {
            showAlertView()
        }
        mHeaderBar.getRightView().onClick {
            mPresenter.editUser(
                mRemoteFileUrl!!,
                mUserNameEt.text?.toString() ?: "",
                if (mGenderFemaleRb.isChecked) "0" else "1",
                mUserSignEt.text?.toString() ?: ""
            )
        }
    }

    private fun initData() {

        mUserIcon = AppPrefsUtils.getString(
            ProviderConstant.KEY_SP_USER_ICON
        )
        mUserName = AppPrefsUtils.getString(
            ProviderConstant.KEY_SP_USER_NAME
        )
        mUserMobile = AppPrefsUtils.getString(
            ProviderConstant.KEY_SP_USER_MOBILE
        )
        mUserGender = AppPrefsUtils.getString(
            ProviderConstant.KEY_SP_USER_GENDER
        )
        mUserSign = AppPrefsUtils.getString(
            ProviderConstant.KEY_SP_USER_SIGN
        )
        if (mUserIcon != "") {
            mRemoteFileUrl = mUserIcon;
            GlideUtils.loadUrlImage(this, mUserIcon!!, mUserIconIv)

        }
        mUserNameEt.setText(mUserName);
        if (mUserGender == "0") {
            mGenderFemaleRb.isChecked = true
        } else {
            mGenderMaleRb.isChecked = true
        }
        mUserSignEt.setText(mUserSign)
        mUserMobileTv.text = mUserMobile;
    }


    private fun showAlertView() {
        AlertView("选择图片", "", "取消",
            null, arrayOf("拍照", "相册"), this,
            AlertView.Style.ActionSheet, OnItemClickListener { o, position ->
                mTakePhoto.onEnableCompress(
                    CompressConfig.ofDefaultConfig(),
                    false
                )
                when (position) {
                    0 -> {
                        createTempFile();

                        mTakePhoto.onPickFromCapture(Uri.fromFile(mTempFile))
                    }
                    1 -> mTakePhoto.onPickFromGallery()
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
        mLocalFileUrl = result?.image?.compressPath;
        mPresenter.getUploadToken();

    }

    override fun takeCancel() {

    }

    override fun takeFail(result: TResult?, msg: String?) {
        Log.e("TakePhoto", msg)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhoto.onActivityResult(requestCode, resultCode, data)
    }

    private fun createTempFile() {
        val tempFileName = "${DateUtils.curTime}.png";
        //系统挂在目录
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            this.mTempFile = File(
                Environment.getExternalStorageDirectory
                    (), tempFileName
            )
            return
        }
        this.mTempFile = File(filesDir, tempFileName)
    }

    /**
     *        permission
     *        Manifest.permission.CAMERA
     * 授权权限
     */

    private fun checkPermission(permission: String, method: () -> Unit) {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 进入这儿表示没有权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                // 提示已经禁止
                toast("请授权,否则闪退")
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(permission), 100);
            }
        }
        method();
    }

    override fun onGetUploadResult(result: String) {
        mUploadManager.put(
            mLocalFileUrl, null, result,
            object : UpCompletionHandler {
                override fun complete(
                    key: String?,
                    info: ResponseInfo?,
                    response: JSONObject?
                ) {
                    mRemoteFileUrl = BaseConstant.IMAGE_SERVER_ADDRESS +
                            response?.get("hash")
                    Log.d("test", mRemoteFileUrl.toString())
                    GlideUtils.loadUrlImage(
                        this@UserInfoActivity
                        , mRemoteFileUrl!!, mUserIconIv
                    );
                }
            }, null
        );
    }

    override fun onEditUserResult(userinfo: UserInfo) {
        toast("修改成功")
        UserPrefsUtils.putUserInfo(userinfo)
    }

}