package com.cqteam.user.ui.activity

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.cqteam.baselibrary.common.BaseConstant
import com.cqteam.baselibrary.glide.GlideEngine
import com.cqteam.baselibrary.ui.activity.BaseActivity
import com.cqteam.baselibrary.utils.AppPrefsUtils
import com.cqteam.baselibrary.utils.GlideUtils
import com.cqteam.baselibrary.utils.QiNiuUtils
import com.cqteam.baselibrary.widgets.ToastUtils
import com.cqteam.provider.common.ProviderConstant
import com.cqteam.user.R
import com.cqteam.user.databinding.ActivityUserInfoBinding
import com.cqteam.user.vm.UserInfoViewModel
import com.kotlin.base.utils.DateUtils
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.qiniu.android.storage.UploadManager
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import kotlinx.coroutines.launch
import permissions.dispatcher.*
import java.io.File
import java.util.*


/**
 *  用户信息页面
 */
@RuntimePermissions
@AndroidEntryPoint
class UserInfoActivity : BaseActivity<UserInfoViewModel>(), View.OnClickListener {

    override val viewModel: UserInfoViewModel by viewModels()

    private val logTag = "takePhoto"
    private val uploadManager: UploadManager by lazy { UploadManager() }
    private var mLocalHeaderImageUrl: String = "" // 本地路径，原始图片路径
    private var mLocalHeaderImageUrlCompress: String = "" // 本地路径，压缩图片路径
    private var mRemoteHeaderImageUrl: String = "" // 远程服务器路径
    private var mUserIcon: String? ="" // 用户头像
    private var mUserName: String? = "" // 用户名
    private var mUserGender: String? = "" // 用户性别
    private var mUserSign: String? = "" // 用户签名
    private var mUserMobile: String? = "" // 用户手机

