package com.tcn.sdk.lifthefansxdemo

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView

/**
 * Created by Administrator on 2016/8/26.
 */
class Titlebar : RelativeLayout {
    private var title_bar_back: Button? = null
    private var title_bar_name: TextView? = null
    private var title_bar_exit: Button? = null

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    private fun initView(context: Context) {
        inflate(context, R.layout.ui_base_tcn_title_bar, this)
        title_bar_back = findViewById<View>(R.id.title_bar_back) as Button
        title_bar_back!!.setOnClickListener(m_ClickListener)
        title_bar_name = findViewById<View>(R.id.title_bar_name) as TextView
        title_bar_exit = findViewById<View>(R.id.title_bar_exit) as Button
        title_bar_exit!!.setOnClickListener(m_ClickListener)
    }

    fun setButtonName(resid: Int) {
        if (title_bar_name != null) {
            title_bar_name!!.setText(resid)
        }
    }

    fun setButtonName(data: String?) {
        if (title_bar_name != null) {
            title_bar_name!!.text = data
        }
    }

    fun setButtonSecondName(resid: Int) {
        if (title_bar_exit != null) {
            title_bar_exit!!.setText(resid)
        }
    }

    fun setButtonType(type: Int) {
        if (BUTTON_TYPE_BACK_AND_EXIT == type) {
            title_bar_back!!.visibility = VISIBLE
            title_bar_exit!!.visibility = VISIBLE
        } else if (BUTTON_TYPE_BACK == type) {
            title_bar_back!!.visibility = VISIBLE
            title_bar_exit!!.visibility = INVISIBLE
        } else {
        }
    }

    fun setTitleBarListener(listener: TitleBarListener?) {
        m_TitleBarListener = listener
    }

    private var m_TitleBarListener: TitleBarListener? = null

    interface TitleBarListener {
        fun onClick(v: View?, buttonId: Int)
    }

    fun removeButtonListener() {
        if (title_bar_back != null) {
            title_bar_back!!.text = null
            title_bar_back!!.setOnClickListener(null)
            title_bar_back = null
        }
        if (title_bar_exit != null) {
            title_bar_exit!!.text = null
            title_bar_exit!!.setOnClickListener(null)
            title_bar_exit = null
        }
        title_bar_name?.setText("")
        title_bar_name = null
        m_ClickListener = null
        m_TitleBarListener = null
    }

    private var m_ClickListener: ClickListener? = ClickListener()

    private inner class ClickListener : OnClickListener {
        override fun onClick(v: View?) {
            if (v == null) {
                return
            }
            val id = v.id
            if (R.id.title_bar_back == id) {
                if (m_TitleBarListener != null) {
                    m_TitleBarListener!!.onClick(this@Titlebar, BUTTON_ID_BACK)
                }
            } else if (R.id.title_bar_exit == id) {
                if (m_TitleBarListener != null) {
                    m_TitleBarListener!!.onClick(this@Titlebar, BUTTON_ID_EXIT)
                }
            } else {
            }
        }
    }

    companion object {
        const val BUTTON_TYPE_BACK_AND_EXIT: Int = 1
        const val BUTTON_TYPE_BACK: Int = 2

        const val BUTTON_ID_BACK: Int = 1
        const val BUTTON_ID_EXIT: Int = 2
    }
}
