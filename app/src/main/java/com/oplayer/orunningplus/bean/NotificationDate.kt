package com.oplayer.orunningplus.bean

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.bean
 * @ClassName:      NotificationDate
 * @Description:     封装NotificationDate 通知类
 * @Author:         Ben
 * @CreateDate:     2019/12/31 16:02
 */
@RealmClass
open class NotificationDate(
    open var pkg: String? = "",
    open var tag: String? = "",
    open var contentTitle: String? = "",
    open var contentText: String? = ""
) : RealmObject() {
    @PrimaryKey
    open var id: String? = ""

    override fun toString(): String {
        return "NotificationDate(pkg=$pkg, tag=$tag, contentTitle=$contentTitle, contentText=$contentText, id=$id)"
    }


}