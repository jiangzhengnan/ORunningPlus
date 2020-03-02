package com.oplayer.orunningplus.function.main.profile.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bumptech.glide.Glide
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.bean.UserInfo
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.service.BleService
import com.vicpin.krealmextensions.createOrUpdate
import kotlinx.android.synthetic.main.activity_user_info.*
import java.util.*


class UserInfoActivity : BaseActivity() {

    val REQUEST_CODE_CHOOSE = 1

    var userInfo: UserInfo = BleService.INSTANCE.getCurrUser()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_user_info
    }

    override fun initData() {
    }

    override fun initView() {
        initToolbar(UIUtils.getString(R.string.main_profile), true)
        initImageView()

    }

    private fun initImageView() {
        profile_image.setOnClickListener {

            selectPhoto()
            //            showPicSeleDialog()


        }
    }

    fun selectPhoto() {
        // 进入相册 以下是例子：用不到的api可以不写
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage()) //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
            .maxSelectNum(1) // 最大图片选择数量 int
            .minSelectNum(1) // 最小选择数量 int
            .imageSpanCount(4) // 每行显示个数 int
            .selectionMode(PictureConfig.SINGLE) // 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
            .previewImage(true) // 是否可预览图片 true or false
            .previewVideo(false) // 是否可预览视频 true or false
            .enablePreviewAudio(false) // 是否可播放音频 true or false
            .isCamera(true) // 是否显示拍照按钮 true or false
            .imageFormat(PictureMimeType.PNG) // 拍照保存图片格式后缀,默认jpeg
            .isZoomAnim(true) // 图片列表点击 缩放效果 默认true
            .sizeMultiplier(0.5f) // glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
            .setOutputCameraPath("/CustomPath") // 自定义拍照保存路径,可不填
            .enableCrop(true) // 是否裁剪 true or false
            .compress(true) // 是否压缩 true or false
            .glideOverride(200, 200) // int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
            .hideBottomControls(true) // 是否显示uCrop工具栏，默认不显示 true or false
            .isGif(false) // 是否显示gif图片 true or false
//                .compressSavePath(getPath())//压缩图片保存地址
            .freeStyleCropEnabled(true) // 裁剪框是否可拖拽 true or false
            .circleDimmedLayer(true) // 是否圆形裁剪 true or false
            .showCropFrame(false) // 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
            .showCropGrid(false) // 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
            .openClickSound(false) // 是否开启点击声音 true or false
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
            .previewEggs(true) // 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                .cropCompressQuality()// 裁剪压缩质量 默认90 int
            .minimumCompressSize(100) // 小于100kb的图片不压缩
            .synOrAsy(true) //同步true或异步false 压缩 默认同步
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
            .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
            .scaleEnabled(true) // 裁剪是否可放大缩小图片 true or false
//            .videoQuality(0) // 视频录制质量 0 or 1 int
//            .videoMaxSecond(15) // 显示多少秒以内的视频or音频也可适用 int
//            .videoMinSecond(10) // 显示多少秒以内的视频or音频也可适用 int
//            .recordVideoSecond(30) //视频秒数录制 默认60s int
            .isDragFrame(true) // 是否可拖动裁剪框(固定)
            .forResult(PictureConfig.CHOOSE_REQUEST) //结果回调onActivityResult code


    }

    override fun initInfo() {

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rl_name -> {
            }
            R.id.rl_gender -> {
                showGenderSelect()
            }
            R.id.rl_birthday -> {
                showDateSelect(Date())
            }
            R.id.rl_weight -> {
            }
            R.id.rl_height -> {
            }
            R.id.rl_bp -> {
            }

        }

    }

    private fun showGenderSelect() {
        val list: List<String> = UIUtils.getContext().resources.getStringArray(R.array.gender_arr).toList()
        // 不联动的多级选项
        var pvOptions = OptionsPickerBuilder(this,
            OnOptionsSelectListener { options1, options2, options3, v ->


            })
            .setOptionsSelectChangeListener { options1, options2, options3 ->

            }
            .setSubmitText("确定")//确定按钮文字
            .setCancelText("取消")//取消按钮文字
            .build<Any>()
        pvOptions.setPicker(list)
        pvOptions.show()


    }

    override fun onGetEvent(event: MessageEvent) {

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK) { // 如果返回码是可以用的

            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {

                    // 图片选择结果回调
                    var selectList = PictureSelector.obtainMultipleResult(data)
                    val local = selectList.first()
                    if (local != null) {
                        Glide.with(this)
                            .load(local.path)
                            .into(profile_image)
                    }
                }
                else -> {
                }
            }
        }


    }

    fun showDateSelect(date: Date?) {
        val endDate = Date()
        val endCalendar = Calendar.getInstance()//得到calendar
        endCalendar.time = endDate
        val startCalendar = Calendar.getInstance()//得到calendar
        startCalendar.time = endDate//当前时间设置给calendar
        startCalendar.add(Calendar.YEAR, -130)  //当前时间的前6个月
        val selectCalendar = Calendar.getInstance()
        selectCalendar.time = date
        val pvTime =
            TimePickerBuilder(this,
                OnTimeSelectListener { date, v ->
                    Toast.makeText(this, date.toString(), Toast.LENGTH_SHORT)
                        .show()
                    userInfo.birthday = date
                    userInfo.createOrUpdate()
                    Slog.d(" 当前用户对象   $userInfo ")
                })
                .setCancelText(getString(R.string.button_cancel))//取消按钮文字
                .setSubmitText(getString(R.string.button_ok))//确认按钮文字
                .setTitleSize(20)//标题文字大小
                .setTitleText(getString(R.string.profile_date_pick))//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleColor(getIconColor())//标题文字颜色
                .setSubmitColor(getIconColor())//确定按钮文字颜色
                .setCancelColor(getIconColor())//取消按钮文字颜色
                .setTitleBgColor(getBGColor())//标题背景颜色 Night mode
                .setBgColor(getBGColor())//滚轮背景颜色 Night mode
                .setDate(selectCalendar)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startCalendar, endCalendar)//起始终止年月日设定
                .setLabel("", "", "", "", "", "")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build()
        pvTime.show()
    }


}
