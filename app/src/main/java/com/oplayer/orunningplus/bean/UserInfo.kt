package com.oplayer.orunningplus.bean

import com.htsmart.wristband2.bean.config.BloodPressureConfig
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
    open var name: String? = ""
    open var iconPath: String? = ""
    open var gender: String? = ""
    open var birthday: Date? = Date()
    open var weight: Int? = 70
    open var height: Int? = 180
    open var bloodPressure: String? = ""
    open var stepGoal: Int? = 8000
    open var sleepGoal: Int? = 480//单位分钟

    override fun toString(): String {
        return "UserInfo(id=$id, name=$name, iconPath=$iconPath, gender=$gender, birthday=$birthday, weight=$weight, height=$height, bloodPressure=$bloodPressure, stepGoal=$stepGoal, sleepGoal=$sleepGoal)"
    }


}