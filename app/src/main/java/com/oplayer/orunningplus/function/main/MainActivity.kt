package com.oplayer.orunningplus.function.main

import android.graphics.Color
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder
import com.oplayer.common.common.PermissList
import com.oplayer.common.common.SystemSetting
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.OSportApplciation
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.main.today.SportFragment
import com.oplayer.orunningplus.function.main.today.TodayFragment
import com.oplayer.orunningplus.function.test.TestActivity
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


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
        initToolbar("MainActivity", true)
        initTabBar()
        initViewPager()
    }

    private fun initViewPager() {

        fragmentList.add(TodayFragment())
        fragmentList.add(SportFragment())
        fragmentList.add(TodayFragment())
        fragmentList.add(SportFragment())
        nsv_main.adapter = ViewPagerAdapter(supportFragmentManager)

        val models: ArrayList<NavigationTabBar.Model> = ArrayList()
        models.add(NavigationTabBar.Model.Builder(resources.getDrawable(R.drawable.ic_launcher_foreground), UIUtils.getSkinColor()).title("today"    ).build())
        models.add(NavigationTabBar.Model.Builder(resources.getDrawable(R.drawable.ic_launcher_foreground), UIUtils.getSkinColor()).title("sport"    ).build())
        models.add(NavigationTabBar.Model.Builder(resources.getDrawable(R.drawable.ic_launcher_foreground), UIUtils.getSkinColor()).title("heart"    ).build())
        models.add(NavigationTabBar.Model.Builder(resources.getDrawable(R.drawable.ic_launcher_foreground), UIUtils.getSkinColor()).title("profile"    ).build())
        ntb_main.setBackgroundColor(UIUtils.getSkinColor())
        ntb_main.setModels(models)
        ntb_main.setViewPager(nsv_main)



    }

    private fun initTabBar() {


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
        Slog.d("检查通知使用权")
        if (isNotificationEnabled()) {
            showToast("通知使用权已经开启  ")
            Slog.d("通知使用权已经开启")
            return
        }
        val dialogBuilder = NiftyDialogBuilder.getInstance(this)

        dialogBuilder.withTitle("通知使用权")
            .withMessage("请开启通知使用权")
            .withEffect(Effectstype.RotateBottom)
            .withDialogColor(UIUtils.getSkinColor())
            .withButton1Text("取消")
            .withButton2Text("确定")
            .isCancelableOnTouchOutside(true)
            .setButton1Click {
                showToast("点击按钮1")
                dialogBuilder.dismiss()
            }
            .setButton2Click {
                showToast("点击按钮2")
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
