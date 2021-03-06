package com.oplayer.orunningplus.utils.javautils

import com.oplayer.common.utils.Slog
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.regex.Pattern

class Utils {

    companion object {

        /**
         * 获取Realm数据库64位秘钥
         *
         * @param key
         * @return
         */
//         fun getRealmKey(key: String): ByteArray? {
//            var newKey = ""
//            for (i in 0..3) {
//                newKey = newKey + key
//            }
//            return newKey.toByteArray()
//        }

        /**
         * 根据密钥长度补足密钥
         *
         * @return
         */
        open fun toMakekey(str: String, strLength: Int, `val`: String): String? {
            var str = str
            var strLen = str.length
            if (strLen < strLength) {
                while (strLen < strLength) {
                    val buffer = StringBuffer()
                    buffer.append(str).append(`val`)
                    str = buffer.toString()
                    strLen = str.length
                }
            }
            return str
        }

        fun formatFloat(value: Float,pattern: String): Float {
            val df = DecimalFormat(pattern)
            var formatValue: Float;
            df.decimalFormatSymbols
            val symbols = DecimalFormatSymbols()
            symbols.decimalSeparator = '.'
            df.decimalFormatSymbols = symbols
            formatValue = value
            try {
                formatValue = df.format(value).toFloat()
            } catch (e: Exception) {
                Slog.d("数字格式化异常 $e")
            }

            return formatValue
        }


    }

}