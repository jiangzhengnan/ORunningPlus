package com.oplayer.orunningplus.function.connect

import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.oplayer.common.common.Constants.Companion.DEVICE_SELECT_ONCLICK
import com.oplayer.common.utils.Slog
import com.oplayer.orunningplus.OSportApplciation
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.connect.mvp.DeviceDetailData
import com.oplayer.orunningplus.function.connect.mvp.DeviceDetailDataArr
import com.oplayer.orunningplus.function.connect.mvp.DeviceTitleData
import org.greenrobot.eventbus.EventBus

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.connect
 * @ClassName:      DeviceSelectAdapter
 * @Description:    设备选择界面 adapter
 * @Author:         Ben
 * @CreateDate:     2020/3/13 11:05
 */
class DeviceSelectAdapter(data: List<MultiItemEntity>) :
    BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    companion object {
        val TYPE_DEVICE_TITLE = 0
        val TYPE_DEVICE_DETAIL = 1
    }


    init {
        addItemType(TYPE_DEVICE_TITLE, R.layout.item_device_title)
        addItemType(TYPE_DEVICE_DETAIL, R.layout.item_device_detail_rv)


    }


    override fun convert(helper: BaseViewHolder?, item: MultiItemEntity) {


        when (helper!!.itemViewType) {
            TYPE_DEVICE_TITLE -> {


                var titleItem = item as DeviceTitleData

                helper.setText(R.id.tv_title, titleItem.title)
                    .setImageResource(
                        R.id.iv_title,
                        if (titleItem.isExpanded) R.mipmap.device_close else R.mipmap.device_open
                    )

                helper.itemView.setOnClickListener {

                    Slog.d("列表点击事件 ${helper.position}")

                    if (titleItem.isExpanded) {
                        collapse(helper.position)
                    } else {
                        expand(helper.position)
                    }


                }
            }
            TYPE_DEVICE_DETAIL -> {

                var detailItem = item as DeviceDetailDataArr
                var recyclerView = helper.itemView.findViewById<RecyclerView>(R.id.rv_device_detaill)
                recyclerView.layoutManager = GridLayoutManager(OSportApplciation.sContext, 2)
                var adapter = DeviceDetailAdapter(R.layout.item_device_detail, detailItem.list)
                adapter.setOnItemClickListener { adapter, view, position -> val device = adapter.data[position] as DeviceDetailData; EventBus.getDefault().post(MessageEvent(DEVICE_SELECT_ONCLICK, device)) }
                adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
                recyclerView.adapter = adapter


            }
        }

    }


}