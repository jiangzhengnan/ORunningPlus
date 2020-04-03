package com.oplayer.orunningplus.function.main.profile.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bumptech.glide.Glide
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.oplayer.common.common.Constants
import com.oplayer.common.common.UnitType
import com.oplayer.common.common.constant
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.OSportApplciation
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.bean.UserInfo
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.service.BleService
import com.oplayer.orunningplus.utils.DateUtil
import com.oplayer.orunningplus.utils.javautils.Utils
import com.rengwuxian.materialedittext.MaterialEditText
import com.vicpin.krealmextensions.createOrUpdate
import kotlinx.android.synthetic.main.activity_user_info.*
import java.util.*


class UserInfoActivity : BaseActivity() {

    val REQUEST_CODE_CHOOSE = 1


    var userInfo: UserInfo = BleService.INSTANCE.getCurrUser()

    override fun getLayoutId(): Int {
        return R.layout.activity_user_info
    }

    override fun initData() {
    }

    override fun initView() {
        initImageView()

        initToolbar(UIUtils.getString(R.string.main_profile), true)

    }

    override fun onResume() {
        super.onResume()
        assignmentName()
        assignmentBP()
        assignmentBirthday()
        assignmentGender()
        assignmentHeight()
        assignmentWeight()
        var path = BleService.INSTANCE.getCurrUser().iconPath
        if (path != null) {
            Glide.with(this)
                .load(path)
                .into(profile_image)

        }


    }

    private fun initImageView() {

        profile_image.setOnClickListener {
            selectPhoto()
//                        showPicSeleDialog()
        }
    }

    fun selectPhoto() {
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
            .compress(false) // 是否压缩 true or false
            .glideOverride(200, 200) // int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
            .hideBottomControls(true) // 是否显示uCrop工具栏，默认不显示 true or false
            .isGif(false) // 是否显示gif图片 true or false
//                .compressSavePath(getPath())//压缩图片保存地址
            .freeStyleCropEnabled(true) // 裁剪框是否可拖拽 true or false
            .circleDimmedLayer(true) // 是否圆形裁剪 true or false
            .showCropFrame(true) // 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
            .showCropGrid(true) // 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
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


    var currUnit = OSportApplciation.getCurrUnit()

    override fun initInfo() {

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rl_name -> {
                showNameDialog()

            }
            R.id.rl_gender -> {
                val list: List<String> =
                    UIUtils.getContext().resources.getStringArray(R.array.gender_arr).toList()

                showCommSelect(
                    getString(R.string.profile_gender),
                    Constants.SELECT_OPTION_GENDER,
                    list,
                    null,
                    null
                )
            }
            R.id.rl_birthday -> {
                showDateSelect(Date())
            }
            R.id.rl_weight -> {
                val numberList: MutableList<String> = mutableListOf()
                val floatList: MutableList<String> = mutableListOf()
                if (currUnit == UnitType.UNIT_METRIC) {
                    for (index in 1..220) numberList.add(index.toString())
                    for (index in 0..9) floatList.add(".$index")
                } else {
                    for (index in 70..500) numberList.add("$index")
                    for (index in 0..9) floatList.add(".$index")
                }
                showCommSelect(
                    "${getString(R.string.profile_weight)} (${getWeightUnit()})",
                    Constants.SELECT_OPTION_WEIGHT,
                    numberList,
                    floatList,
                    null
                )


            }
            R.id.rl_height -> {
                val numberList: MutableList<String> = mutableListOf()
                val floatList: MutableList<String> = mutableListOf()
                if (currUnit == UnitType.UNIT_METRIC) {
                    for (index in 120..220) numberList.add(index.toString())
                    for (index in 0..9) floatList.add(".$index")
                } else {
                    for (index in 3..7) numberList.add("${index}\'")
                    for (index in 0..11) floatList.add("${index}\"")
                }
                showCommSelect(
                    "${getString(R.string.profile_heightr)} (${getHeightUnit()})",
                    Constants.SELECT_OPTION_HEIGHT,
                    numberList,
                    floatList,
                    null
                )


            }
            R.id.rl_bp -> {
                val numberList: MutableList<Int> = mutableListOf()
                for (index in 60..170) numberList.add(index)
                showCommSelect(
                    "${getString(R.string.profile_bloodr)} (${getString(R.string.unit_bp)})",
                    Constants.SELECT_OPTION_BP,
                    numberList,
                    numberList,
                    null
                )


            }

        }

    }

