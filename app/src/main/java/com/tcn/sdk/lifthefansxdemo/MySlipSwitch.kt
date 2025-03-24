package com.tcn.sdk.lifthefansxdemo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener

class MySlipSwitch : View, OnTouchListener {
    //弿?弿Я时的背景，关闭时的背景，滑动按钮
    private var switch_on_Bkg: Bitmap? = null
    private var switch_off_Bkg: Bitmap? = null
    private var slip_Btn: Bitmap? = null
    private var on_Rect: Rect? = null
    private var off_Rect: Rect? = null

    //是否正在滑动
    private var isSlipping = false

    //当前弿?状瀯?true为开启，false为关闿
    private var isSwitchOn = false

    //手指按下时的水平坐标X，当前的水平坐标X
    private var previousX = 0f
    private var currentX = 0f

    //弿?监听噿
    private var onSwitchListener: OnSwitchListener? = null

    //是否设置了开关监听器
    private var isSwitchListenerOn = false


    constructor(context: Context?) : super(context) {
        init()
    }


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }


    private fun init() {
        setOnTouchListener(this)
    }


    fun setImageResource(switchOnBkg: Int, switchOffBkg: Int, slipBtn: Int) {
        switch_on_Bkg = BitmapFactory.decodeResource(resources, switchOnBkg)?:return
        switch_off_Bkg = BitmapFactory.decodeResource(resources, switchOffBkg)?:return
        slip_Btn = BitmapFactory.decodeResource(resources, slipBtn)

        //右半边Rect，即滑动按钮在右半边时表示开关开吿
        on_Rect = Rect(
            switch_off_Bkg!!.getWidth() - slip_Btn!!.getWidth(),
            0,
            switch_off_Bkg!!.getWidth(),
            slip_Btn!!.getHeight()
        )
        //左半边Rect，即滑动按钮在左半边时表示开关关闿
        off_Rect = Rect(0, 0, slip_Btn!!.getWidth(), slip_Btn!!.getHeight())
    }


     var switchState: Boolean
        get() = isSwitchOn
        set(switchState) {
            isSwitchOn = switchState
            updateSwitchState(switchState)
        }


    protected fun updateSwitchState(switchState: Boolean) {
        isSwitchOn = switchState
        invalidate()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val matrix = Matrix()
        val paint = Paint()
        //滑动按钮的左边坐栿
        var left_SlipBtn: Float

        //手指滑动到左半边的时候表示开关为关闭状瀯?滑动到右半边的时怨h示弿?为开启状怿
        if (currentX < (switch_on_Bkg!!.width / 2)) {
            canvas.drawBitmap(switch_off_Bkg!!, matrix, paint)
        } else {
            canvas.drawBitmap(switch_on_Bkg!!, matrix, paint)
        }

        //判断当前是否正在滑动
        left_SlipBtn = if (isSlipping) {
            if (currentX > switch_on_Bkg!!.width) {
                (switch_on_Bkg!!.width - slip_Btn!!.width).toFloat()
            } else {
                currentX - slip_Btn!!.width / 2
            }
        } else {
            //根据当前的开关状态设置滑动按钮的位置
            if (isSwitchOn) {
                on_Rect!!.left.toFloat()
            } else {
                off_Rect!!.left.toFloat()
            }
        }

        //对滑动按钮的位置进行异常判断
        if (left_SlipBtn < 0) {
            left_SlipBtn = 0f
        } else if (left_SlipBtn > switch_on_Bkg!!.width - slip_Btn!!.width) {
            left_SlipBtn = (switch_on_Bkg!!.width - slip_Btn!!.width).toFloat()
        }

        canvas.drawBitmap(slip_Btn!!, left_SlipBtn, 0f, paint)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (null == switch_on_Bkg) {
            return
        }
        setMeasuredDimension(switch_on_Bkg!!.width, switch_on_Bkg!!.height)
    }


    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE -> currentX = event.x
            MotionEvent.ACTION_DOWN -> {
                //				if(event.getX() > switch_on_Bkg.getWidth() || event.getY() > switch_on_Bkg.getHeight()) {
//					return false;
//				}
                isSlipping = true
                previousX = event.x
                currentX = previousX
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isSlipping = false
                //松开前开关的状濍
                val previousSwitchState = isSwitchOn

                isSwitchOn = if (currentX >= (switch_on_Bkg!!.width / 2)) {
                    true
                } else {
                    false
                }

                //如果设置了监听器，则调用此方泿
                if (isSwitchListenerOn && (previousSwitchState != isSwitchOn)) {
                    onSwitchListener!!.onSwitched(isSwitchOn)
                }
            }

            else -> {}
        }

        //重新绘制控件
        invalidate()
        return true
    }


    fun setOnSwitchListener(listener: OnSwitchListener?) {
        onSwitchListener = listener
        isSwitchListenerOn = true
    }


    interface OnSwitchListener {
        fun onSwitched(isSwitchOn: Boolean)
    }
}
