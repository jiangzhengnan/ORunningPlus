package com.oplayer.orunningplus.function.connect

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.bean.BluetoothDeviceInfo
import com.polidea.rxandroidble2.scan.ScanResult

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.lib_device.adapter
 * @ClassName:      DeviceAdapter
 * @Description:     java类作用描述
 * @Author:         Ben
 * @CreateDate:     2019/7/26 10:00
 */
class DeviceAdapter(layoutResId: Int, data: List<BluetoothDeviceInfo>):BaseQuickAdapter<BluetoothDeviceInfo,BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: BluetoothDeviceInfo?) {
        helper?.setText(R.id.tv_device_name, item?.scanResult?.bleDevice?.name)
        helper?.setText(R.id.tv_device_address, item?.scanResult?.bleDevice?.macAddress)
//        helper?.setText(R.id.tv_device_rssi, item?.rssi!!)
    }


}