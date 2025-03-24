package com.tcn.sdk.lifthefansxdemo

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

/**
 * Created by Administrator on 2017/10/23.
 */
class ButtonEditSelectD : RelativeLayout {
    private val m_btn_type = BUTTON_TYPE_EDIT
    private var btn_edit_linearLayout: LinearLayout? = null
    private var btn_edit_linearLayout_2: LinearLayout? = null
    private var btn_edit_name: TextView? = null
    private var btn_edit_query_btn: Button? = null
    var buttonEdit: EditText? = null
        private set
    var buttonEditSecond: EditText? = null
        private set
    private var btn_edit_select_line: TextView? = null
    private var btn_edit_select_line_2: TextView? = null
    private var btn_edit_display: TextView? = null
    private var btn_edit_select_btn: Button? = null
    private var btn_edit_select_btn_2: Button? = null
    private var btn_edit_edit_input: EditText? = null


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
        inflate(context, R.layout.ui_base_button_edit_seletct_d_layout, this)
        btn_edit_linearLayout = findViewById<View>(R.id.btn_edit_linearLayout) as LinearLayout
        btn_edit_linearLayout_2 = findViewById<View>(R.id.btn_edit_linearLayout_2) as LinearLayout
        btn_edit_name = findViewById<View>(R.id.btn_edit_name) as TextView
        btn_edit_query_btn = findViewById<View>(R.id.btn_edit_query_btn) as Button
        btn_edit_query_btn!!.setOnClickListener(m_ClickListener)

        buttonEdit = findViewById<View>(R.id.btn_edit_edit_select) as EditText
        buttonEdit!!.setOnClickListener(m_ClickListener)

        buttonEditSecond = findViewById<View>(R.id.btn_edit_edit_select_2) as EditText
        buttonEditSecond!!.setOnClickListener(m_ClickListener)

        btn_edit_select_line = findViewById<View>(R.id.btn_edit_select_line) as TextView
        btn_edit_select_line_2 = findViewById<View>(R.id.btn_edit_select_line_2) as TextView

        btn_edit_select_btn = findViewById<View>(R.id.btn_edit_select_btn) as Button
        btn_edit_select_btn!!.setOnClickListener(m_ClickListener)

        btn_edit_select_btn_2 = findViewById<View>(R.id.btn_edit_select_btn_2) as Button
        btn_edit_select_btn_2!!.setOnClickListener(m_ClickListener)

        btn_edit_edit_input = findViewById<View>(R.id.btn_edit_edit_input) as EditText

