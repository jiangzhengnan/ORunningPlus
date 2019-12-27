package com.oplayer.orunningplus.base

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ng.lib_common.base.Weak
import com.oplayer.common.common.AppManager
import com.oplayer.common.mvp.IBasePresenter
import com.oplayer.common.mvp.IBaseView
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.event.MessageEvent
import com.tapadoo.alerter.Alerter
import com.tbruyelle.rxpermissions2.RxPermissions
import io.multimoon.colorful.CAppCompatActivity
import io.multimoon.colorful.Colorful
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.nio.file.Path
import com.kct.bluetooth.pkt.FunDo.i
import me.weyye.hipermission.PermissionCallback
import me.weyye.hipermission.HiPermission
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.app.ComponentActivity.ExtraData
import androidx.fragment.app.FragmentActivity
import me.weyye.hipermission.PermissionItem


abstract class BaseActivity : CAppCompatActivity(), IBaseView {
    private var presenters: MutableList<IBasePresenter<IBaseView>> = mutableListOf()

    var act: Activity? by Weak()

    protected fun addPresenter(p: IBasePresenter<IBaseView>) {
        presenters.add(p)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        act = this
        AppManager.instance.addActivity(act as BaseActivity)
        registerEventBus(this)

        initInfo()
        initView()
        initData()
    }

    open fun startTo(targetClass: Class<out Activity>) {
        Slog.d("普通 跳转 目标 ${targetClass.name}")
        val intent = Intent(this, targetClass)
        startActivity(intent)

    }



    abstract fun getLayoutId(): Int



    abstract fun initData()

    abstract fun initView()

    abstract fun initInfo()


    abstract fun onClick(v: View)

    @Subscribe
    fun onMessageEvent(event: MessageEvent) {
        onGetEvent(event)
    }

    protected abstract fun onGetEvent(event: MessageEvent)

    override fun onDestroy() {
        super.onDestroy()
        unregisterEventBus(this)
        presenters.forEach { it.detachView() }
        //AppManager.finishActivity()
    }


    fun openKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText as View?, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun closeKeyBord() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive && currentFocus != null) {
            if (currentFocus!!.windowToken != null) {
                imm.hideSoftInputFromWindow(
                    currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    override fun showAlert(message: String, enablePro: Boolean, iconResId: Int, showIcon: Boolean) {


        Alerter.create(this@BaseActivity)
            .enableProgress(enablePro)
            .setIcon(iconResId)
            .showIcon(showIcon)
            .setBackgroundColorInt(UIUtils.getSkinColor())
            .setText(message)
            .show()
    }

    /**
     * 隐藏加载框的默认实现
     */
    override fun hideLoading() {
        Slog.d("hide loading ")
    }

    /**
     * 错误信息的默认实现
     */
    override fun onError(message: String) {
        Slog.d("error " + message)
    }

    @SuppressLint("CheckResult")
    public fun checkPermission(permissions: Array<String>) {
        val rxPermission = RxPermissions(this)
        Slog.d("检查全权限")
        rxPermission
            .requestEach(*permissions)
            .subscribe { t ->
                if (t?.granted!!) {
                    // 用户已经同意该权限
//                    Slog.d("用户已经同意该权限")
                } else if (t.shouldShowRequestPermissionRationale) {
                    // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
//                    Slog.d("用户拒绝了该权限，没有选中『不再询问』")
                    //                        AppManager.instance.finishActivity(this)
                } else {
                    // 用户拒绝了该权限，并且选中『不再询问』
//                    Slog.d("用户拒绝了该权限，并且选中『不再询问』")
                    //                        AppManager.instance.finishActivity(ScanDeviceActivity)

                }
            }

    }


    //带UI的权限请求
    public fun checkPermis( permissionItems:MutableList<PermissionItem>) {


        HiPermission.create(this)
            .permissions(permissionItems)
            .checkMutiPermission(object : PermissionCallback {
                override fun onClose() {
                    Slog.d("onClose ")
                }

                override fun onFinish() {
                    Slog.d("onFinish ")
                }

                override fun onDeny(permission: String, position: Int) {
                    Slog.d("onDeny  $String")
                }

                override fun onGuarantee(permission: String, position: Int) {
                    Slog.d("onGuarantee  $String")
                }
            })


    }


    val REQUEST_ENABLE_BT = 1

    public fun checkBTState() {
        val blueadapter = BluetoothAdapter.getDefaultAdapter()
        if (!blueadapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        } else {
            Slog.d("蓝牙开启")
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BT) {
            Slog.d("蓝牙开启成功")
        }
    }

    fun isEventBusRegisted(subscribe: Any): Boolean {
        return EventBus.getDefault().isRegistered(subscribe)
    }

    fun registerEventBus(subscribe: Any) {
        if (!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe)
        }
    }

    fun unregisterEventBus(subscribe: Any) {
        if (isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe)
        }
    }

}