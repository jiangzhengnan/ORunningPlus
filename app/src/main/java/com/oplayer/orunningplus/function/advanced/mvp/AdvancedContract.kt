package com.oplayer.orunningplus.function.advanced.mvp

import android.content.Context
import com.oplayer.common.mvp.IBasePresenter
import com.oplayer.common.mvp.IBaseView
import com.oplayer.orunningplus.bean.SettingItem

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.advanced.mvp
 * @ClassName:      AdvancedContract
 * @Description:    高级设置契约类
 * @Author:         Ben
 * @CreateDate:     2020/1/9 9:18
 */
class AdvancedContract  {

    interface View :IBaseView{

        fun  showSettingItem(list:List<SettingItem>)

    }

    interface  Presenter:IBasePresenter<View>{
        fun  getSettingItem(mContext:Context)
    }


    interface  Model{

     fun   getAdvancedItem(mContext: Context):List<SettingItem>

    }


}