        btn_edit_display = findViewById<View>(R.id.btn_edit_display) as TextView
        // LayoutInflater.from(context).inflate(R.layout.button_edit_layout, this, true);
        // View contentView = View.inflate(context, R.layout.button_edit_layout, null);
        // addView(contentView);
    }

    fun setLayouRatio(ratioEdit: Float, ratio: Float) {
        val mLayoutParamsoEdit = buttonEdit!!.layoutParams as LinearLayout.LayoutParams
        mLayoutParamsoEdit.width = 0
        mLayoutParamsoEdit.weight = ratioEdit

        buttonEdit!!.layoutParams = mLayoutParamsoEdit

        val mLayoutParams = btn_edit_select_btn!!.layoutParams as LinearLayout.LayoutParams
        mLayoutParams.width = 0
        mLayoutParams.weight = ratio
        btn_edit_select_btn!!.layoutParams = mLayoutParams
    }

    fun setButtonType(type: Int) {
        btn_edit_query_btn!!.setBackgroundResource(R.drawable.ui_base_btn_selector)
        if (BUTTON_TYPE_SELECTTWO_INPUT_QUERY == type) {
            btn_edit_linearLayout!!.visibility = VISIBLE
            btn_edit_linearLayout_2!!.visibility = VISIBLE
            btn_edit_edit_input!!.visibility = VISIBLE
        } else if (BUTTON_TYPE_QUERY == type) {
            btn_edit_linearLayout!!.visibility = GONE
            btn_edit_linearLayout_2!!.visibility = GONE
            btn_edit_edit_input!!.visibility = GONE
        } else if (BUTTON_TYPE_EDIT_QUERY == type) {
            btn_edit_linearLayout!!.visibility = GONE
            btn_edit_linearLayout_2!!.visibility = GONE
            btn_edit_edit_input!!.visibility = VISIBLE
        } else if (BUTTON_TYPE_SELECT_QUERY == type) {
            buttonEdit!!.isEnabled = false
            btn_edit_linearLayout_2!!.visibility = GONE
            btn_edit_edit_input!!.visibility = GONE
        } else if (BUTTON_TYPE_SELECT_SECOND_QUERY == type) {
            buttonEditSecond!!.isEnabled = false
            btn_edit_linearLayout!!.visibility = GONE
            btn_edit_edit_input!!.visibility = GONE
        } else if (BUTTON_TYPE_SELECTTWO_QUERY == type) {
            buttonEdit!!.isEnabled = false
            buttonEditSecond!!.isEnabled = false
            btn_edit_edit_input!!.visibility = GONE
        } else if (BUTTON_TYPE_SELECT_INPUT_QUERY == type) {
            buttonEdit!!.isEnabled = false
            btn_edit_linearLayout_2!!.visibility = GONE
            btn_edit_edit_input!!.visibility = VISIBLE
        } else if (BUTTON_TYPE_SELECT_SECOND_INPUT_QUERY == type) {
            buttonEditSecond!!.isEnabled = false
            btn_edit_linearLayout!!.visibility = GONE
            btn_edit_edit_input!!.visibility = VISIBLE
        } else if (BUTTON_TYPE_EDIT == type) {
            btn_edit_linearLayout!!.visibility = GONE
            btn_edit_linearLayout_2!!.visibility = GONE
        } else if (BUTTON_TYPE_SELECT == type) {
            buttonEdit!!.isEnabled = false
            btn_edit_linearLayout_2!!.visibility = GONE
            btn_edit_edit_input!!.visibility = GONE
            btn_edit_query_btn!!.visibility = GONE
            btn_edit_display!!.visibility = GONE
        } else {
        }
    }

    fun setButtonQueryText(resid: Int) {
        if (btn_edit_query_btn != null) {
            btn_edit_query_btn!!.setText(resid)
        }
    }

    fun setButtonQueryText(text: String?) {
        if (btn_edit_query_btn != null) {
            btn_edit_query_btn!!.text = text
        }
    }

    fun setButtonQueryTextColor(color: String?) {
        if (btn_edit_query_btn != null) {
            btn_edit_query_btn!!.setTextColor(Color.parseColor(color))
        }
    }

    fun setButtonQueryTextSize(size: Int) {
        if (btn_edit_query_btn != null) {
            btn_edit_query_btn!!.textSize = size.toFloat()
        }
    }

    fun setButtonInputTextSize(size: Int) {
        if (btn_edit_edit_input != null) {
            btn_edit_edit_input!!.textSize = size.toFloat()
        }
    }

    fun setButtonSelectText(resid: Int) {
        if (btn_edit_select_btn != null) {
            btn_edit_select_btn!!.setText(resid)
        }
    }

    fun setButtonSelectTextSecond(resid: Int) {
        if (btn_edit_select_btn_2 != null) {
            btn_edit_select_btn_2!!.setText(resid)
        }
    }


    fun setButtonSelectText(text: String?) {
        if (btn_edit_select_btn != null) {
            btn_edit_select_btn!!.text = text
        }
    }

    fun setButtonSelectTextSecond(text: String?) {
        if (btn_edit_select_btn_2 != null) {
            btn_edit_select_btn_2!!.text = text
        }
    }

    fun setButtonSelectTextColor(color: String?) {
        if (btn_edit_select_btn != null) {
            btn_edit_select_btn!!.setTextColor(Color.parseColor(color))
        }
    }

    fun setButtonSelectTextColorSecond(color: String?) {
        if (btn_edit_select_btn_2 != null) {
            btn_edit_select_btn_2!!.setTextColor(Color.parseColor(color))
        }
    }

    fun setButtonDisplayVisibility(visibility: Int) {
        if (btn_edit_display != null) {
            btn_edit_display!!.visibility = visibility
        }
    }

    fun setButtonDisplayText(resid: Int) {
        if (btn_edit_display != null) {
            btn_edit_display!!.setText(resid)
        }
    }

    fun setButtonDisplayText(text: String?) {
        if (btn_edit_display != null) {
            btn_edit_display!!.text = text
        }
    }

    fun setButtonDisplayTextColor(color: String?) {
        if (btn_edit_display != null) {
            btn_edit_display!!.setTextColor(Color.parseColor(color))
        }
    }

    fun setButtonDisplayTextSize(size: Int) {
        if (btn_edit_display != null) {
            btn_edit_display!!.textSize = size.toFloat()
        }
    }

    fun setButtonNameTextColor(color: Int) {
        if (btn_edit_name != null) {
            btn_edit_name!!.setTextColor(color)
        }
    }

    fun setButtonNameTextSize(size: Int) {
        if (btn_edit_name != null) {
            btn_edit_name!!.textSize = size.toFloat()
        }
    }

    fun setButtonName(resid: Int) {
        if (btn_edit_name != null) {
            btn_edit_name!!.setText(resid)
        }
    }

    fun setButtonName(text: String?) {
        if (btn_edit_name != null) {
            btn_edit_name!!.text = text
        }
    }

    fun setButtonEditTextSize(size: Int) {
        if (buttonEdit != null) {
            buttonEdit!!.textSize = size.toFloat()
        }
    }

    fun setButtonEditTextSizeSecond(size: Int) {
        if (buttonEditSecond != null) {
            buttonEditSecond!!.textSize = size.toFloat()
        }
    }

    /*
    //输入类型为没有指定明确的类型的特殊内容类型
    editText.setInputType(InputType.TYPE_NULL);

    //输入类型为普通文本
    editText.setInputType(InputType.TYPE_CLASS_TEXT);

    //输入类型为数字文本
    editText.setInputType(InputType.TYPE_CLASS_NUMBER);

    //输入类型为电话号码
    editText.setInputType(InputType.TYPE_CLASS_PHONE);

    //输入类型为日期和时间
    editText.setInputType(InputType.TYPE_CLASS_DATETIME);
    ....
     */
    fun setInputType(type: Int) {
        if (buttonEdit != null) {
            buttonEdit!!.inputType = type
        }
    }

    fun setInputTypeSecond(type: Int) {
        if (buttonEditSecond != null) {
            buttonEditSecond!!.inputType = type
        }
    }

    fun setInputTypeInput(type: Int) {
        if (btn_edit_edit_input != null) {
            btn_edit_edit_input!!.inputType = type
        }
    }

    fun setButtonHintText(resid: Int) {
        if (buttonEdit != null) {
            buttonEdit!!.setHint(resid)
        }
    }

    fun setButtonHintTextSecond(resid: Int) {
        if (buttonEditSecond != null) {
            buttonEditSecond!!.setHint(resid)
        }
    }

    fun setButtonText(resid: Int) {
        if (buttonEdit != null) {
            buttonEdit!!.setText(resid)
        }
    }

    fun setButtonTextSecond(resid: Int) {
        if (buttonEditSecond != null) {
            buttonEditSecond!!.setText(resid)
        }
    }

    fun setButtonText(text: String?) {
        if (buttonEdit != null) {
            buttonEdit!!.setText(text)
        }
    }

    fun setButtonTextSecond(text: String?) {
        if (buttonEditSecond != null) {
            buttonEditSecond!!.setText(text)
        }
    }

    fun setButtonEditEnable(enable: Boolean) {
        if (buttonEdit != null) {
            buttonEdit!!.isEnabled = enable
        }
    }

    fun setButtonEditEnableSecond(enable: Boolean) {
        if (buttonEditSecond != null) {
            buttonEditSecond!!.isEnabled = enable
        }
    }

    fun setErrText(err: String?) {
        if (buttonEdit != null) {
            buttonEdit!!.error = err
        }
    }

    fun setErrTextSecond(err: String?) {
        if (buttonEditSecond != null) {
            buttonEditSecond!!.error = err
        }
    }

    val buttonEditSelectVisibility: Int
        get() {
            var iVisibility = GONE
            if (null == buttonEdit) {
                return iVisibility
            }
            iVisibility = buttonEdit!!.visibility
            return iVisibility
        }

    val buttonEditSelectVisibilitySecond: Int
        get() {
            var iVisibility = GONE
            if (null == buttonEditSecond) {
                return iVisibility
            }
            iVisibility = buttonEditSecond!!.visibility
            return iVisibility
        }

    val buttonEditText: String
        get() {
            var strData = ""
            if (null == buttonEdit) {
                return strData
            }
            strData = buttonEdit!!.text.toString()
            return strData
        }

    val buttonEditTextSecond: String
        get() {
            var strData = ""
            if (null == buttonEditSecond) {
                return strData
            }
            strData = buttonEditSecond!!.text.toString()
            return strData
        }

    var buttonEditInputText: String?
        get() {
            var strData = ""
            if (null == btn_edit_edit_input) {
                return strData
            }
            strData = btn_edit_edit_input!!.text.toString()
            return strData
        }
        set(text) {
            if (null == btn_edit_edit_input) {
                return
            }
            btn_edit_edit_input!!.setText(text)
        }

    fun setButtonEnabled(enable: Boolean) {
        if (buttonEdit != null) {
            buttonEdit!!.isEnabled = enable
        }
    }

    fun setButtonEnabledSecond(enable: Boolean) {
        if (buttonEditSecond != null) {
            buttonEditSecond!!.isEnabled = enable
        }
    }

    fun setButtonListener(listener: ButtonListener?) {
        m_ButtonListener = listener
    }

    fun removeButtonListener() {
        if (buttonEdit != null) {
            buttonEdit!!.text = null
            buttonEdit!!.setOnClickListener(null)
            buttonEdit = null
        }
        if (btn_edit_select_btn != null) {
            btn_edit_select_btn!!.text = null
            btn_edit_select_btn!!.setOnClickListener(null)
            btn_edit_select_btn = null
        }

        if (buttonEditSecond != null) {
            buttonEditSecond!!.text = null
            buttonEditSecond!!.setOnClickListener(null)
            buttonEditSecond = null
        }
        if (btn_edit_select_btn_2 != null) {
            btn_edit_select_btn_2!!.text = null
            btn_edit_select_btn_2!!.setOnClickListener(null)
            btn_edit_select_btn_2 = null
        }

        if (btn_edit_query_btn != null) {
            btn_edit_query_btn!!.text = null
            btn_edit_query_btn!!.setOnClickListener(null)
            btn_edit_query_btn = null
        }
        if (btn_edit_name != null) {
            btn_edit_name!!.setOnClickListener(null)
            btn_edit_name = null
        }

        if (btn_edit_query_btn != null) {
            btn_edit_query_btn?.setOnClickListener(null)
            btn_edit_query_btn = null
        }

        if (buttonEdit != null) {
            buttonEdit?.setOnClickListener(null)
            buttonEdit = null
        }

        if (buttonEditSecond != null) {
            buttonEditSecond?.setOnClickListener(null)
            buttonEditSecond = null
        }

        if (btn_edit_select_line != null) {
            btn_edit_select_line!!.setOnClickListener(null)
            btn_edit_select_line = null
        }

        if (btn_edit_select_line_2 != null) {
            btn_edit_select_line_2!!.setOnClickListener(null)
            btn_edit_select_line_2 = null
        }
        if (btn_edit_display != null) {
            btn_edit_display!!.setOnClickListener(null)
            btn_edit_display = null
        }

        if (btn_edit_select_btn != null) {
            btn_edit_select_btn?.setOnClickListener(null)
            btn_edit_select_btn = null
        }

        if (btn_edit_select_btn_2 != null) {
            btn_edit_select_btn_2?.setOnClickListener(null)
            btn_edit_select_btn_2 = null
        }

        if (btn_edit_edit_input != null) {
            btn_edit_edit_input!!.setOnClickListener(null)
            btn_edit_edit_input = null
        }

        if (btn_edit_linearLayout != null) {
            btn_edit_linearLayout!!.layoutTransition = null
            btn_edit_linearLayout = null
        }

        if (btn_edit_linearLayout_2 != null) {
            btn_edit_linearLayout_2!!.layoutTransition = null
            btn_edit_linearLayout_2 = null
        }
        m_ButtonListener = null
        m_ClickListener = null
        btn_edit_linearLayout = null
        btn_edit_linearLayout_2 = null
        btn_edit_name = null
        btn_edit_query_btn = null
        buttonEdit = null
        buttonEditSecond = null
        btn_edit_select_line = null
        btn_edit_select_line_2 = null
        btn_edit_display = null
        btn_edit_select_btn = null
        btn_edit_select_btn_2 = null
        btn_edit_edit_input = null
    }

    private var m_ButtonListener: ButtonListener? = null

    interface ButtonListener {
        fun onClick(v: View?, buttonId: Int)
    }

    private var m_ClickListener: ClickListener? = ClickListener()

    private inner class ClickListener : OnClickListener {
        override fun onClick(v: View?) {
            if (null == v) {
                return
            }
            val id = v.id
            if (R.id.btn_edit_query_btn == id) {
                if (m_ButtonListener != null) {
                    m_ButtonListener!!.onClick(this@ButtonEditSelectD, BUTTON_ID_QUERY)
                }
            } else if (R.id.btn_edit_edit_select == id) {
                if (m_ButtonListener != null) {
                    m_ButtonListener!!.onClick(this@ButtonEditSelectD, BUTTON_ID_EDIT)
                }
            } else if (R.id.btn_edit_edit_select_2 == id) {
                if (m_ButtonListener != null) {
                    m_ButtonListener!!.onClick(this@ButtonEditSelectD, BUTTON_ID_EDIT_SECOND)
                }
            } else if (R.id.btn_edit_select_btn == id) {
                if (m_ButtonListener != null) {
                    m_ButtonListener!!.onClick(this@ButtonEditSelectD, BUTTON_ID_SELECT)
                }
            } else if (R.id.btn_edit_select_btn_2 == id) {
                if (m_ButtonListener != null) {
                    m_ButtonListener!!.onClick(this@ButtonEditSelectD, BUTTON_ID_SELECT_SECOND)
                }
            } else {
            }
        }
    }

    companion object {
        const val BUTTON_TYPE_EDIT: Int = 1
        const val BUTTON_TYPE_QUERY: Int = 2
        const val BUTTON_TYPE_EDIT_QUERY: Int = 3
        const val BUTTON_TYPE_SELECT_QUERY: Int = 4
        const val BUTTON_TYPE_SELECT_SECOND_QUERY: Int = 5
        const val BUTTON_TYPE_SELECTTWO_QUERY: Int = 6
        const val BUTTON_TYPE_SELECT_INPUT_QUERY: Int = 7
        const val BUTTON_TYPE_SELECT_SECOND_INPUT_QUERY: Int = 8
        const val BUTTON_TYPE_SELECTTWO_INPUT_QUERY: Int = 9
        const val BUTTON_TYPE_SELECT: Int = 10

        const val BUTTON_ID_QUERY: Int = 0
        const val BUTTON_ID_EDIT: Int = 1
        const val BUTTON_ID_EDIT_SECOND: Int = 2
        const val BUTTON_ID_SELECT: Int = 3
        const val BUTTON_ID_SELECT_SECOND: Int = 4
    }
}
