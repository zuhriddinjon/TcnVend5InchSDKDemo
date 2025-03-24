package com.tcn.sdk.lifthefansxdemo

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast

object TcnUtilityUI {
    private var toast: Toast? = null

    fun getsToastSign(context: Context?, text: String?) {
        try {
            val view = LayoutInflater.from(context).inflate(R.layout.ui_base_toast, null)
            if (toast == null) {
                toast = Toast(context)
                val msg = view.findViewById<View>(R.id.msg) as TextView
                msg.text = text
                toast!!.setGravity(Gravity.CENTER, 0, 0)
                toast!!.duration = Toast.LENGTH_SHORT
            } else {
                val msg = view.findViewById<View>(R.id.msg) as TextView
                msg.text = text
            }
            view.setBackgroundResource(R.drawable.ui_base_toaststyle)
            toast!!.view = view
            toast!!.show()
        } catch (e: Exception) {
        }
    }

    /**
     * 自定义toast的样式
     * @param context
     * @param text
     */
    fun getToast(context: Context?, text: String?) {
        try {
            val toast = Toast(context)
            val view = LayoutInflater.from(context).inflate(R.layout.ui_base_toast, null)
            val msg = view.findViewById<View>(R.id.msg) as TextView
            msg.text = text
            view.setBackgroundResource(R.drawable.ui_base_toaststyle)
            toast.view = view
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.duration = 500
            toast.show()
        } catch (e: Exception) {
        }
    }

    fun getToast(context: Context?, text: String?, textSize: Float) {
        try {
            val toast = Toast(context)
            val view = LayoutInflater.from(context).inflate(R.layout.ui_base_toast, null)
            val msg = view.findViewById<View>(R.id.msg) as TextView
            msg.text = text
            msg.textSize = textSize
            view.setBackgroundResource(R.drawable.ui_base_toaststyle)
            toast.view = view
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.duration = Toast.LENGTH_LONG
            toast.show()
        } catch (e: Exception) {
        }
    }

    fun getToast(context: Context?, text: String?, duration: Int): Toast {
        val toast = Toast(context)
        try {
            val view = LayoutInflater.from(context).inflate(R.layout.ui_base_toast, null)
            val msg = view.findViewById<View>(R.id.msg) as TextView
            msg.text = text
            toast.view = view
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.duration = duration
        } catch (e: Exception) {
        }
        return toast
    }

    fun getToastAndShow(context: Context?, text: String?, duration: Int): Toast {
        val toast = Toast(context)
        try {
            val view = LayoutInflater.from(context).inflate(R.layout.ui_base_toast, null)
            val msg = view.findViewById<View>(R.id.msg) as TextView
            msg.text = text
            toast.view = view
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.duration = duration
            toast.show()
        } catch (e: Exception) {
        }
        return toast
    }

    private var lastClickTime: Long = 0
    val isFastClick: Boolean
        get() {
            val time = System.currentTimeMillis()
            val timeD = time - lastClickTime
            if (0 < timeD && timeD < 800) {
                return true
            }
            lastClickTime = time
            return false
        }
}
