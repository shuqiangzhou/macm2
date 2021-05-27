package com.mail.myapplication.ui.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.mail.comm.app.AppManager
import com.zhy.autolayout.utils.AutoUtils
import org.xutils.common.util.LogUtil
import org.xutils.x
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MyUtils {

    companion object {

        fun getVersionName(context: Context): String {
            try {
                val packageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
                return packageInfo.versionName
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }

        fun browerUpdate(url: String) {
            val intent = Intent()
            intent.action = "android.intent.action.VIEW"
            val content_url = Uri.parse(url)
            intent.data = content_url
            AppManager.getInstance().topActivity.startActivity(intent)
        }

        fun getVersionCode(context: Context): Int {
            try {
                val packageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
                return packageInfo.versionCode
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return 0
        }


        fun fileIsExists(filename: String?): Boolean {
            val s = Environment.getExternalStorageDirectory().absolutePath + "/分享赚/" + filename + ".mp4"
            return File(s).exists()
        }

        fun getPath():String= Environment.getExternalStorageDirectory().absolutePath + "/分享赚/"  + "icon.zip"

        fun getVideoPath(tag: String?): String = Environment.getExternalStorageDirectory().absolutePath + "/分享赚/" + tag + ".mp4"

        fun getCurrentYear():Int= Integer.parseInt( SimpleDateFormat("yyyy").format(Date()))

        fun getCurrentMonth():Int= Integer.parseInt( SimpleDateFormat("MM").format(Date()))

        fun getCurrentDay():Int= Integer.parseInt( SimpleDateFormat("dd").format(Date()))

        fun getMonthOfDays(year:Int,month:Int):Int{
            val a = Calendar.getInstance()
            a.set(Calendar.YEAR, year)
            a.set(Calendar.MONTH, month - 1)
            a.set(Calendar.DATE, 1)//把日期设置为当月第一天
            a.roll(Calendar.DATE, -1)//日期回滚一天，也就是最后一天
            return a.get(Calendar.DATE)
        }

        fun getFirstDayOfMonth(year:Int,month:Int):Int{
            val a = Calendar.getInstance()
            a.set(Calendar.DAY_OF_MONTH, 1)
            a.set(Calendar.YEAR, year)
            a.set(Calendar.MONTH, month-1)
            return a.get(Calendar.DAY_OF_WEEK)
        }

        fun getTimestampDay(year:Int,month:Int,day:Int,hour:Int):Long{
            var cal = Calendar.getInstance()
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH,month-1)
            cal.set(Calendar.DATE, day)//把日期设置为当月第一天
            cal.set(Calendar.HOUR_OF_DAY,hour)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.MILLISECOND, 0)
            return cal.timeInMillis/1000
        }

        /**
         * 根据毫秒返回时分秒
         */
        fun getFormatHMS(time: Int): String {
            var s = (time % 60)//秒
            var m = (time / 60)//分
            var h= (time/3600)//秒
            return String.format("%02d:%02d", m, s)
        }
    }

}