    private lateinit var binding: ActivityUserInfoBinding
    private lateinit var mTempFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initData()
    }

    private fun initData() {

        mUserIcon = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON)
        mUserName = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
        mUserGender = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_GENDER)
        mUserSign = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)
        mUserMobile = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_MOBILE)

        if (!mUserIcon.isNullOrEmpty()) {
            mRemoteHeaderImageUrl = mUserIcon!!
            GlideUtils.loadUrlImage(this,mUserIcon!!,binding.mUserIconIv)
        }
        binding.mUserNameEt.setText(mUserName)
        binding.mUserSignEt.setText(mUserSign)
        binding.mUserMobileTv.text = mUserMobile
        if (mUserGender == "0") binding.mGenderMaleRb.isChecked = true
        else  binding.mGenderFemaleRb.isChecked = true

        // 获取凭证之后的提交
        viewModel.uploadTokenResult.observe(this, {
            uploadImage(it)
        })

        // 提交用户信息
        viewModel.editUserInfoResult.observe(this, {
            ToastUtils.show("保存成功")
        })
    }

    private fun initView() {
        // 头像
        binding.mUserIconIv.setOnClickListener {
            AlertView("选择图片", null, "取消", null,
                arrayOf("拍照", "相册"), this, AlertView.Style.ActionSheet,
                OnItemClickListener { _, position ->
                    // 开启压缩
                    when (position) {
                        0 -> openCameraWithPermissionCheck()
                        1 -> openGalleryWithPermissionCheck()
                    }
                }).show()
        }

        // 保存按钮
        binding.mHeaderBar.mRightTv.setOnClickListener {
            viewModel.editUser(
                mRemoteHeaderImageUrl,
                binding.mUserNameEt.text.toString(),
                if (binding.mGenderMaleRb.isChecked) "0" else "1",
                binding.mUserSignEt.text.toString())
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mConfirmBtn -> {
            }
        }
    }

    /**
     *  打开摄像头
     */
    @NeedsPermission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun openCamera() {
        Log.d(logTag, "打开相机")
        PictureSelector.create(this)
            .openCamera(PictureMimeType.ofImage())
            .imageEngine(GlideEngine.createGlideEngine())
            .forResult(PictureConfig.REQUEST_CAMERA)
    }

    /**
     *  打开图库
     */
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun openGallery() {
        Log.d(logTag, "打开图库")
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .imageEngine(GlideEngine.createGlideEngine())
            .selectionMode(PictureConfig.SINGLE) // 单选
            .forResult(PictureConfig.CHOOSE_REQUEST)
    }

    // 相机权限
    @OnShowRationale(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun showRationaleForCamera(request: PermissionRequest) {
        showRationaleDialog("需要打开相机和存储权限", request)
    }

    @OnPermissionDenied(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onCameraDenied() {
        Toast.makeText(this, "相机和存储被禁用了", Toast.LENGTH_SHORT).show()
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onCameraNeverAskAgain() {
        Toast.makeText(this, "不再继续询问", Toast.LENGTH_SHORT).show()
    }

    // 存储权限
    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun showRationaleForStorage(request: PermissionRequest) {
        showRationaleDialog("需要打开储存权限", request)
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onStorageDenied() {
        Toast.makeText(this, "储存权限被禁用了", Toast.LENGTH_SHORT).show()
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onStorageNeverAskAgain() {
        Toast.makeText(this, "不再继续询问", Toast.LENGTH_SHORT).show()
    }

    private fun showRationaleDialog(message: String, request: PermissionRequest) {
        AlertDialog.Builder(this)
            .setPositiveButton("允许") { _, _ -> request.proceed() }
            .setNegativeButton("取消") { _, _ -> request.cancel() }
            .setCancelable(false)
            .setMessage(message)
            .show()
    }

    /**
     *  创建文件
     */
    private fun createTempFile() {
        // 文件名，时间戳命名
        val tempFileName = "${DateUtils.curTime}.png"
        // 检查SD卡是否装载，若装载有，就存储到SD卡中；如果没有就放在手机存储中
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()){
            Log.d("takePhoto", "SD卡装载成功，图片写入SD卡中")
            mTempFile = File(Environment.getStorageDirectory(), tempFileName)
            return
        }
        mTempFile = File(filesDir, tempFileName)
        Log.d(logTag, "SD卡未装载，图片写入手机存储器中")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when(requestCode) {
                PictureConfig.REQUEST_CAMERA -> { // 单独拍照
                    val result: List<LocalMedia> = PictureSelector.obtainMultipleResult(data)
                    Log.d(logTag, "单独拍照回调")
                    if (result.isNullOrEmpty()) {
                        ToastUtils.show("请重新拍照")
                        return
                    }
                    operationImageUrl(result)
                }

                PictureConfig.CHOOSE_REQUEST -> { // 选择图片
                    val result: List<LocalMedia> = PictureSelector.obtainMultipleResult(data)
                    Log.d(logTag, "选择图片回调")
                    if (result.isNullOrEmpty()) {
                        ToastUtils.show("请重新选择图片")
                        return
                    }
                    operationImageUrl(result)
                }
            }
        }
    }

    /**
     *  处理图片路径
     */
    private fun operationImageUrl(result: List<LocalMedia>) {
        val realPath: String?
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            realPath = result[0].realPath
            Log.d(logTag, "小于 Android Q realPath:$realPath")
        } else {
            realPath = result[0].androidQToPath
            Log.d(logTag, "大于等于 Android Q realPath:$realPath")
        }
        if (!realPath.isNullOrEmpty()) {
            mLocalHeaderImageUrl = realPath
            compressImage() // 启动压缩操作
        } else {
            ToastUtils.show("暂时无法修改头像")
        }
    }

    /**
     *  压缩图片
     */
    private fun compressImage() {
        viewModel.showLoading.value = "loading"
        lifecycleScope.launch {
            Log.d(logTag, "lifecycleScope.launch==${Thread.currentThread().name}")
            val compressedImageFile = Compressor.compress(this@UserInfoActivity, File(mLocalHeaderImageUrl)) {
                Log.d(logTag, "Compressor.compress==${Thread.currentThread().name}")
                // 使用 Bitmap.CompressFormat.WEBP_LOSSY 和  Bitmap.CompressFormat.WEBP_LOSSLESS，应用会崩溃
                default(width = 120, format = Bitmap.CompressFormat.WEBP)
            }
            if (compressedImageFile.absolutePath.isNullOrEmpty()) {
                viewModel.hideLoading.value = "loading"
                Log.e(logTag,"图片压缩失败")
                ToastUtils.show("暂时无法修改头像")
            } else {
                mLocalHeaderImageUrlCompress = compressedImageFile.absolutePath
                Log.d(logTag,"压缩后图片的路径：$mLocalHeaderImageUrlCompress")
                viewModel.getUploadToken()
            }
        }
    }


    /**
     *  上传图片
     */
    private fun uploadImage(uploadToken: String) {
        viewModel.showLoading.value = "loading"
        uploadManager.put(mLocalHeaderImageUrlCompress,null,uploadToken,
            { key, info, response ->
                viewModel.hideLoading.value = "hide"
                if (info.isOK) {
                    ToastUtils.show("上传成功")
                    Log.d(logTag, "上传成功")
                    Log.d(logTag, response.toString())
                    mRemoteHeaderImageUrl = BaseConstant.QINIU_ADDRESS + response?.get("hash")
//                    mRemoteHeaderImageUrl = QiNiuUtils.instance.downloadUrl(BaseConstant.QINIU_ADDRESS + response?.get("hash"))
                    setHeaderImage(mRemoteHeaderImageUrl)
                } else {
                    ToastUtils.show("上传失败")
                    Log.e(logTag, info.error)
                }
            },null)
    }

    /**
     *  设置头像图片
     */
    private fun setHeaderImage(path: String) {
        if (TextUtils.isEmpty(path)) {
            return
        }
        GlideUtils.loadUrlImage(this,path,binding.mUserIconIv)
    }
}