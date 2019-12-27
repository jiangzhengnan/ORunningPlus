package com.ng.lib_common.base

import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

/**
 * @ProjectName: DXZS
 * @Package: com.dx.dxzs.bean
 * @Description: 弱引用  https://www.jianshu.com/p/9e07774dd32d?utm_source=oschina-app
 * //需要指定初始值的情况
    //自动推断出泛型
var act by Weak{
context
}
//也可以指定泛型，一种是给属性指定类型，必须为可null的
var act: Activity? by Weak {
context
}
//第二种是为Weak指定泛型，不可null的
var act by Weak<Activity> {
context
}

//不指定初始值的情况，此时必须指定泛型
var act:Activity? by Weak()
或者
var act by Weak<Activity>()
 * @Author: Eden
 * @CreateDate: 2019/7/10 14:40
 */
class Weak<T : Any>(initializer: () -> T?) {
    private var weakReference = WeakReference<T?>(initializer())

    constructor() : this({
        null
    })

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return weakReference.get()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        weakReference = WeakReference(value)
    }

}