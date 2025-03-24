package com.tcn.sdk.lifthefansxdemo

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.TextView

class LoadingDialog(context: Context, load: String, tv: String) :
    Dialog(context, R.style.ui_base_Dialog_bocop) {
    private var timeCount = 0
    private var m_iMaxTime = 60000
    private var cancelable = true

    private var m_Context: Context? = null
    private var tv: TextView? = null
    private var load_text: TextView? = null
    private var mAnim: RotateAnimation? = null


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

                if (isShowing) {
                    this.sendEmptyMessageDelayed(
                        CHANGE_TITLE_WHAT,
                        CHNAGE_TITLE_DELAYMILLIS.toLong()
                    )
                } else {
                    timeCount = 0
                    num = 0
                }
            }
        }
    }

    init {
        m_Context = context
        init(load, tv)
    }

    private fun init(load: String, tv1: String) {
        val contentView = View.inflate(
            m_Context,
            ResourceUtil.getLayoutId(m_Context!!, "app_activity_custom_loding_dialog_layout"),
            null
        )
        //contentView.setBackgroundResource(R.drawable.dialoback);
        setContentView(contentView)
        setCancelable(false)
        contentView.setOnClickListener {
            if (cancelable) {
                dismiss()
            }
        }
        tv = findViewById<View>(ResourceUtil.getId(m_Context!!, "tv")) as TextView
        tv!!.text = tv1
        load_text = findViewById<View>(ResourceUtil.getId(m_Context!!, "load_text")) as TextView
        load_text!!.text = load
        //		initAnim();
        window!!.setWindowAnimations(Resources.getAnimResourceID(R.anim.ui_base_alpha_in))
    }

    fun deInit() {
        setShowTime(0)
        setOnCancelListener(null)
        setOnShowListener(null)
        setOnDismissListener(null)
        if (mAnim != null) {
            mAnim!!.cancel()
            mAnim = null
        }
        if (handler != null) {
            handler!!.removeCallbacksAndMessages(null)
            handler = null
        }
        tv = null
        load_text = null
        m_Context = null
    }

    private fun initAnim() {
        mAnim = RotateAnimation(360f, 0f, Animation.RESTART, 0.5f, Animation.RESTART, 0.5f)
        mAnim!!.duration = 2000
        mAnim!!.repeatCount = Animation.INFINITE
        mAnim!!.repeatMode = Animation.RESTART
        mAnim!!.startTime = Animation.START_ON_FIRST_FRAME.toLong()
    }

    override fun show() {
        super.show()
        timeCount = 0
        handler!!.sendEmptyMessage(CHANGE_TITLE_WHAT)
    }

    override fun dismiss() {
//		mAnim.cancel();
        super.dismiss()
        handler!!.removeCallbacksAndMessages(null)
    }

    override fun cancel() {
        super.cancel()
        handler!!.removeCallbacksAndMessages(null)
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

    fun setLoadText(str: String?) {
        if (load_text != null) {
            load_text!!.text = str
        }
    }

    fun setShowTime(second: Int) {
        timeCount = 0
        m_iMaxTime = second * 1000
    }

    companion object {
        private const val CHANGE_TITLE_WHAT = 1
        private const val CHNAGE_TITLE_DELAYMILLIS = 300
        private const val MAX_SUFFIX_NUMBER = 3
        private const val SUFFIX = '.'
    }
}
