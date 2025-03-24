package com.tcn.sdk.lifthefansxdemo

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by Administrator on 2016/4/18.
 */
class OutDialog(context: Context, str: String, str1: String) :
    Dialog(context, R.style.ui_base_Dialog_bocop) {
    private var mAnim: RotateAnimation? = null

    private var m_Context: Context? = null
    private var iv_route: ImageView? = null
    private var tv: TextView? = null
    private var time_shyu: TextView? = null
    private var tv_point: TextView? = null
    private var outnum: TextView? = null
    private var cancelable = true
    private var timeCount = 0
    private var m_iMaxTime = 60000
    private var m_itime_sy = 0
    private var m_sy_fdata: String? = null
    private var m_sy_data_bf: StringBuffer? = null


    private var handler: Handler? = object : Handler() {
        private var num = 0


        override fun handleMessage(msg: Message) {
            if (msg.what == CHANGE_TITLE_WHAT) {
                val builder = StringBuilder()
                if (num >= MAX_SUFFIX_NUMBER) {
                    num = 0
                }
                num++
                for (i in 0..<num) {
                    builder.append(SUFFIX)
                }

                if (timeCount++ > (m_iMaxTime / CHNAGE_TITLE_DELAYMILLIS)) {
                    this.removeMessages(CHANGE_TITLE_WHAT)
                    timeCount = 0
                    dismiss()
                    return
                }

                tv_point!!.text = builder.toString()
                if (isShowing) {
                    this.sendEmptyMessageDelayed(
                        CHANGE_TITLE_WHAT,
                        CHNAGE_TITLE_DELAYMILLIS.toLong()
                    )
                } else {
                    num = 0
                }
            } else if (DISMISS_DIALOG == msg.what) {
                dismiss()
            } else if (msg.what == TIME_TITLE_SY) {
                if (null == m_sy_data_bf) {
                    m_sy_data_bf = StringBuffer()
                }
                m_sy_data_bf!!.delete(0, m_sy_data_bf!!.length)
                m_sy_data_bf!!.append(m_sy_fdata)
                m_sy_data_bf!!.append(msg.arg1.toString())
                m_sy_data_bf!!.append("s")
                if (time_shyu != null) {
                    time_shyu!!.text = m_sy_data_bf.toString()
                }

                if (msg.arg1 > 0) {
                    val message = this.obtainMessage()
                    message.what = TIME_TITLE_SY
                    message.arg1 = msg.arg1 - 1
                    this.removeMessages(TIME_TITLE_SY)
                    this.sendMessageDelayed(message, 1000)
                } else {
                    if (time_shyu != null) {
                        time_shyu!!.text = ""
                    }
                }
            } else {
            }
        }
    }

    init {
        m_Context = context
        init(str, str1)
    }

    private fun init(str: String, str1: String) {
        val contentView = View.inflate(
            context, ResourceUtil.getLayoutId(
                m_Context!!, "app_outdialog"
            ), null
        )
        //contentView.setBackgroundResource(R.drawable.dialoback);
        setContentView(contentView)

        contentView.setOnClickListener { }
        time_shyu = findViewById<View>(ResourceUtil.getId(m_Context!!, "time_shyu")) as TextView
        iv_route = findViewById<View>(ResourceUtil.getId(m_Context!!, "iv_route")) as ImageView
        tv = findViewById<View>(ResourceUtil.getId(m_Context!!, "tv")) as TextView
        tv!!.text = str1
        tv_point = findViewById<View>(ResourceUtil.getId(m_Context!!, "tv_point")) as TextView
        outnum = findViewById<View>(ResourceUtil.getId(m_Context!!, "outnum")) as TextView
        outnum!!.text = str
        initAnim()
        timeCount = 0
        window!!.setWindowAnimations(Resources.getAnimResourceID(R.anim.ui_base_alpha_in))
    }

    fun deInit() {
        setShowTime(0)
        setOnCancelListener(null)
        setOnShowListener(null)
        setOnDismissListener(null)
        if (iv_route != null) {
            iv_route!!.clearAnimation()
            iv_route = null
        }
        if (mAnim != null) {
            mAnim!!.cancel()
            mAnim = null
        }
        if (handler != null) {
            handler!!.removeCallbacksAndMessages(null)
            handler = null
        }
        tv = null
        tv_point = null
        outnum = null
        m_Context = null
    }

    private fun initAnim() {
        mAnim = RotateAnimation(0f, 360f, Animation.RESTART, 0.5f, Animation.RESTART, 0.5f)
        mAnim!!.duration = 1500
        mAnim!!.repeatCount = Animation.INFINITE
        mAnim!!.repeatMode = Animation.RESTART
        mAnim!!.startTime = Animation.START_ON_FIRST_FRAME.toLong()
        val lir = LinearInterpolator()
        mAnim!!.interpolator = lir
    }

    override fun show() {
        timeCount = 0
        iv_route!!.startAnimation(mAnim)
        handler!!.removeMessages(CHANGE_TITLE_WHAT)
        handler!!.sendEmptyMessage(CHANGE_TITLE_WHAT)
        super.show()
    }

    override fun dismiss() {
        mAnim!!.cancel()
        iv_route!!.clearAnimation()
        handler!!.removeCallbacksAndMessages(null)
        super.dismiss()
    }


    override fun cancel() {
        mAnim!!.cancel()
        iv_route!!.clearAnimation()
        handler!!.removeCallbacksAndMessages(null)
        super.cancel()
    }

    override fun setCancelable(flag: Boolean) {
        cancelable = flag
        super.setCancelable(flag)
    }

    override fun setTitle(title: CharSequence?) {
        tv!!.text = title
    }

    override fun setTitle(titleId: Int) {
        setTitle(context.getString(titleId))
    }

    fun showSuccess() {
        handler!!.removeMessages(CHANGE_TITLE_WHAT)
        mAnim!!.cancel()
        iv_route!!.clearAnimation()
        //iv_route.setBackgroundResource(0);
        iv_route!!.setImageResource(R.mipmap.lyric_search_pressed)
        outnum!!.text = ""
        tv!!.text = "出货成功"
        tv_point!!.text = ""
        tv_point!!.visibility = View.GONE
        handler!!.sendEmptyMessageDelayed(DISMISS_DIALOG, DISMISS_DIALOG_DELAYMILLIS.toLong())
    }

    fun showFail() {
        handler!!.removeMessages(CHANGE_TITLE_WHAT)
        mAnim!!.cancel()
        iv_route!!.clearAnimation()
        iv_route!!.setBackgroundResource(0)
        iv_route!!.setImageResource(R.mipmap.shibai)
        outnum!!.text = ""
        tv!!.text = "出货失败"
        tv_point!!.text = ""
        tv_point!!.visibility = View.GONE
        handler!!.sendEmptyMessageDelayed(DISMISS_DIALOG, DISMISS_DIALOG_DELAYMILLIS.toLong())
    }

    fun setNumber(number: String?) {
        timeCount = 0
        if (number != null && outnum != null) {
            outnum!!.text = number
        }
    }

    fun setText(text: String?) {
        if (tv != null) {
            tv!!.text = text
        }
    }

    fun setShowTime(second: Int) {
        m_iMaxTime = second * 1000
    }

    fun cleanData() {
        m_itime_sy = 0
        if (time_shyu != null) {
            time_shyu!!.text = ""
        }
        m_sy_fdata = null
        handler!!.removeMessages(TIME_TITLE_SY)
    }

    fun setSyTime(second: Int, data: String?) {
        if (second <= 0) {
            handler!!.removeMessages(TIME_TITLE_SY)
            if (time_shyu != null) {
                time_shyu!!.text = data
            }
            return
        }
        m_itime_sy = second
        m_sy_fdata = data
        val message = handler!!.obtainMessage()
        message.what = TIME_TITLE_SY
        message.arg1 = second
        handler!!.removeMessages(TIME_TITLE_SY)
        handler!!.sendMessage(message)
    }

    companion object {
        private const val CHANGE_TITLE_WHAT = 1
        private const val DISMISS_DIALOG = 2
        private const val DISMISS_DIALOG_DELAYMILLIS = 1000
        private const val CHNAGE_TITLE_DELAYMILLIS = 300
        private const val MAX_SUFFIX_NUMBER = 3
        private const val TIME_TITLE_SY = 5
        private const val SUFFIX = '.'
    }
}
