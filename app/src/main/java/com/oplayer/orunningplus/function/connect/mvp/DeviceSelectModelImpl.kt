package com.oplayer.orunningplus.function.connect.mvp

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.oplayer.common.common.DeviceType
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.connect.mvp
 * @ClassName:      DeviceSelectModel
 * @Description:    设备选择界面数据收集
 * @Author:         Ben
 * @CreateDate:     2020/3/13 14:31
 */
class DeviceSelectModelImpl : DeviceSelectContract.Model {
    override fun getDeviceSelectData(): List<MultiItemEntity> {


        val res: ArrayList<MultiItemEntity> = ArrayList()
        var gpsDevice = DeviceTitleData(UIUtils.getString(R.string.device_select_gps))
        var roundDevice = DeviceTitleData(UIUtils.getString(R.string.device_select_round))
        var squareDevice = DeviceTitleData(UIUtils.getString(R.string.device_select_square))
        var braceletDevice = DeviceTitleData(UIUtils.getString(R.string.device_select_bracelet))
        var ecgDevice = DeviceTitleData(UIUtils.getString(R.string.device_select_ecg))


        /*GPS手表集合   类型暂时 都为分动*/

        var gpsList = mutableListOf(
            DeviceDetailData(R.mipmap.device_1040, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1104, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1105, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1305, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1311, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1312, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1314, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1315, DeviceType.DEVICE_FUNDO)
        )



        /*圆形手表集合*/
        var roundList = mutableListOf(
            DeviceDetailData(R.mipmap.device_1024, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1027, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1046, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1204, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1302, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1306, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1321, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1323, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1327, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1337, DeviceType.DEVICE_FUNDO)




        )
        /*方形手表集合*/
        var squareList = mutableListOf(
            DeviceDetailData(R.mipmap.device_1324, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1325, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1326, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1331, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1402, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1424, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1603, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1605, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1606, DeviceType.DEVICE_FUNDO)
        )
        /*手环集合*/
        var braceletList = mutableListOf(
            DeviceDetailData(R.mipmap.device_1002, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1003, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1005, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1006, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1007, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1016, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1017, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1023, DeviceType.DEVICE_FUNDO)
        )

        /*心电集合*/
        var ecgList = mutableListOf(
            DeviceDetailData(R.mipmap.device_1044, DeviceType.DEVICE_FUNDO),
            DeviceDetailData(R.mipmap.device_1045, DeviceType.DEVICE_FUNDO)




        )

        gpsDevice.addSubItem(0, DeviceDetailDataArr(gpsList))
        roundDevice.addSubItem(0, DeviceDetailDataArr(roundList))
        squareDevice.addSubItem(0, DeviceDetailDataArr(squareList))
        braceletDevice.addSubItem(0, DeviceDetailDataArr(braceletList))
        ecgDevice.addSubItem(0, DeviceDetailDataArr(ecgList))

        res.add(gpsDevice)
        res.add(roundDevice)
        res.add(squareDevice)
        res.add(braceletDevice)
        res.add(ecgDevice)

        return res


    }


}