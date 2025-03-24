package com.tcn.sdk.lifthefansxdemo

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.tcn.sdk.lifthefansxdemo.MySlipSwitch.OnSwitchListener

/**
 * Created by Administrator on 2016/8/26.
 */
class ButtonSwitch : RelativeLayout {
    private var btn_name: TextView? = null
    private var btn_switch: MySlipSwitch? = null


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
        inflate(context, R.layout.ui_base_button_switch_layout, this)
        btn_name = findViewById<View>(R.id.btn_name) as TextView
        btn_switch = findViewById<View>(R.id.btn_switch) as MySlipSwitch
        btn_switch!!.setImageResource(
            R.mipmap.switch_bkg_switch,
            R.mipmap.switch_bkg_switch, R.mipmap.switch_btn_slip
        )
        btn_switch!!.setOnSwitchListener(m_SwitchListener)
    }

    fun setTextSize(size: Int) {
        if (btn_name != null) {
            btn_name!!.textSize = size.toFloat()
        }
    }

    fun setButtonName(resid: Int) {
        if (btn_name != null) {
            btn_name!!.setText(resid)
        }
    }

    fun setButtonName(text: String?) {
        if (btn_name != null) {
            btn_name!!.text = text
        }
    }

    fun setSwitchState(switchState: Boolean) {
        if (btn_switch != null) {
            btn_switch!!.switchState = switchState
        }
    }

    private var m_ButtonListener: ButtonListener? = null

    fun setButtonListener(listener: ButtonListener?) {
        m_ButtonListener = listener
    }

    fun removeButtonListener() {
        if (btn_switch != null) {
            btn_switch!!.setOnSwitchListener(null)
        }
        m_SwitchListener = null
        m_ButtonListener = null
    }

    interface ButtonListener {
        fun onSwitched(v: View?, isSwitchOn: Boolean)
    }

    private var m_SwitchListener: SwitchListener? = SwitchListener()

    private inner class SwitchListener : OnSwitchListener {
        override fun onSwitched(isSwitchOn: Boolean) {
            if (m_ButtonListener != null) {
                m_ButtonListener!!.onSwitched(this@ButtonSwitch, isSwitchOn)
            }
        }
    }
}
