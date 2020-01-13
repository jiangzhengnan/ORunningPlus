package com.oplayer.orunningplus.function.profile

import android.content.Intent
import android.graphics.Color
import android.os.Environment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.style.PictureCropParameterStyle
import com.luck.picture.lib.style.PictureParameterStyle
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.bean.SettingItem
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.main.settings.SettingsAdapter
import com.oplayer.orunningplus.function.profile.mvp.ProfileContract
import com.oplayer.orunningplus.function.profile.mvp.ProfilePresenter
import com.oplayer.orunningplus.utils.GlideEngine
import kotlinx.android.synthetic.main.activity_my_profile.*
import top.limuyang2.photolibrary.activity.LPhotoPickerActivity
import top.limuyang2.photolibrary.engine.LGlideEngine
import top.limuyang2.photolibrary.util.LPPImageType

import java.io.File

class MyProfileActivity : BaseActivity(), ProfileContract.View {


    var mPresenter: ProfileContract.Presenter = ProfilePresenter()
    var settingsList = mutableListOf<SettingItem>()
    lateinit var settingsAdapter: SettingsAdapter


    override fun getLayoutId(): Int {

        return R.layout.activity_my_profile
    }

    override fun initData() {
        mPresenter.getSettingItem(this)
    }

    override fun initView() {
        initToolbar(UIUtils.getString(R.string.main_profile), true)

        initRecycleView()

        initImageView()
    }

    val CHOOSE_PHOTO_REQUEST =1
    private fun initImageView() {
        profile_image.setOnClickListener {


//            showPicSeleDialog()


        }
    }


    private fun initRecycleView() {

        settingsAdapter = SettingsAdapter(R.layout.item_settings, settingsList)
        settingsAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        rv_myProfile.layoutManager = LinearLayoutManager(this)
        rv_myProfile.adapter = settingsAdapter
//        settingsAdapter.setOnItemChildClickListener { adapter, view, position ->
//
//            var switchButton = view.findViewById<SwitchButton>(R.id.sb_item)
//            Slog.d(" switchButton.isChecked  ${switchButton.isChecked}")
//            return@setOnItemChildClickListener
//        }


//        settingsAdapter.setOnItemClickListener { adapter, view, position ->
//            var settingItem = adapter.data[position] as SettingItem
//            when (settingItem.function) {
//                null -> {
//                    Slog.d("该项不执行方法  ")
//                }
//                is String -> {
//
//
//                    Slog.d("点击字符方法  $settingItem.function")
//                    var stringFun = settingItem.function as String
//
//                    when (stringFun) {
//                        DeviceSetting.FIND_DEVICE -> {
//                            EventBus.getDefault()
//                                .post(MessageEvent(DeviceSetting, DeviceSetting.FIND_DEVICE))
//                        }
//                        else -> {
//                        }
//                    }
//                }
//                else -> {
//                    var function = settingItem.function as () -> Unit
//                    Slog.d("点击可执行方法   $function")
////                    function.invoke()
//                }
//            }


//        }

    }


    override fun initInfo() {
        mPresenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        mPresenter.detachView()
    }

    override fun onClick(v: View) {

    }

    override fun onGetEvent(event: MessageEvent) {

    }


    override fun showSettingItem(list: List<SettingItem>) {
        settingsList = list as MutableList<SettingItem>
        settingsAdapter.setNewData(settingsList)
        rv_myProfile.adapter = settingsAdapter
    }



    fun showPicSeleDialog() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
            .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
            .isWeChatStyle(false)// 是否开启微信图片选择风格
            .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
            .maxSelectNum(1)// 最大图片选择数量
            .selectionMode( PictureConfig.SINGLE)// 多选 or 单选
            .isCamera(true)// 是否显示拍照按钮
//            .setLanguage()
            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
            .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
            .compress(true)// 是否压缩
            .synOrAsy(true)//同步true或异步false 压缩 默认同步
            .compressSavePath(getCompressPath())//压缩图片自定义保存地址
            .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
            .circleDimmedLayer(true)// 是否圆形裁剪
            .forResult(PictureConfig.CHOOSE_REQUEST)//结果回调onActivityResult code

    }

    // 压缩后图片文件存储位置
    fun getCompressPath():String {
        val path = Environment.getDataDirectory().absolutePath + "/PictureSelector/image/"
        val file =  File(path)
        if (file.mkdirs()) {
            return path
        }
        return path
    }

    private var mCropParameterStyle: PictureCropParameterStyle? = null
    private var mPictureParameterStyle: PictureParameterStyle? = null
