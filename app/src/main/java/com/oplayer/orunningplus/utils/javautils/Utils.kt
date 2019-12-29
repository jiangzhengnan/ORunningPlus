package com.oplayer.orunningplus.utils.javautils

class Utils {

    companion object{

        /**
         * 获取Realm数据库64位秘钥
         *
         * @param key
         * @return
         */
        open fun getRealmKey(key: String): ByteArray? {
            var newKey = ""
            for (i in 0..3) {
                newKey = newKey + key
            }
            return newKey.toByteArray()
        }


    }

}