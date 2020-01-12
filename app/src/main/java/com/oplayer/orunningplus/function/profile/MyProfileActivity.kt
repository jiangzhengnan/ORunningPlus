package com.oplayer.orunningplus.function.profile

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.oplayer.common.common.DeviceSetting
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.bean.SettingItem
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.main.settings.SettingsAdapter
import com.oplayer.orunningplus.function.profile.mvp.ProfileContract
import com.oplayer.orunningplus.function.profile.mvp.ProfilePresenter
import kotlinx.android.synthetic.main.activity_my_profile.*

import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.luck.picture.lib.config.PictureMimeType
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

    private fun initImageView() {
        profile_image.setOnClickListener {


            showPicSeleDialog()

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
            .maxSelectNum(1)// 最大图片选择数量
            .selectionMode( PictureConfig.SINGLE)// 多选 or 单选
            .isCamera(true)// 是否显示拍照按钮
            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
            .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
            .compress(true)// 是否压缩
            .synOrAsy(true)//同步true或异步false 压缩 默认同步
            .compressSavePath(getCompressPath())//压缩图片自定义保存地址
            //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
            .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
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
//                            Glide.with(this)
//                                .load(albumPath).apply(options).into(profile_image)

                           Slog.d(" 图片回调位置  $albumPath ")

                        }
                    }

                }
                else -> {
                }
            }
        }


    }


}
