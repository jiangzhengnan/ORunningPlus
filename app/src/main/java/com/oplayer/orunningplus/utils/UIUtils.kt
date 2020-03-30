package com.oplayer.common.utils

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.oplayer.orunningplus.OSportApplciation
import com.oplayer.orunningplus.R
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.*


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
//        fun getSkinColor(): Int {
//            return Colorful().getPrimaryColor().getColorPack().dark().asInt()
//        }

//        var color_RED         =      ThemeColor. RED                 .getColorPack().dark().asInt()
//        var color_PINK        =      ThemeColor. PINK                  .getColorPack().dark().asInt()
//        var color_PURPLE      =      ThemeColor. PURPLE                 .getColorPack().dark().asInt()
//        var color_DEEP_PURPLE =      ThemeColor. DEEP_PURPLE                .getColorPack().dark().asInt()
//        var color_INDIGO      =      ThemeColor. INDIGO                 .getColorPack().dark().asInt()
//        var color_BLUE        =      ThemeColor. BLUE                  .getColorPack().dark().asInt()
//        var color_LIGHT_BLUE  =      ThemeColor. LIGHT_BLUE                 .getColorPack().dark().asInt()
//        var color_CYAN        =      ThemeColor. CYAN                  .getColorPack().dark().asInt()
//        var color_TEAL        =      ThemeColor. TEAL                  .getColorPack().dark().asInt()
//        var color_GREEN       =      ThemeColor. GREEN                  .getColorPack().dark().asInt()
//        var color_LIGHT_GREEN =      ThemeColor. LIGHT_GREEN                .getColorPack().dark().asInt()
//        var color_LIME        =      ThemeColor. LIME                  .getColorPack().dark().asInt()
//        var color_YELLOW      =      ThemeColor. YELLOW                 .getColorPack().dark().asInt()
//        var color_AMBER       =      ThemeColor. AMBER                  .getColorPack().dark().asInt()
//        var color_ORANGE      =      ThemeColor. ORANGE                 .getColorPack().dark().asInt()
//        var color_DEEP_ORANGE =      ThemeColor. DEEP_ORANGE                .getColorPack().dark().asInt()
//        var color_BROWN       =      ThemeColor. BROWN                  .getColorPack().dark().asInt()
//        var color_GREY        =      ThemeColor. GREY                  .getColorPack().dark().asInt()
//        var color_BLUE_GREY   =      ThemeColor. BLUE_GREY                 .getColorPack().dark().asInt()
//        var color_WHITE       =      ThemeColor. WHITE                  .getColorPack().dark().asInt()
//        var color_BLACK       =      ThemeColor. BLACK                  .getColorPack().dark().asInt()

        /**
         * 获取资源对象
         */
//        fun getSkinArray(): IntArray {
//            val colors = intArrayOf(
//                         color_RED
//                        ,color_PINK
//                        ,color_PURPLE
//                        ,color_DEEP_PURPLE
//                        ,color_INDIGO
//                        ,color_BLUE
//                        ,color_LIGHT_BLUE
//                        ,color_CYAN
//                        ,color_TEAL
//                        ,color_GREEN
//                        ,color_LIGHT_GREEN
//                        ,color_LIME
//                        ,color_YELLOW
//                        ,color_AMBER
//                        ,color_ORANGE
//                        ,color_DEEP_ORANGE
//                        ,color_BROWN
//                        ,color_GREY
//                        ,color_BLUE_GREY
////                        ,color_WHITE
//                        ,color_BLACK
//
//            )
//            return  colors
//        }