    private fun showNameDialog() {
        var view = View.inflate(this, R.layout.pop_user_name, null)
        var met_name = view.findViewById<MaterialEditText>(R.id.met_name)
        var userName = BleService.INSTANCE.getCurrUser().name

        if (userName == null) {
            userName = getString(R.string.profile_unknown)
        }
        var hintName = "${getString(R.string.user_info_old_name)} ${userName}"

        met_name.floatingLabelText = hintName
        met_name.floatingLabelTextColor=getIconColor()



        met_name.hint = hintName
        val dialogBuilder = NiftyDialogBuilder.getInstance(this)
        dialogBuilder
            .withTitle(getString(R.string.user_info_name))
            .withMessage(R.string.user_info_chage_message)

            .setCustomView(view, this)
            .withEffect(Effectstype.RotateBottom)
            .withDialogColor(getBGGrayAColor())


            .withTitleColor(getTextColor())
            .withMessageColor(getTextColor())


            .withButton1Text(getString(R.string.button_cancel))

            .withButton2Text(getString(R.string.button_ok))
            .isCancelableOnTouchOutside(true)
            .setButton1Click {
                dialogBuilder.dismiss()
            }
            .setButton2Click {


                if (met_name.text?.length!! > 30) {
                    showToast(getString(R.string.user_info_chage_message))

                } else {
                    user.name = met_name.text.toString()
                    BleService.INSTANCE.saveCurrUser(user)
                    assignmentName()
                    dialogBuilder.dismiss()
                }


            }
        dialogBuilder.show()


    }



    var user = BleService.INSTANCE.getCurrUser()
    private fun showCommSelect(
        title: String,
        optionType: String,
        opentionList1: List<Any>?,
        opentionList2: List<Any>?,
        opentionList3: List<Any>?
    ) {
        // 不联动的多级选项
        var pvOptions = OptionsPickerBuilder(this,
            OnOptionsSelectListener { options1, options2, options3, v ->
                Slog.d("OnOptionsSelectListener  optionType  $optionType  title  $title  optionType  $optionType   options1 $options1   options2 $options2   options3 $options3 ")

                when (optionType) {
                    Constants.SELECT_OPTION_GENDER -> {

                        var gender = opentionList1?.get(options1)
                        user.gender = options1
                        BleService.INSTANCE.saveCurrUser(user)
                        assignmentGender()
                        showToast("当前性别为  $gender")
                    }
                    Constants.SELECT_OPTION_WEIGHT -> {

                        var weightInt = opentionList1?.get(options1)
                        var weightFloat = opentionList2?.get(options2)

                        var str = "$weightInt$weightFloat"

                        //全部转换为公制保存
                        var weightValue = 70F
                        try {
                            weightValue = str.toFloat()
                            if (currUnit == UnitType.UNIT_BRITISH) {
                                weightValue = str.toFloat() / constant.kg_to_lb
                            }
                        } catch (e: Exception) {
                            Slog.d("体重转换异常  ${e.toString()}")

                        }

                        user.weight = weightValue
                        BleService.INSTANCE.saveCurrUser(user)
                        assignmentWeight()
                        showToast("当前体重为   weightValue  $weightValue")


                    }
                    Constants.SELECT_OPTION_HEIGHT -> {


                        var heightInt = opentionList1?.get(options1)
                        var heightFloat = opentionList2?.get(options2)


                        //全部转换为公制保存
                        var heightValue = 175F
                        try {

                            if (currUnit == UnitType.UNIT_METRIC) {
                                var str = "$heightInt$heightFloat"

                                heightValue = str.toFloat()
                            } else {
                                heightInt = heightInt.toString().replace("\'", "").toInt()
                                heightFloat = heightFloat.toString().replace("\"", "").toInt()
                                heightValue =
                                    (heightInt * constant.ft_to_cm) + (heightFloat * constant.in_to_cm)

                            }

                        } catch (e: Exception) {
                            Slog.d("身高转换异常  ${e.toString()}")

                        }


                        showToast("当前体重为   weightValue  $heightValue")
                        user.height = heightValue
                        BleService.INSTANCE.saveCurrUser(user)
                        assignmentHeight()

                    }
                    Constants.SELECT_OPTION_BP -> {

                        var bp_hight = opentionList1?.get(options1)
                        var bp_low = opentionList2?.get(options2)

                        showToast("当前血压为   高压 $bp_hight  低压 $bp_low")
                        user.bloodPressure = "$bp_hight/$bp_low"
                        BleService.INSTANCE.saveCurrUser(user)
                        assignmentBP()
                    }

                }


            })

            .setTitleColor(getIconColor())
            .setSubmitColor(getIconColor())
            .setCancelColor(getIconColor())
            .setTitleText(title)
//            .setCyclic(true, true, true)//循环与否
            .setSubmitText(getString(R.string.button_ok))//确定按钮文字
            .setCancelText(getString(R.string.button_cancel))//取消按钮文字
            .build<Any>()
        pvOptions.setNPicker(opentionList1, opentionList2, opentionList3)

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

                    selectList.forEach {

                        Slog.d("   图片选择结果回调  $it ")
                    }

                    val local = selectList.first()

                    if (local != null) {

                        user.iconPath = local.path
                        BleService.INSTANCE.saveCurrUser(user)

                        Glide.with(this)
                            .load(user.iconPath)
                            .into(profile_image)


                        Slog.d("图片选择界面路径   ${user.iconPath}")

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
                    assignmentBirthday()
                    userInfo.birthday = date
                    userInfo.createOrUpdate()

                })
                .setCancelText(getString(R.string.button_cancel))//取消按钮文字
                .setSubmitText(getString(R.string.button_ok))//确认按钮文字
                .setTitleSize(20)//标题文字大小
                .setTitleText(getString(R.string.profile_birthday))//标题文字
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


