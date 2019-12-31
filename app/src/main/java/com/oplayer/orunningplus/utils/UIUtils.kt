package com.oplayer.common.utils

import android.content.Context
import android.content.res.Resources
import com.oplayer.orunningplus.OSportApplciation
import io.multimoon.colorful.Colorful
import io.multimoon.colorful.ThemeColor
import io.multimoon.colorful.ThemeColorInterface

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.common.utils
 * @ClassName:      UIUtils
 * @Description:    公共工具类
 * @Author:         Ben
 * @CreateDate:     2019/7/26 14:33
 */
class UIUtils {
    companion object {


        fun getContext(): Context {
            return OSportApplciation.sContext
        }



        /**
         * 获取资源对象
         */
        fun getSkinColor(): Int {
            return Colorful().getPrimaryColor().getColorPack().dark().asInt()
        }

        var color_RED         =      ThemeColor. RED                 .getColorPack().dark().asInt()
        var color_PINK        =      ThemeColor. PINK                  .getColorPack().dark().asInt()
        var color_PURPLE      =      ThemeColor. PURPLE                 .getColorPack().dark().asInt()
        var color_DEEP_PURPLE =      ThemeColor. DEEP_PURPLE                .getColorPack().dark().asInt()
        var color_INDIGO      =      ThemeColor. INDIGO                 .getColorPack().dark().asInt()
        var color_BLUE        =      ThemeColor. BLUE                  .getColorPack().dark().asInt()
        var color_LIGHT_BLUE  =      ThemeColor. LIGHT_BLUE                 .getColorPack().dark().asInt()
        var color_CYAN        =      ThemeColor. CYAN                  .getColorPack().dark().asInt()
        var color_TEAL        =      ThemeColor. TEAL                  .getColorPack().dark().asInt()
        var color_GREEN       =      ThemeColor. GREEN                  .getColorPack().dark().asInt()
        var color_LIGHT_GREEN =      ThemeColor. LIGHT_GREEN                .getColorPack().dark().asInt()
        var color_LIME        =      ThemeColor. LIME                  .getColorPack().dark().asInt()
        var color_YELLOW      =      ThemeColor. YELLOW                 .getColorPack().dark().asInt()
        var color_AMBER       =      ThemeColor. AMBER                  .getColorPack().dark().asInt()
        var color_ORANGE      =      ThemeColor. ORANGE                 .getColorPack().dark().asInt()
        var color_DEEP_ORANGE =      ThemeColor. DEEP_ORANGE                .getColorPack().dark().asInt()
        var color_BROWN       =      ThemeColor. BROWN                  .getColorPack().dark().asInt()
        var color_GREY        =      ThemeColor. GREY                  .getColorPack().dark().asInt()
        var color_BLUE_GREY   =      ThemeColor. BLUE_GREY                 .getColorPack().dark().asInt()
        var color_WHITE       =      ThemeColor. WHITE                  .getColorPack().dark().asInt()
        var color_BLACK       =      ThemeColor. BLACK                  .getColorPack().dark().asInt()

        /**
         * 获取资源对象
         */
        fun getSkinArray(): IntArray {
            val colors = intArrayOf(
                         color_RED
                        ,color_PINK
                        ,color_PURPLE
                        ,color_DEEP_PURPLE
                        ,color_INDIGO
                        ,color_BLUE
                        ,color_LIGHT_BLUE
                        ,color_CYAN
                        ,color_TEAL
                        ,color_GREEN
                        ,color_LIGHT_GREEN
                        ,color_LIME
                        ,color_YELLOW
                        ,color_AMBER
                        ,color_ORANGE
                        ,color_DEEP_ORANGE
                        ,color_BROWN
                        ,color_GREY
                        ,color_BLUE_GREY
//                        ,color_WHITE
                        ,color_BLACK

            )
            return  colors
        }

        fun getSkinThem(color:Int): ThemeColorInterface {
              var them=ThemeColor. BLACK
            when (color) {
              color_RED         ->them=ThemeColor. RED
              color_PINK        ->them=ThemeColor. PINK
              color_PURPLE      ->them=ThemeColor. PURPLE
              color_DEEP_PURPLE ->them=ThemeColor. DEEP_PURPLE
              color_INDIGO      ->them=ThemeColor. INDIGO
              color_BLUE        ->them=ThemeColor. BLUE
              color_LIGHT_BLUE  ->them=ThemeColor. LIGHT_BLUE
              color_CYAN        ->them=ThemeColor. CYAN
              color_TEAL        ->them=ThemeColor. TEAL
              color_GREEN       ->them=ThemeColor. GREEN
              color_LIGHT_GREEN ->them=ThemeColor. LIGHT_GREEN
              color_LIME        ->them=ThemeColor. LIME
              color_YELLOW      ->them=ThemeColor. YELLOW
              color_AMBER       ->them=ThemeColor. AMBER
              color_ORANGE      ->them=ThemeColor. ORANGE
              color_DEEP_ORANGE ->them=ThemeColor. DEEP_ORANGE
              color_BROWN       ->them=ThemeColor. BROWN
              color_GREY        ->them=ThemeColor. GREY
              color_BLUE_GREY   ->them=ThemeColor. BLUE_GREY
//              color_WHITE       ->them=ThemeColor. WHITE
              color_BLACK       ->them=ThemeColor. BLACK
            }
            return  them
        }

        /**
         * 获取资源对象
         */
        fun getResources(): Resources {
            return getContext().resources
        }

        /**
         * 获取资源文件字符串
         *
         * @param id
         * @return
         */
        fun getString(id: Int): String {
            return getResources().getString(id)
        }




    }


}