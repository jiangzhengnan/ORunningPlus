package com.oplayer.orunningplus.bean

import com.htsmart.wristband2.bean.config.BloodPressureConfig
import com.oplayer.common.common.UserConstant
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.bean
 * @ClassName:      UserInfo
 * @Description:   封装用户对象
 * @Author:         Ben
 * @CreateDate:     2020/1/13 11:22
 */
@RealmClass
open class UserInfo : RealmObject() {
    @PrimaryKey
    open var id: String? = ""
    open var name: String? = null
    open var iconPath: String? = null
    open var gender: Int? = null
    open var birthday: Date? = null
    open var weight: Float? = null
    open var height: Float? = null
    open var bloodPressure: String? = null
    open var stepGoal: Int? = null
    open var sleepGoal: Int? = null//单位分钟

    override fun toString(): String {
        return "UserInfo(id=$id, name=$name, iconPath=$iconPath, gender=$gender, birthday=$birthday, weight=$weight, height=$height, bloodPressure=$bloodPressure, stepGoal=$stepGoal, sleepGoal=$sleepGoal)"
    }


}