    fun assignmentGender() {
        var gender = BleService.INSTANCE.getCurrUser().gender
        if (gender != null) {
            tv_gender.text = resources.getStringArray(R.array.gender_arr)[gender]
        }

    }


    fun assignmentBirthday() {

        var data = BleService.INSTANCE.getCurrUser().birthday
        if (data != null) {
            tv_birthday.text = DateUtil.getCurDateStr(data)
        }
    }

    fun assignmentWeight() {
        var weight = BleService.INSTANCE.getCurrUser().weight
        if (weight != null) {
            if (currUnit == UnitType.UNIT_BRITISH) {
                weight *= constant.kg_to_lb
                weight=Utils.formatFloat(weight,"0.0");
            }
            tv_weight.text = "$weight ${getWeightUnit()}"
        }
    }

    fun assignmentName() {
        var name = BleService.INSTANCE.getCurrUser().name
        if (name != null) {

            tv_name.text = name
        }
    }

    fun assignmentHeight() {
        var height = BleService.INSTANCE.getCurrUser().height
        var heightStr = height.toString()

        if (height != null) {

            if (currUnit == UnitType.UNIT_BRITISH) {
                height / constant.ft_to_cm
                heightStr =

                    "${(height / constant.ft_to_cm).toInt()}\'${(height % constant.ft_to_cm / constant.in_to_cm).toInt()}\""

            }

            tv_height.text = "$heightStr ${getHeightUnit()}"
        }
    }

    fun assignmentBP() {
        var BP = BleService.INSTANCE.getCurrUser().bloodPressure

        if (BP != null) {

            tv_bp.text = "$BP ${resources.getString(R.string.unit_bp)}"
        }
    }


    fun getWeightUnit(): String {

        return resources.getStringArray(R.array.unit_weight_arr)[currUnit]

    }


    fun getHeightUnit(): String {

        return resources.getStringArray(R.array.unit_arr)[currUnit]

    }
}
