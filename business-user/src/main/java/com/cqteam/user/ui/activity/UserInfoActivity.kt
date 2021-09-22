package com.cqteam.user.ui.activity

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.cqteam.baselibrary.ui.activity.BaseActivity
import com.cqteam.baselibrary.widgets.ToastUtils
import com.cqteam.user.R
import com.cqteam.user.databinding.ActivityUserInfoBinding
import com.cqteam.user.vm.UserInfoViewModel
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.TResult
import com.kotlin.base.utils.DateUtils
import dagger.hilt.android.AndroidEntryPoint
import permissions.dispatcher.*
import java.io.File

/**
 *  用户信息页面
 */
@RuntimePermissions
@AndroidEntryPoint
class UserInfoActivity : BaseActivity<UserInfoViewModel>(), View.OnClickListener,TakePhoto.TakeResultListener {

    override val viewModel: UserInfoViewModel by viewModels()

    private lateinit var binding: ActivityUserInfoBinding
    private lateinit var mTakePhoto: TakePhoto
    private lateinit var mTempFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mTakePhoto = TakePhotoImpl(this, this)
        mTakePhoto.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initData() {
    }

    private fun initView() {
        binding.mUserIconIv.setOnClickListener {
            AlertView("选择图片", null, "取消", null,
                arrayOf("拍照", "相册"), this, AlertView.Style.ActionSheet,
                OnItemClickListener { _, position ->
                    // 开启压缩
                    mTakePhoto.onEnableCompress(CompressConfig.ofDefaultConfig(), false)
                    when (position) {
                        0 -> openCameraWithPermissionCheck()
                        1 -> openGalleryWithPermissionCheck()
                    }
                }).show()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mConfirmBtn -> {
            }
        }
    }

    // 选择图片结果
    override fun takeSuccess(result: TResult?) {
        Log.d("takePhoto", result?.image?.originalPath ?: "没有找到原始文件地址")
        Log.d("takePhoto", result?.image?.compressPath ?: "没有找到压缩文件地址")
    }

    override fun takeFail(result: TResult?, msg: String?) {
        Log.e("takePhoto", msg ?: "出现错了")
    }

    override fun takeCancel() {
        Log.e("takePhoto", "取消了")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mTakePhoto.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     *  打开摄像头
     */
    @NeedsPermission(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun openCamera() {
        createTempFile()
        mTakePhoto.onPickFromCapture(Uri.fromFile(mTempFile))
        ToastUtils.show("打开相机")
    }

    /**
     *  打开图库
     */
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun openGallery() {
        mTakePhoto.onPickFromGallery()
        ToastUtils.show("打开图库")
    }

    // 相机权限
    @OnShowRationale(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun showRationaleForCamera(request: PermissionRequest) {
        showRationaleDialog("需要打开相机和存储权限", request)
    }

    @OnPermissionDenied(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onCameraDenied() {
        Toast.makeText(this, "相机和存储被禁用了", Toast.LENGTH_SHORT).show()
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
            Log.d("takePhoto","SD卡装载成功，图片写入SD卡中")
            mTempFile = File(Environment.getStorageDirectory(),tempFileName)
            return
        }
        mTempFile = File(filesDir,tempFileName)
        Log.d("takePhoto","SD卡未装载，图片写入手机存储器中")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
}