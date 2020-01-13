package com.ng.lib_common.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.github.johnpersano.supertoasts.library.Style
import com.github.johnpersano.supertoasts.library.SuperActivityToast

import com.oplayer.common.mvp.IBaseView
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.event.MessageEvent
import com.tapadoo.alerter.Alerter
import kotlinx.android.synthetic.main.toolbar_common.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


abstract class BaseFragment : Fragment(), IBaseView {
    private val STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN"
    lateinit var mActivity: Context
    /**
     * 视图是否加载完毕
     */
    private var isViewPrepare = false
    /**
     * 数据是否加载过了
     */
    private var hasLoadData = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        EventBus.builder().build().register(this)
        return inflater.inflate(getLayoutId(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        initView()
        lazyLoadDataIfPrepared()

    }

    open fun startTo(targetClass: Class<out Activity>) {
        val intent = Intent(context, targetClass)
        context?.startActivity(intent)
    }

    @Subscribe
    fun onMessageEvent(event: MessageEvent) {
        onGetEvent(event)
    }

    protected abstract fun onGetEvent(event: MessageEvent)

    private lateinit var alertDialog: AlertDialog
    fun isAlertDialogInit() = ::alertDialog.isInitialized

    fun showLoadingDialog() {
        if (!alertDialog.isShowing) {
            alertDialog.show()
        }
    }

    fun dismissLoadingDialog() {
        if (isAlertDialogInit() && alertDialog.isShowing) {
            alertDialog.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden)
    }

    override fun onAttach(context: Context) {
        this.mActivity = context
        super.onAttach(context)
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }


    private fun lazyLoad() {
        initInjector()
        lazyLoadData()
    }


    abstract fun onClick(v: View)
    abstract fun getLayoutId(): Int
    abstract fun initInjector()
    abstract fun initView()
    abstract fun lazyLoadData()


    /**
     * 获取主题颜色
     * */

    fun getBGColor(): Int = UIUtils.getColor(R.color.colorPrimary)

    fun getTransparentColor(): Int = UIUtils.getColor(R.color.transparent_color)
    fun getTextColor(): Int = UIUtils.getColor(R.color.white_date_text_color)
    fun getIconColor(): Int = UIUtils.getColor(R.color.icon_green_color)

    open fun initToolbar(title: String) {

        toolbar_title.text = title
        iv_back.visibility = View.GONE
    }


    fun showToast(msg: String) {
        SuperActivityToast.create(mActivity, Style(), Style.TYPE_STANDARD)
            .setText(msg)
            .setDuration(Style.DURATION_VERY_SHORT)
            .setFrame(Style.FRAME_LOLLIPOP)
            .setTextColor(getTextColor())
            .setColor(getBGColor())
            .setAnimations(Style.ANIMATIONS_POP).show()
    }


    open fun startTo(mContext: Context?, targetClass: Class<out Activity>) {
        Slog.d("普通 跳转 目标 ${targetClass.name}")
        val intent = Intent(mContext, targetClass)
        mContext?.startActivity(intent)

    }

    override fun showAlert(message: String, enablePro: Boolean, iconResId: Int, showIcon: Boolean) {
        Alerter.create(activity)
            .enableProgress(enablePro)
            .setIcon(iconResId)
            .showIcon(showIcon)
            .setBackgroundColorInt(getBGColor())
            .setText(message)
            .show()
    }

}