//    private fun getWhiteStyle() { // 相册主题
//        mPictureParameterStyle = PictureParameterStyle()
//        // 是否改变状态栏字体颜色(黑白切换)
//        mPictureParameterStyle!!.isChangeStatusBarFontColor = true
//        // 是否开启右下角已完成(0/9)风格
//        mPictureParameterStyle.isOpenCompletedNumStyle = false
//        // 是否开启类似QQ相册带数字选择风格
//        mPictureParameterStyle.isOpenCheckNumStyle = false
//        // 相册状态栏背景色
//        mPictureParameterStyle.pictureStatusBarColor = Color.parseColor("#FFFFFF")
//        // 相册列表标题栏背景色
//        mPictureParameterStyle.pictureTitleBarBackgroundColor =
//            Color.parseColor("#FFFFFF")
//        // 相册列表标题栏右侧上拉箭头
//        mPictureParameterStyle.pictureTitleUpResId = R.drawable.ic_orange_arrow_up
//        // 相册列表标题栏右侧下拉箭头
//        mPictureParameterStyle.pictureTitleDownResId = R.drawable.ic_orange_arrow_down
//        // 相册文件夹列表选中圆点
//        mPictureParameterStyle.pictureFolderCheckedDotStyle = R.drawable.picture_orange_oval
//        // 相册返回箭头
//        mPictureParameterStyle.pictureLeftBackIcon = R.drawable.ic_back_arrow
//        // 标题栏字体颜色
//        mPictureParameterStyle.pictureTitleTextColor =
//            ContextCompat.getColor(this, R.color.app_color_black)
//        // 相册右侧取消按钮字体颜色  废弃 改用.pictureRightDefaultTextColor和.pictureRightDefaultTextColor
//        mPictureParameterStyle.pictureCancelTextColor =
//            ContextCompat.getColor(this, R.color.app_color_black)
//        // 相册列表勾选图片样式
//        mPictureParameterStyle.pictureCheckedStyle = R.drawable.picture_checkbox_selector
//        // 相册列表底部背景色
//        mPictureParameterStyle.pictureBottomBgColor =
//            ContextCompat.getColor(this, R.color.picture_color_fa)
//        // 已选数量圆点背景样式
//        mPictureParameterStyle.pictureCheckNumBgStyle = R.drawable.picture_num_oval
//        // 相册列表底下预览文字色值(预览按钮可点击时的色值)
//        mPictureParameterStyle.picturePreviewTextColor =
//            ContextCompat.getColor(this, R.color.picture_color_fa632d)
//        // 相册列表底下不可预览文字色值(预览按钮不可点击时的色值)
//        mPictureParameterStyle.pictureUnPreviewTextColor =
//            ContextCompat.getColor(this, R.color.picture_color_9b)
//        // 相册列表已完成色值(已完成 可点击色值)
//        mPictureParameterStyle.pictureCompleteTextColor =
//            ContextCompat.getColor(this, R.color.picture_color_fa632d)
//        // 相册列表未完成色值(请选择 不可点击色值)
//        mPictureParameterStyle.pictureUnCompleteTextColor =
//            ContextCompat.getColor(this, R.color.picture_color_9b)
//        // 预览界面底部背景色
//        mPictureParameterStyle.picturePreviewBottomBgColor =
//            ContextCompat.getColor(this, R.color.picture_color_white)
//        // 原图按钮勾选样式  需设置.isOriginalImageControl(true); 才有效
//        mPictureParameterStyle.pictureOriginalControlStyle = R.drawable.picture_original_checkbox
//        // 原图文字颜色 需设置.isOriginalImageControl(true); 才有效
//        mPictureParameterStyle.pictureOriginalFontColor =
//            ContextCompat.getColor(this, R.color.gray_date_text_color)
//        // 外部预览界面删除按钮样式
//        mPictureParameterStyle.pictureExternalPreviewDeleteStyle =
//            R.drawable.picture_icon_black_delete
//        // 外部预览界面是否显示删除按钮
//        mPictureParameterStyle.pictureExternalPreviewGonePreviewDelete = true
//        //        // 自定义相册右侧文本内容设置
////        mPictureParameterStyle.pictureRightDefaultText = "";
////        // 自定义相册未完成文本内容
////        mPictureParameterStyle.pictureUnCompleteText = "";
////        // 自定义相册完成文本内容
////        mPictureParameterStyle.pictureCompleteText = "";
////        // 自定义相册列表不可预览文字
////        mPictureParameterStyle.pictureUnPreviewText = "";
////        // 自定义相册列表预览文字
////        mPictureParameterStyle.picturePreviewText = "";
////        // 自定义相册标题字体大小
////        mPictureParameterStyle.pictureTitleTextSize = 18;
////        // 自定义相册右侧文字大小
////        mPictureParameterStyle.pictureRightTextSize = 14;
////        // 自定义相册预览文字大小
////        mPictureParameterStyle.picturePreviewTextSize = 14;
////        // 自定义相册完成文字大小
////        mPictureParameterStyle.pictureCompleteTextSize = 14;
////        // 自定义原图文字大小
////        mPictureParameterStyle.pictureOriginalTextSize = 14;
//// 裁剪主题
//        mCropParameterStyle = PictureCropParameterStyle(
//            ContextCompat.getColor(this, R.color.colorPrimary),
//            ContextCompat.getColor(this, R.color.colorPrimary),
//            ContextCompat.getColor(this, R.color.colorPrimary),
//            mPictureParameterStyle.isChangeStatusBarFontColor
//        )
//    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) { // 如果返回码是可以用的

            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {


                    if (PictureSelector.obtainMultipleResult(data)[0] != null) {
                        // 图片选择结果回调
                        val localMedia = PictureSelector.obtainMultipleResult(data)[0]
                        if (localMedia.isCompressed()) {
                            val albumPath = localMedia.compressPath
                            profile_image.setBackgroundResource(0)
                            //设置图片圆角角度
                            val roundedCorners = RoundedCorners(30)
                            //通过RequestOptions扩展功能
                            val options =
                                RequestOptions.bitmapTransform(roundedCorners).override(300, 300)
                                    //圆形
                                    .circleCrop()
                            Glide.with(this)
                                .load(albumPath).apply(options).into(profile_image)

                           Slog.d(" 图片回调位置  $albumPath ")

                        }
                    }

                }
                CHOOSE_PHOTO_REQUEST -> {
                    val selectedPhotos = LPhotoPickerActivity.getSelectedPhotos(data)
                    Glide.with(this)
                        .load(selectedPhotos[0]).into(profile_image)

                }
                else -> {
                }
            }
        }


    }


}
