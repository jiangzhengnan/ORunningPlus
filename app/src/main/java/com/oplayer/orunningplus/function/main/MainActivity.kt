package com.oplayer.orunningplus.function.main

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder
import com.oplayer.common.common.BluetoothState
import com.oplayer.common.common.Constants
import com.oplayer.common.common.PermissList
import com.oplayer.common.common.SystemSetting
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.main.settings.SettingsFragment
import com.oplayer.orunningplus.function.main.today.SportFragment
import com.oplayer.orunningplus.function.main.today.TodayFragment
import com.oplayer.orunningplus.service.BleService
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {


    var fragmentList = mutableListOf<Fragment>()

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {

    }

    override fun initView() {
//        startTo(TestActivity::class.java)
        //开发阶段 指令测试
        initViewPager()
        initTabBar()
    }

    private fun initViewPager() {

        fragmentList.add(TodayFragment())
        fragmentList.add(SportFragment())
        fragmentList.add(SportFragment())
        fragmentList.add(SettingsFragment())
        nsv_main.adapter = ViewPagerAdapter(supportFragmentManager)
        nsv_main.offscreenPageLimit = 4

    }

    private fun initTabBar() {
        bb_main.setOnTabSelectListener {
            when (it) {
                R.id.tab_today -> {
                    nsv_main.setCurrentItem(0,false)
                }
                R.id.tab_history -> {
                    nsv_main.setCurrentItem(1,false)
                }
                R.id.tab_clockface -> {
                    nsv_main.setCurrentItem(2,false)
                }
                R.id.tab_settings -> {
                    nsv_main.setCurrentItem(3,false)
                }
                else -> {

                Slog.d("未知按钮   $it")
                }
            }

        }


    }


    override fun onResume() {
        super.onResume()

    }

    override fun initInfo() {
        checkBTState()
        checkNotification()
        checkPermission(PermissList.PERMISSIONS_LIST)
    }

    override fun onClick(v: View) {

        when (v.id) {


        }

    }

    override fun onGetEvent(event: MessageEvent) {


    }

    fun checkNotification() {
        /**
         * Fadein, Slideleft, Slidetop, SlideBottom,
         * Slideright, Fall, Newspager, Fliph, Flipv,
         * RotateBottom, RotateLeft, Slit, Shake, Sidefill
         * */
        if (isNotificationEnabled()) {
            return
        }
        val dialogBuilder = NiftyDialogBuilder.getInstance(this)
        dialogBuilder.withTitle("通知使用权")
            .withMessage("请开启通知使用权")
            .withEffect(Effectstype.RotateBottom)
            .withDialogColor(getBGColor())
            .withTitleColor(getTextColor())
            .withMessageColor(getTextColor())
            .withButton1Text("取消")
            .withButton2Text("确定")
            .isCancelableOnTouchOutside(true)
            .setButton1Click {
                dialogBuilder.dismiss()
            }
            .setButton2Click {
                startActivity(SystemSetting.NOTIFICATION_LISTENER_INTENT)
                dialogBuilder.dismiss()
            }
        dialogBuilder.show()

    }


    inner class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager) {

        var fm: FragmentManager? = null

        init {
            this.fm = fragmentManager
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList.get(position)
        }

        override fun getCount(): Int {
            return fragmentList.size
        }
    }

}
