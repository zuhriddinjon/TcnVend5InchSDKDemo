package com.tcn.sdk.lifthefansxdemo

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class DialogInput(context: Context) : Dialog(context, R.style.ui_base_Dialog_bocop) {
    private var m_iBtnType = BUTTON_TYPE_INPUT

    var buttonFlag: Int = 0

    private var m_Context: Context? = null
    private var m_EditText: EditText? = null
    private var m_EditText_end: EditText? = null
    private var m_Text: TextView? = null
    private var dialog_start_text: TextView? = null
    private var dialog_end_text: TextView? = null
    private var dialog_input_cancel_button: Button? = null
    private var dialog_input_sure_button: Button? = null

    override fun show() {
        super.show()
    }

    override fun dismiss() {
        super.dismiss()
    }


    private fun init() {
        val contentView = View.inflate(m_Context, R.layout.ui_base_dialog_input, null)
        setContentView(contentView)
        setCancelable(false)
        m_Text = findViewById<View>(R.id.dialog_input_text) as TextView
        m_Text!!.textSize = 20f
        m_EditText = findViewById<View>(R.id.dialog_input_editText) as EditText
        m_EditText_end = findViewById<View>(R.id.dialog_input_editText_end) as EditText

        dialog_start_text = findViewById<View>(R.id.dialog_start_text) as TextView
        dialog_end_text = findViewById<View>(R.id.dialog_end_text) as TextView

        dialog_input_cancel_button = findViewById<View>(R.id.dialog_input_cancel_button) as Button
        dialog_input_cancel_button!!.setOnClickListener(m_ClickListener)
        dialog_input_cancel_button!!.textSize = 20f
        dialog_input_sure_button = findViewById<View>(R.id.dialog_input_sure_button) as Button
        dialog_input_sure_button!!.setOnClickListener(m_ClickListener)
        dialog_input_sure_button!!.textSize = 20f
        window!!.setWindowAnimations(Resources.getAnimResourceID(R.anim.ui_base_alpha_in))
    }

    fun deInit() {
        setOnCancelListener(null)
        setOnShowListener(null)
        setOnDismissListener(null)
        if (dialog_input_cancel_button != null) {
            dialog_input_cancel_button!!.setOnClickListener(null)
            dialog_input_cancel_button = null
        }
        if (dialog_input_sure_button != null) {
            dialog_input_sure_button!!.setOnClickListener(null)
            dialog_input_sure_button = null
        }
        m_Context = null
        m_EditText = null
        m_EditText_end = null
        dialog_start_text = null
        dialog_end_text = null
        m_Text = null
    }

    fun setButtonTiTle(title: String?) {
        if (m_Text != null) {
            m_Text!!.text = title
        }
    }

    fun setButtonTiTleColor(color: Int) {
        if (m_Text != null) {
            m_Text!!.setTextColor(color)
        }
    }

    fun setButtonTiTle(resId: Int) {
        if (m_Text != null) {
            m_Text!!.setText(resId)
        }
    }

    fun setButtonTiTleSize(size: Float) {
        if (m_Text != null) {
            m_Text!!.textSize = size
        }
    }

    fun setButtonCancelVisiable(visiable: Boolean) {
        if (visiable) {
            if (dialog_input_cancel_button != null) {
                dialog_input_cancel_button!!.visibility = View.VISIBLE
            }
        } else {
            if (dialog_input_cancel_button != null) {
                dialog_input_cancel_button!!.visibility = View.INVISIBLE
            }
        }
    }

    fun setButtonTextSize(size: Float) {
        if (dialog_input_cancel_button != null) {
            dialog_input_cancel_button!!.textSize = size
        }
        if (dialog_input_sure_button != null) {
            dialog_input_sure_button!!.textSize = size
        }
    }

    fun setButtonSureText(text: String?) {
        if (dialog_input_sure_button != null) {
            dialog_input_sure_button!!.text = text
        }
    }

    fun setButtonCancelText(text: String?) {
        if (dialog_input_cancel_button != null) {
            dialog_input_cancel_button!!.text = text
        }
    }

    var buttonType: Int
        get() = m_iBtnType
        set(type) {
            m_iBtnType = type
            if (type == BUTTON_TYPE_INPUT) {
                if (dialog_start_text != null) {
                    dialog_start_text!!.visibility = View.VISIBLE
                }
                if (dialog_end_text != null) {
                    dialog_end_text!!.visibility = View.VISIBLE
                }
                if (m_EditText != null) {
                    m_EditText!!.visibility = View.VISIBLE
                }
                if (m_EditText_end != null) {
                    m_EditText_end!!.visibility = View.VISIBLE
                }
            } else if (type == BUTTON_TYPE_NO_INPUT) {
                if (dialog_start_text != null) {
                    dialog_start_text!!.visibility = View.GONE
                }
                if (dialog_end_text != null) {
                    dialog_end_text!!.visibility = View.GONE
                }
                if (m_EditText != null) {
                    m_EditText!!.visibility = View.GONE
                }
                if (m_EditText_end != null) {
                    m_EditText_end!!.visibility = View.GONE
                }
            } else if (type == BUTTON_TYPE_INPUT_ONE) {
                if (dialog_start_text != null) {
                    dialog_start_text!!.visibility = View.GONE
                }
                if (dialog_end_text != null) {
                    dialog_end_text!!.visibility = View.GONE
                }
                if (m_EditText != null) {
                    m_EditText!!.visibility = View.VISIBLE
                }
                if (m_EditText_end != null) {
                    m_EditText_end!!.visibility = View.GONE
                }
            } else {
            }
        }

    fun setButtonInputType(type: Int) {
        if (m_EditText != null) {
            m_EditText!!.inputType = type
        }
        if (m_EditText_end != null) {
            m_EditText_end!!.inputType = type
        }
    }

    fun setButtonError(error: String?) {
        if (m_EditText != null) {
            m_EditText!!.error = error
        }
    }

    fun setButtonEndError(error: String?) {
        if (m_EditText_end != null) {
            m_EditText_end!!.error = error
        }
    }

    fun setButtonText(text: String?) {
        if (m_EditText != null) {
            m_EditText!!.setText(text)
        }
    }

    fun setButtonEndText(text: String?) {
        if (m_EditText_end != null) {
            m_EditText_end!!.setText(text)
        }
    }

    fun setButtonText(resId: Int) {
        if (m_EditText != null) {
            m_EditText!!.setText(resId)
        }
    }

    fun setButtonEndText(resId: Int) {
        if (m_EditText_end != null) {
            m_EditText_end!!.setText(resId)
        }
    }


    private val m_ClickListener: ClickListener = ClickListener()

    private inner class ClickListener : View.OnClickListener {
        override fun onClick(v: View?) {
            if (null == v) {
                return
            }
            val id = v.id
            var mData: String? = null
            var mDataSecond: String? = null

            if ((BUTTON_TYPE_INPUT == m_iBtnType) || (BUTTON_TYPE_INPUT_ONE == m_iBtnType)) {
                if (null == m_EditText) {
                    dismiss()
                    return
                }
                mData = m_EditText!!.text.toString()
                if (m_EditText_end != null) {
                    mDataSecond = m_EditText_end!!.text.toString()
                }
                if (R.id.dialog_input_cancel_button == id) {
                    if (m_ButtonListener != null) {
                        m_ButtonListener!!.onClick(BUTTON_ID_CANCEL, mData, mDataSecond)
                    }
                } else if (R.id.dialog_input_sure_button == id) {
                    if (m_ButtonListener != null) {
                        m_ButtonListener!!.onClick(BUTTON_ID_SURE, mData, mDataSecond)
                    }
                }
            } else {
                if (R.id.dialog_input_cancel_button == id) {
                    if (m_ButtonListener != null) {
                        m_ButtonListener!!.onClick(BUTTON_ID_CANCEL, mData, mDataSecond)
                    }
                } else if (R.id.dialog_input_sure_button == id) {
                    if (m_ButtonListener != null) {
                        m_ButtonListener!!.onClick(BUTTON_ID_SURE, mData, mDataSecond)
                    }
                }
            }
        }
    }

    fun setButtonListener(listener: ButtonListener?) {
        m_ButtonListener = listener
    }

    private var m_ButtonListener: ButtonListener? = null

    init {
        m_Context = context
        init()
    }

    interface ButtonListener {
        fun onClick(buttonId: Int, firstData: String?, secondData: String?)
    }

    companion object {
        const val BUTTON_ID_CANCEL: Int = 1
        const val BUTTON_ID_SURE: Int = 2

        const val BUTTON_TYPE_INPUT: Int = 1
        const val BUTTON_TYPE_NO_INPUT: Int = 2
        const val BUTTON_TYPE_INPUT_ONE: Int = 3
    }
}
