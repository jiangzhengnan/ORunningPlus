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

import com.oplayer.common.mvp.IBaseView
import com.oplayer.orunningplus.event.MessageEvent
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


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

}