//        fun getSkinThem(color:Int): ThemeColorInterface {
//              var them=ThemeColor. BLACK
//            when (color) {
//              color_RED         ->them=ThemeColor. RED
//              color_PINK        ->them=ThemeColor. PINK
//              color_PURPLE      ->them=ThemeColor. PURPLE
//              color_DEEP_PURPLE ->them=ThemeColor. DEEP_PURPLE
//              color_INDIGO      ->them=ThemeColor. INDIGO
//              color_BLUE        ->them=ThemeColor. BLUE
//              color_LIGHT_BLUE  ->them=ThemeColor. LIGHT_BLUE
//              color_CYAN        ->them=ThemeColor. CYAN
//              color_TEAL        ->them=ThemeColor. TEAL
//              color_GREEN       ->them=ThemeColor. GREEN
//              color_LIGHT_GREEN ->them=ThemeColor. LIGHT_GREEN
//              color_LIME        ->them=ThemeColor. LIME
//              color_YELLOW      ->them=ThemeColor. YELLOW
//              color_AMBER       ->them=ThemeColor. AMBER
//              color_ORANGE      ->them=ThemeColor. ORANGE
//              color_DEEP_ORANGE ->them=ThemeColor. DEEP_ORANGE
//              color_BROWN       ->them=ThemeColor. BROWN
//              color_GREY        ->them=ThemeColor. GREY
//              color_BLUE_GREY   ->them=ThemeColor. BLUE_GREY
////              color_WHITE       ->them=ThemeColor. WHITE
//              color_BLACK       ->them=ThemeColor. BLACK
//            }
//            return  them
//        }


        /**
         * 获取资源对象
         */
        fun getResources(): Resources {
            return getContext().resources
        }


        /**
         * 获取屏幕宽度
         *
         * @return
         */
        fun getAndroiodScreenProperty(): Int {
            val wm =
                getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getMetrics(dm)
            val width = dm.widthPixels // 屏幕宽度（像素）
            val height = dm.heightPixels // 屏幕高度（像素）
            val density = dm.density // 屏幕密度（0.75 / 1.0 / 1.5）
            val densityDpi = dm.densityDpi // 屏幕密度dpi（120 / 160 / 240）
            // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
            val screenWidth = (width / density).toInt() // 屏幕宽度(dp)
            val screenHeight = (height / density).toInt() // 屏幕高度(dp)
            return width
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

        /**
         * 获取颜色
         *
         * @param context 上下文
         * @param resId   资源ID
         */
        fun getColor( @ColorRes resId: Int): Int {
            return ContextCompat.getColor(getContext(), resId)
        }


        /**
         * 获取Drawable
         *
         * @param context 上下文
         * @param resId   资源ID
         */
        fun getDrawable(context: Context?, @DrawableRes resId: Int): Drawable? {
            return ContextCompat.getDrawable(context!!, resId)
        }

        /**
         * 获取尺寸资源
         *
         * @param context 上下文
         * @param resId   资源ID
         * @return px
         */
        fun getDimen(context: Context?, @DimenRes resId: Int): Float {
            return getResources().getDimension(resId)
        }



        fun activityShot(activity: Activity): Bitmap { /*获取windows中最顶层的view*/
            val view: View = activity.window.decorView
            //允许当前窗口保存缓存信息
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            //获取状态栏高度
            val rect: Rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val statusBarHeight: Int = rect.top
            val windowManager: WindowManager = activity.getWindowManager()
            //获取屏幕宽和高
            val outMetrics: DisplayMetrics = DisplayMetrics()
            windowManager.getDefaultDisplay().getMetrics(outMetrics)
            val width: Int = outMetrics.widthPixels
            val height: Int = outMetrics.heightPixels
            //去掉状态栏
            val bitmap: Bitmap = Bitmap.createBitmap(
                view.getDrawingCache(), 0, statusBarHeight, width,
                height - statusBarHeight
            )
            //销毁缓存信息
            view.destroyDrawingCache()
            view.setDrawingCacheEnabled(false)
            return bitmap
        }

        fun saveBitmap(bitmap: Bitmap): File { // 首先保存图片
            val appDir: File =
                File(Environment.getExternalStorageDirectory(), "image")
            if (!appDir.exists()) {
                appDir.mkdir()
            }
            val fileName: String = System.currentTimeMillis().toString() + ".png"
            val file: File = File(appDir, fileName)
            try {
                val fos: FileOutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
            } catch (e: Exception) {
                e.printStackTrace()
                Slog.d("保存出错  $e")
            }
            // 把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(
                    getContext().getContentResolver(),
                    file.getAbsolutePath(),
                    fileName,
                    null
                )
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Slog.d("插入到系统图库  $e")
            }
            // 通知图库更新
            getContext().sendBroadcast(
                Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.parse("file://" + "/sdcard/namecard/")
                )
            )
            return file
        }


        fun originalShareImage(activity: Activity,context: Context) {
            val bitmap: Bitmap = activityShot(activity)
            val file: File = saveBitmap(bitmap)
            val share_intent: Intent = Intent()
            val imageUris: ArrayList<Uri?> =
                ArrayList<Uri?>()
            var uri: Uri?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val imageContentUri: Uri? = getImageContentUri(activity, file)
                imageUris.add(imageContentUri)
            } else {
                imageUris.add(Uri.fromFile(file))
            }
            share_intent.setAction(Intent.ACTION_SEND) //设置分享行为
            share_intent.setType("image/*") //设置分享内容的类型
            share_intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            share_intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)

//            activity.startActivity(Intent.createChooser(share_intent, activity.getString(R.string.today_str_share)))
            context.startActivity(Intent.createChooser(share_intent, activity.getString(R.string.today_str_share)))

        }


        /**
         *
         * @param context
         * @param imageFile
         * @return content Uri
         */
        fun getImageContentUri(
            context: Context,
            imageFile: File
        ): Uri? {
            val filePath: String = imageFile.getAbsolutePath()
            val cursor: Cursor? = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf<String>(MediaStore.Images.Media._ID),
                MediaStore.Images.Media.DATA + "=? ",
                arrayOf<String>(filePath),
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val id: Int =
                    cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
                val baseUri: Uri =
                    Uri.parse("content://media/external/images/media")
                return Uri.withAppendedPath(baseUri, "" + id)
            } else {
                if (imageFile.exists()) {
                    val values: ContentValues = ContentValues()
                    values.put(MediaStore.Images.Media.DATA, filePath)
                    return context.getContentResolver()
                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                } else {
                    return null
                }
            }
        }

         fun createColorStateList(
            normal: Int,
            pressed: Int,
            focused: Int,
            unable: Int
        ): ColorStateList? {
            val colors = intArrayOf(pressed, focused, normal, focused, unable, normal)
            val states = arrayOfNulls<IntArray>(6)
            states[0] = intArrayOf(android.R.attr.state_pressed, android.R.attr.state_enabled)
            states[1] = intArrayOf(android.R.attr.state_enabled, android.R.attr.state_focused)
            states[2] = intArrayOf(android.R.attr.state_enabled)
            states[3] = intArrayOf(android.R.attr.state_focused)
            states[4] = intArrayOf(android.R.attr.state_window_focused)
            states[5] = intArrayOf()
            return ColorStateList(states, colors)
        }



    }




}