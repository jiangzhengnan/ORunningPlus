package com.oplayer.orunningplus.function.connect

import android.Manifest
import android.os.Handler
import android.os.ParcelUuid
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.oplayer.common.common.BluetoothState
import com.oplayer.common.common.Constants
import com.oplayer.common.common.DeviceUUID
import com.oplayer.common.common.ScanDeviceState
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils

import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.bean.BluetoothDeviceInfo
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.main.MainActivity
import com.oplayer.orunningplus.service.BleService
import com.polidea.rxandroidble2.scan.ScanFilter
import com.scwang.smartrefresh.layout.header.BezierRadarHeader
import kotlinx.android.synthetic.main.activity_connect.*
import org.greenrobot.eventbus.EventBus

class ConnectActivity : BaseActivity() {
    var mDevice: MutableList<BluetoothDeviceInfo> = mutableListOf()
    private lateinit var mDeviceAdapter: DeviceAdapter
    override fun getLayoutId(): Int {
        return R.layout.activity_connect
    }
    override fun initInfo() {}

    override fun initView() {
        initSRL()
        checkState()
        initRecycle()
        initToolbar(UIUtils.getString(R.string.title_current_device),true)

    }
    override fun initData() {
        disConnaction()
        scanDevice()
    }

    private fun disConnaction() {

        BleService.INSTANCE.disConnBle()
    }


    override fun onClick(v: View) {
    }

    override fun onGetEvent(event: MessageEvent) {

        when (event.getMessageType()) {
            ScanDeviceState.SCAN_RESULT -> {
                var scanResult = event.getMessage() as BluetoothDeviceInfo
                Slog.d("搜索蓝牙返回    $scanResult")
                mDevice.add(scanResult)
                mDeviceAdapter.notifyItemChanged(mDevice.size)
            }
            ScanDeviceState.SCAN_START -> {
                Slog.d("搜索蓝牙开始")
            }
            ScanDeviceState.SCAN_STOP -> {
                Slog.d("搜索蓝牙停止")
            }
            ScanDeviceState.SCAN_ERROR -> {
                Slog.d("搜索蓝牙错误")
            }

            Constants.BLUETOOTH_MESSAGE -> {
                Slog.d("蓝牙连接消息")
                when (event.getMessage()) {
                    BluetoothState.CONNECTIONNTING -> {
                        Slog.d("回调连接中 ")
                    }
                    BluetoothState.CONNECTION_FAILED -> {
                        showAlert(UIUtils.getString(R.string.device_state_failed), true, R.mipmap.ic_launcher_round, false)
                        //连接失败可点击
                        rv_devices.isEnabled=true
                    }
                    BluetoothState.CONNECTION_SUCCESS -> {
                        showAlert(UIUtils.getString(R.string.device_state_success), true, R.mipmap.ic_launcher_round, false)
                        //todo 测试代码跳转位置
                       Handler().postDelayed({ startTo(MainActivity::class.java); finish() },1000)

                    }
                }

            }

        }

    }

    private fun initRecycle() {
        rv_devices.layoutManager = LinearLayoutManager(this)
        mDeviceAdapter = DeviceAdapter(R.layout.item_device, mDevice)
        //渐显、缩放、从下到上，从左到右、从右到左
        mDeviceAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        mDeviceAdapter.isFirstOnly(false)
        mDeviceAdapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                //停止搜索
                BleService.INSTANCE.stopScanDevice()

                val bluetoothDeviceInfo = mDevice[position]
                Slog.d("device  $bluetoothDeviceInfo")
                showAlert(UIUtils.getString(R.string.device_state_connectionning), true, R.mipmap.ic_launcher_round, false)
                //发送连接对象
                EventBus.getDefault().post(MessageEvent(Constants.BLUETOOTH_DEVICE, bluetoothDeviceInfo))
                //设置不可点击
                rv_devices.isEnabled=false
                //只保留点击设备
                mDevice.clear()
                mDevice.add(bluetoothDeviceInfo)
                mDeviceAdapter.setNewData(mDevice)

            }
        rv_devices.adapter = mDeviceAdapter

    }

    override fun onResume() {
        super.onResume()

    }

    private fun checkState() {
        checkBTState()
        checkPermission(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    override fun onDestroy() {
        super.onDestroy()
        BleService.INSTANCE.stopScanDevice()
    }

    override fun onStop() {
        super.onStop()
        BleService.INSTANCE.stopScanDevice()
    }

    private fun initSRL() {
        //设置 Header 为 贝塞尔雷达 样式
        srl_device.setPrimaryColors(getBGGrayColor())
        srl_device.setRefreshHeader(BezierRadarHeader(this).setEnableHorizontalDrag(true))
        srl_device.setOnRefreshListener {
            it.finishRefresh(2000/*,false*/)//传入false表示刷新失败
            mDevice.clear()
            mDeviceAdapter.notifyDataSetChanged()
            scanDevice()
        }
        srl_device.setOnLoadMoreListener {
            it.finishLoadMore(2000/*,false*/)//传入false表示加载失败
        }


    }

    private fun scanDevice() {
        val scanFilter = ScanFilter.Builder()
            .setServiceUuid(ParcelUuid.fromString(DeviceUUID.FUNDO_BLE_YDS_UUID.toString()))
            .build()
        BleService.INSTANCE.scanDevice(scanFilter)
    }




}
