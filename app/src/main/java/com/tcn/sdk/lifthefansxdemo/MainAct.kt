package com.tcn.sdk.lifthefansxdemo

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.text.InputType
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.tcn.lifthefansxboard.control.PayMethod
import com.tcn.lifthefansxboard.control.ShipSlotInfo
import com.tcn.lifthefansxboard.control.TcnVendEventID
import com.tcn.lifthefansxboard.control.TcnVendEventResultID
import com.tcn.lifthefansxboard.control.TcnVendIF
import com.tcn.lifthefansxboard.control.TcnVendIF.VendEventListener
import com.tcn.lifthefansxboard.control.VendEventInfo
import com.tcn.sdk.lifthefansxdemo.Titlebar.TitleBarListener

class MainAct : TcnMainActivity() {
    private var singleitem = 0
    private var m_Titlebar: Titlebar? = null
    private var main_serport: Button? = null
    private var shop_car: Button? = null
    private var menu_lift_clear_drive_fault: ButtonEditSelectD? = null
    private var menu_lift_query_drive_fault: ButtonEditSelectD? = null
    private var menu_lift_ship_slot: ButtonEditSelectD? = null
    private var menu_lift_ship_slot_test: ButtonEditSelectD? = null
    private var menu_lift_reqselect: ButtonEditSelectD? = null


    private var menu_ys_query_drive_slot_info: ButtonEditSelectD? = null


    private var m_OutDialog: OutDialog? = null

    private var m_LoadingDialog: LoadingDialog? = null
    private val func_5inch_board_btn: TextView? = null
    private val tv: TextView? = null
    private var shipContent: TextView? = null

    private var shipMsgContent: StringBuffer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.background_menu_settings_layout_lift)
        TcnVendIF.getInstance().LoggerDebug(TAG, "MainAct onCreate()")
        initView()
        shipMsgContent = StringBuffer()
    }

    override fun onResume() {
        super.onResume()
        TcnVendIF.getInstance().registerListener(m_vendListener)
    }

    override fun onPause() {
        super.onPause()
        TcnVendIF.getInstance().unregisterListener(m_vendListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (m_Titlebar != null) {
            m_Titlebar!!.removeButtonListener()
            m_Titlebar = null
        }

        if (menu_lift_clear_drive_fault != null) {
            menu_lift_clear_drive_fault!!.removeButtonListener()
            menu_lift_clear_drive_fault!!.setOnClickListener(null)
            menu_lift_clear_drive_fault = null
        }

        if (menu_ys_query_drive_slot_info != null) {
            menu_ys_query_drive_slot_info!!.removeButtonListener()
            menu_ys_query_drive_slot_info!!.setOnClickListener(null)
            menu_ys_query_drive_slot_info = null
        }
        if (m_OutDialog != null) {
            m_OutDialog!!.deInit()
            m_OutDialog = null
        }

        if (menu_lift_query_drive_fault != null) {
            menu_lift_query_drive_fault!!.removeButtonListener()
            menu_lift_query_drive_fault = null
        }


        if (main_serport != null) {
            main_serport!!.setOnClickListener(null)
            main_serport = null
        }

        if (shop_car != null) {
            shop_car!!.setOnClickListener(null)
            shop_car = null
        }

        m_OutDialog = null
        m_TitleBarListener = null
        m_ButtonEditClickListener = null
        m_vendListener = null
    }

    private fun initView() {
        if (m_OutDialog == null) {
            m_OutDialog = OutDialog(this@MainAct, "", getString(R.string.background_drive_setting))
            m_OutDialog!!.setShowTime(10000)
        }

        shipContent = findViewById<View>(R.id.shipContent) as TextView
        shipContent!!.movementMethod = ScrollingMovementMethod.getInstance()
        m_Titlebar = findViewById<View>(R.id.menu_setttings_titlebar) as Titlebar
        if (m_Titlebar != null) {
            m_Titlebar!!.setButtonType(Titlebar.BUTTON_TYPE_BACK)
            m_Titlebar!!.setButtonName(R.string.background_menu_settings)
            m_Titlebar!!.setTitleBarListener(m_TitleBarListener)
        }

        main_serport = findViewById<View>(R.id.main_serport) as Button
        shop_car = findViewById<View>(R.id.shop_car) as Button
        main_serport!!.setOnClickListener(m_ButtonClickListener)
        shop_car!!.setOnClickListener(m_ButtonClickListener)

        menu_lift_query_drive_fault =
            findViewById<View>(R.id.menu_lift_query_drive_fault) as ButtonEditSelectD
        if (menu_lift_query_drive_fault != null) {
            if (UIComBack.instance.isMutiGrpHefanZp) {
                menu_lift_query_drive_fault!!.setButtonType(ButtonEditSelectD.BUTTON_TYPE_SELECT_QUERY)
            } else {
                menu_lift_query_drive_fault!!.setButtonType(ButtonEditSelectD.BUTTON_TYPE_QUERY)
            }
            menu_lift_query_drive_fault!!.setButtonName(getString(R.string.background_drive_query_fault))
            menu_lift_query_drive_fault!!.setButtonNameTextSize(
                TcnVendIF.getInstance().getFitScreenSize(20)
            )
            menu_lift_query_drive_fault!!.setButtonQueryText(getString(R.string.background_drive_query))
            menu_lift_query_drive_fault!!.setButtonQueryTextColor("#ffffff")
            menu_lift_query_drive_fault!!.setButtonDisplayTextColor("#4e5d72")
            menu_lift_query_drive_fault!!.setButtonListener(m_ButtonEditClickListener)
        }

        menu_lift_clear_drive_fault =
            findViewById<View>(R.id.menu_lift_clear_drive_fault) as ButtonEditSelectD
        if (menu_lift_clear_drive_fault != null) {
            if (UIComBack.instance.isMutiGrpHefanZp) {
                menu_lift_clear_drive_fault!!.setButtonType(ButtonEditSelectD.BUTTON_TYPE_SELECT_QUERY)
            } else {
                menu_lift_clear_drive_fault!!.setButtonType(ButtonEditSelectD.BUTTON_TYPE_QUERY)
            }
            menu_lift_clear_drive_fault!!.setButtonName(getString(R.string.background_drive_clean_fault))
            menu_lift_clear_drive_fault!!.setButtonNameTextSize(
                TcnVendIF.getInstance().getFitScreenSize(20)
            )
            menu_lift_clear_drive_fault!!.setButtonQueryText(R.string.background_drive_clean)
            menu_lift_clear_drive_fault!!.setButtonQueryTextColor("#ffffff")
            menu_lift_clear_drive_fault!!.setButtonDisplayTextColor("#4e5d72")
            menu_lift_clear_drive_fault!!.setButtonListener(m_ButtonEditClickListener)
        }

        menu_ys_query_drive_slot_info = findViewById(R.id.menu_ys_query_drive_slot_info)
        if (menu_ys_query_drive_slot_info != null) {
            menu_ys_query_drive_slot_info!!.setButtonType(ButtonEditSelectD.BUTTON_TYPE_QUERY)
            menu_ys_query_drive_slot_info!!.setButtonName("查询货道")
            menu_ys_query_drive_slot_info!!.setButtonNameTextSize(
                TcnVendIF.getInstance().getFitScreenSize(20)
            )
            menu_ys_query_drive_slot_info!!.setButtonQueryText(R.string.background_drive_query)
            menu_ys_query_drive_slot_info!!.setButtonQueryTextColor("#ffffff")
            menu_ys_query_drive_slot_info!!.setButtonDisplayTextColor("#4e5d72")
            menu_ys_query_drive_slot_info!!.setButtonListener(m_ButtonEditClickListener)
        }

        menu_lift_ship_slot = findViewById<View>(R.id.menu_lift_ship_slot) as ButtonEditSelectD
        if (menu_lift_ship_slot != null) {
            menu_lift_ship_slot!!.setButtonType(ButtonEditSelectD.BUTTON_TYPE_EDIT_QUERY)
            menu_lift_ship_slot!!.setButtonName("出货例子")
            menu_lift_ship_slot!!.setButtonNameTextSize(
                TcnVendIF.getInstance().getFitScreenSize(20)
            )
            menu_lift_ship_slot!!.setButtonQueryText("出货")
            menu_lift_ship_slot!!.setButtonQueryTextSize(
                TcnVendIF.getInstance().getFitScreenSize(16)
            )
            menu_lift_ship_slot!!.setButtonQueryTextColor("#ffffff")
            menu_lift_ship_slot!!.setButtonDisplayTextColor("#4e5d72")
            menu_lift_ship_slot!!.setInputTypeInput(InputType.TYPE_CLASS_NUMBER)
            menu_lift_ship_slot!!.setButtonListener(m_ButtonEditClickListener)
        }

        menu_lift_ship_slot_test =
            findViewById<View>(R.id.menu_lift_ship_slot_test) as ButtonEditSelectD
        if (menu_lift_ship_slot_test != null) {
            menu_lift_ship_slot_test!!.setButtonType(ButtonEditSelectD.BUTTON_TYPE_EDIT_QUERY)
            menu_lift_ship_slot_test!!.setButtonName("测试货道")
            menu_lift_ship_slot_test!!.setButtonNameTextSize(
                TcnVendIF.getInstance().getFitScreenSize(20)
            )
            menu_lift_ship_slot_test!!.setButtonQueryText("测试")
            menu_lift_ship_slot_test!!.setButtonQueryTextSize(
                TcnVendIF.getInstance().getFitScreenSize(16)
            )
            menu_lift_ship_slot_test!!.setButtonQueryTextColor("#ffffff")
            menu_lift_ship_slot_test!!.setButtonDisplayTextColor("#4e5d72")
            menu_lift_ship_slot_test!!.setInputTypeInput(InputType.TYPE_CLASS_NUMBER)
            menu_lift_ship_slot_test!!.setButtonListener(m_ButtonEditClickListener)
        }

        menu_lift_reqselect = findViewById<View>(R.id.menu_lift_reqselect) as ButtonEditSelectD
        if (menu_lift_reqselect != null) {
            menu_lift_reqselect!!.setButtonType(ButtonEditSelectD.BUTTON_TYPE_EDIT_QUERY)
            menu_lift_reqselect!!.setButtonName("选择货道")
            menu_lift_reqselect!!.setButtonNameTextSize(
                TcnVendIF.getInstance().getFitScreenSize(20)
            )
            menu_lift_reqselect!!.setButtonQueryText("选择")
            menu_lift_reqselect!!.setButtonQueryTextSize(
                TcnVendIF.getInstance().getFitScreenSize(16)
            )
            menu_lift_reqselect!!.setButtonQueryTextColor("#ffffff")
            menu_lift_reqselect!!.setButtonDisplayTextColor("#4e5d72")
            menu_lift_reqselect!!.setInputTypeInput(InputType.TYPE_CLASS_NUMBER)
            menu_lift_reqselect!!.setButtonListener(m_ButtonEditClickListener)
        }
    }

    private fun setDialogShow() {
        if (m_OutDialog != null) {
            m_OutDialog!!.setShowTime(5)
            m_OutDialog!!.setTitle(getString(R.string.background_tip_wait_amoment))
            m_OutDialog!!.show()
        }
    }

    private fun showSetConfirm(cmdType: Int, grp: String, data1: String, data2: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.background_drive_modify_ask))
        builder.setPositiveButton(
            getString(R.string.background_backgroound_ensure)
        ) { dialog, which ->
            var showTimeOut = 5
            if (CMD_SET_LIFT_RESTORE_FACTORY == cmdType) {
                if (TcnVendIF.getInstance().isDigital(grp)) {
                    TcnVendIF.getInstance().reqFactoryReset(grp.toInt())
                } else {
                    TcnVendIF.getInstance().reqFactoryReset(-1)
                }
            } else if (CMD_SET_LIFT_ID == cmdType) {
                showTimeOut = 1
                if (TcnVendIF.getInstance().isDigital(grp) && TcnVendIF.getInstance()
                        .isDigital(data1)
                ) {
                    TcnVendIF.getInstance().reqSetId(grp.toInt(), data1.toInt())
                } else {
                    if (TcnVendIF.getInstance().isDigital(data1)) {
                        TcnVendIF.getInstance().reqSetId(-1, data1.toInt())
                    }
                }
            } else if (CMD_SET_LIFT_LIGHT_OUT_STEPS == cmdType) {
                if (TcnVendIF.getInstance().isDigital(grp) && TcnVendIF.getInstance()
                        .isDigital(data1)
                ) {
                    TcnVendIF.getInstance().reqSetLightOutSteps(grp.toInt(), data1.toInt())
                } else {
                    if (TcnVendIF.getInstance().isDigital(data1)) {
                        TcnVendIF.getInstance().reqSetLightOutSteps(-1, data1.toInt())
                    }
                }
            } else if (CMD_SET_PARAMETERS == cmdType) {
                TcnVendIF.getInstance().LoggerDebug(
                    TAG,
                    "CMD_SET_PARAMETERS data1: $data1 data2: $data2"
                )
                if (TcnVendIF.getInstance().isDigital(grp) && TcnVendIF.getInstance()
                        .isNumeric(data2)
                ) {
                    TcnVendIF.getInstance().LoggerDebug(
                        TAG,
                        "CMD_SET_PARAMETERS 1 data2: $data2"
                    )
                    TcnVendIF.getInstance().reqSetParameters(
                        grp.toInt(),
                        UIComBack.instance.getQueryParameters(data1),
                        data2
                    )
                } else {
                    if (TcnVendIF.getInstance().isNumeric(data2)) {
                        TcnVendIF.getInstance().LoggerDebug(
                            TAG,
                            "CMD_SET_PARAMETERS 2 data2: $data2"
                        )

                        TcnVendIF.getInstance().reqSetParameters(-1, data1.toInt(), data2)
                    }

                    //						TcnShareUseData.getInstance().getTemperature_on_line();// 上线
                    //						TcnShareUseData.getInstance().getTemperature_off_line();// 下线
                }
            } else if (CMD_SET_SLOTNO_SPRING == cmdType) {
                if (TcnVendIF.getInstance().isDigital(data1)) {
                    TcnVendIF.getInstance().reqSetSpringSlot(data1.toInt())
                }
            } else if (CMD_SET_SLOTNO_BELTS == cmdType) {
                if (TcnVendIF.getInstance().isDigital(data1)) {
                    TcnVendIF.getInstance().reqSetBeltsSlot(data1.toInt())
                }
            } else if (CMD_SET_SLOTNO_ALL_SPRING == cmdType) {
                if (TcnVendIF.getInstance().isDigital(grp)) {
                    TcnVendIF.getInstance().reqSpringAllSlot(grp.toInt())
                } else {
                    TcnVendIF.getInstance().reqSpringAllSlot(-1)
                }
            } else if (CMD_SET_SLOTNO_ALL_BELT == cmdType) {
                if (TcnVendIF.getInstance().isDigital(grp)) {
                    TcnVendIF.getInstance().reqBeltsAllSlot(grp.toInt())
                } else {
                    TcnVendIF.getInstance().reqBeltsAllSlot(-1)
                }
            } else if (CMD_SET_SLOTNO_SINGLE == cmdType) {
                if (TcnVendIF.getInstance().isDigital(data1)) {
                    TcnVendIF.getInstance().reqSingleSlot(data1.toInt())
                }
            } else if (CMD_SET_SLOTNO_DOUBLE == cmdType) {
                if (TcnVendIF.getInstance().isDigital(data1)) {
                    TcnVendIF.getInstance().reqDoubleSlot(data1.toInt())
                }
            } else if (CMD_SET_SLOTNO_ALL_SINGLE == cmdType) {
                if (TcnVendIF.getInstance().isDigital(grp)) {
                    TcnVendIF.getInstance().reqSingleAllSlot(grp.toInt())
                } else {
                    TcnVendIF.getInstance().reqSingleAllSlot(-1)
                }
            } else if (CMD_SET_TEST_MODE == cmdType) {
                showTimeOut = 60 * 60
                if (TcnVendIF.getInstance().isDigital(grp)) {
                    TcnVendIF.getInstance().reqTestMode(grp.toInt())
                } else {
                    TcnVendIF.getInstance().reqTestMode(-1)
                }
            } else if (CMD_SET_GLASS_HEAT_OPEN == cmdType) {
                if (TcnVendIF.getInstance().isDigital(grp)) {
                    TcnVendIF.getInstance().reqSetGlassHeatEnable(grp.toInt(), true)
                } else {
                    TcnVendIF.getInstance().reqSetGlassHeatEnable(-1, true)
                }
            } else if (CMD_SET_GLASS_HEAT_CLOSE == cmdType) {
                if (TcnVendIF.getInstance().isDigital(grp)) {
                    TcnVendIF.getInstance().reqSetGlassHeatEnable(grp.toInt(), false)
                } else {
                    TcnVendIF.getInstance().reqSetGlassHeatEnable(-1, false)
                }
            } else {
            }
            if (m_OutDialog != null) {
                m_OutDialog!!.setShowTime(showTimeOut)
                m_OutDialog!!.show()
            }
        }
        builder.setNegativeButton(
            getString(R.string.background_backgroound_cancel)
        ) { dialog, which -> }
        builder.show()
    }

    private fun selectData(type: Int, which: Int, str: Array<String>?) {
        if ((which < 0) || (null == str) || (which >= str.size)) {
            TcnVendIF.getInstance().LoggerError(
                TAG,
                "selectData which: $which str: $str"
            )
            return
        }
    }

    private fun showSelectDialog(
        type: Int,
        title: String,
        v: EditText,
        selectData: String?,
        str: Array<String>?
    ) {
        if (null == str) {
            return
        }
        var checkedItem = -1
        if ((selectData != null) && (selectData.length > 0)) {
            for (i in str.indices) {
                if (str[i] == selectData) {
                    checkedItem = i
                    break
                }
            }
        }

        singleitem = 0
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setSingleChoiceItems(
            str, checkedItem
        ) { dialog, which -> singleitem = which }
        builder.setPositiveButton(
            getString(R.string.background_backgroound_ensure)
        ) { dialog, which -> v.setText(str[singleitem]) }
        builder.setNegativeButton(
            getString(R.string.background_backgroound_cancel)
        ) { dialog, which -> }
        builder.show()
    }

    private fun takeAwayGoodsMenu(cEventInfo: VendEventInfo, errCode: Int, msg: String) {
        if (m_LoadingDialog == null) {
            m_LoadingDialog =
                LoadingDialog(this, getString(R.string.ui_base_notify_shipment_success), msg)
        } else {
            m_LoadingDialog!!.setLoadText(this.getString(R.string.ui_base_notify_shipment_success))
            m_LoadingDialog!!.setTitle(msg)
        }
        m_LoadingDialog!!.setShowTime(10)
        m_LoadingDialog!!.show()
    }

    private fun takeAwayGoodsMenuTips(cEventInfo: VendEventInfo, errCode: Int, msg: String) {
        if (m_LoadingDialog == null) {
            m_LoadingDialog = LoadingDialog(this, "", msg)
        } else {
            m_LoadingDialog!!.setLoadText("")
            m_LoadingDialog!!.setTitle(msg)
        }
        m_LoadingDialog!!.setShowTime(10)
        m_LoadingDialog!!.show()
    }

    private var m_TitleBarListener: MenuSetTitleBarListener? = MenuSetTitleBarListener()

    private inner class MenuSetTitleBarListener : TitleBarListener {
        override fun onClick(v: View?, buttonId: Int) {
            if (Titlebar.BUTTON_ID_BACK == buttonId) {
                this@MainAct.finish()
            }
        }
    }

    private val m_ButtonClickListener: ButtonClickListener = ButtonClickListener()

    private inner class ButtonClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            val mId = view.id
            TcnVendIF.getInstance().LoggerDebug(TAG, "onClick()")
            if (R.id.main_serport == mId) {
                TcnVendIF.getInstance().LoggerDebug(TAG, "onClick() to SerialPortSetting")
                val `in` = Intent(this@MainAct, SerialPortSetting::class.java)
                `in`.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(`in`)
            } else if (R.id.shop_car == mId) {
                val list: MutableList<ShipSlotInfo> = ArrayList()
                val shipSlotInfo = ShipSlotInfo(1, 0, "255")
                shipSlotInfo.amount = "1"
                shipSlotInfo.tradeNo = System.currentTimeMillis().toString() + "1"
                list.add(shipSlotInfo)


                val shipSlotInfo2 = ShipSlotInfo(2, 1, "255")
                shipSlotInfo2.amount = "1"
                shipSlotInfo2.tradeNo = System.currentTimeMillis().toString() + "2"
                list.add(shipSlotInfo2)


                val shipSlotInfo3 = ShipSlotInfo(3, 2, "255")
                shipSlotInfo3.amount = "1"
                shipSlotInfo3.tradeNo = System.currentTimeMillis().toString() + "3"
                list.add(shipSlotInfo3)


                TcnVendIF.getInstance().reqShipList(list)
            }
        }
    }

    private var m_ButtonEditClickListener: ButtonEditClickListener? = ButtonEditClickListener()

    private inner class ButtonEditClickListener : ButtonEditSelectD.ButtonListener {
        override fun onClick(v: View?, buttonId: Int) {
            if (null == v) {
                return
            }
            if (TcnUtilityUI.isFastClick) {
                return
            }
            val id = v.id
            if (R.id.menu_lift_query_drive_fault == id) {
                if (ButtonEditSelectD.BUTTON_ID_QUERY == buttonId) {
                    menu_lift_query_drive_fault!!.setButtonDisplayText("")
                    if (UIComBack.instance.isMutiGrpHefanZp) {
                        val strParam = menu_lift_query_drive_fault!!.buttonEditText
                        if ((null == strParam) || (strParam.length < 1)) {
                            TcnUtilityUI.getToast(
                                this@MainAct,
                                getString(R.string.background_drive_tips_select_cabinetno)
                            )
                        } else {
                            TcnVendIF.getInstance()
                                .reqQueryStatus(UIComBack.instance.getGroupHefanZpId(strParam))
                        }
                    } else {
                        TcnVendIF.getInstance().reqQueryStatus(-1)
                    }
                } else if (ButtonEditSelectD.BUTTON_ID_SELECT == buttonId) {
                    showSelectDialog(
                        -1,
                        getString(R.string.background_drive_tips_select_cabinetno),
                        menu_lift_query_drive_fault!!.buttonEdit!!,
                        "",
                        UIComBack.instance.groupListHefanZpShow?.filterNotNull()!!.toTypedArray()
                    )
                } else {
                }
            } else if (R.id.menu_lift_clear_drive_fault == id) {
                if (ButtonEditSelectD.BUTTON_ID_QUERY == buttonId) {
                    menu_lift_clear_drive_fault!!.setButtonDisplayText("")
                    if (UIComBack.instance.isMutiGrpHefanZp) {
                        val strParam = menu_lift_clear_drive_fault!!.buttonEditText
                        if ((null == strParam) || (strParam.length < 1)) {
                            TcnUtilityUI.getToast(
                                this@MainAct,
                                getString(R.string.background_drive_tips_select_cabinetno)
                            )
                        } else {
                            TcnVendIF.getInstance().reqCleanDriveFaults(
                                UIComBack.instance.getGroupHefanZpId(strParam)
                            )
                        }
                    } else {
                        TcnVendIF.getInstance().reqCleanDriveFaults(-1)
                    }
                } else if (ButtonEditSelectD.BUTTON_ID_SELECT == buttonId) {
                    showSelectDialog(
                        -1,
                        getString(R.string.background_drive_tips_select_cabinetno),
                        menu_lift_clear_drive_fault!!.buttonEdit!!,
                        "",
                        UIComBack.instance.groupListHefanZpShow?.filterNotNull()!!.toTypedArray()
                    )
                } else {
                }
            } else if (R.id.menu_ys_query_drive_slot_info == id) {
                TcnVendIF.getInstance().reqQueryWorkStatus(-1, 2)
            } else if (R.id.menu_lift_ship_slot == id) {
                if (ButtonEditSelectD.BUTTON_ID_QUERY == buttonId) {
                    val strParam = menu_lift_ship_slot!!.buttonEditInputText
                    if ((null == strParam) || (strParam.length < 1)) {
                        TcnUtilityUI.getToast(
                            this@MainAct,
                            getString(R.string.background_drive_tips_input_slotno)
                        )
                    } else {
                        val slotNo = strParam.toInt() //出货的货道号
                        val shipMethod = PayMethod.PAYMETHED_WECHAT //出货方法,微信支付出货，此处自己可以修改。
                        val amount = "0.1" //支付的金额（元）,自己修改
                        val tradeNo = "1811020095201811150126888" //支付订单号，每次出货，订单号不能一样，此处自己修改。
                        val heatSeconds = 0 //此处加热时间自己设置,单位秒，最大不超过300秒
                        TcnVendIF.getInstance()
                            .reqShip(slotNo, heatSeconds, shipMethod, amount, tradeNo)
                    }
                }
            } else if (R.id.menu_lift_ship_slot_test == id) {
                if (ButtonEditSelectD.BUTTON_ID_QUERY == buttonId) {
                    val strParam = menu_lift_ship_slot_test!!.buttonEditInputText
                    if ((null == strParam) || (strParam.length < 1)) {
                        TcnUtilityUI.getToast(
                            this@MainAct,
                            getString(R.string.background_drive_tips_input_slotno)
                        )
                    } else {
                        if (TcnVendIF.getInstance().isDigital(strParam)) {
                            val heatSeconds = 0 //此处加热时间自己设置,单位秒，最大不超过300秒
                            TcnVendIF.getInstance().reqShipTest(strParam.toInt(), heatSeconds)
                        } else {
                            TcnUtilityUI.getToast(
                                this@MainAct,
                                getString(R.string.background_drive_tips_input_slotno)
                            )
                        }
                    }
                }
            } else if (R.id.menu_lift_reqselect == id) {
                if (ButtonEditSelectD.BUTTON_ID_QUERY == buttonId) {
                    val strParam = menu_lift_reqselect!!.buttonEditInputText
                    if ((null == strParam) || (strParam.length < 1)) {
                        TcnUtilityUI.getToast(
                            this@MainAct,
                            getString(R.string.background_drive_tips_input_slotno)
                        )
                    } else {
                        TcnVendIF.getInstance().reqSelectSlotNo(strParam.toInt())
                    }
                }
            } else {
            }
        }
    }

    private var m_vendListener: VendListener? = VendListener()

    private inner class VendListener : VendEventListener {
        override fun VendEvent(cEventInfo: VendEventInfo?) {
            if (tv != null) {
                tv.text =
                    cEventInfo!!.m_iEventID.toString() + " " + cEventInfo.m_lParam1 + "   " + cEventInfo.m_lParam4
            }
            if (null == cEventInfo) {
                TcnVendIF.getInstance().LoggerError(TAG, "VendListener cEventInfo is null")
                return
            }
            Log.d(
                "VendListener",
                cEventInfo.m_iEventID.toString() + "   cEventInfo:  " + cEventInfo.m_lParam1 + "   " + cEventInfo.m_lParam4
            )
            when (cEventInfo.m_iEventID) {
                TcnVendEventID.COMMAND_QUERY_PARAMETERS -> {}
                TcnVendEventID.COMMAND_SELECT_GOODS -> TcnUtilityUI.getToast(
                    this@MainAct,
                    "选货成功"
                )

                TcnVendEventID.COMMAND_SELECT_FAIL -> TcnUtilityUI.getToast(
                    this@MainAct,
                    "选货失败"
                )

                TcnVendEventID.COMMAND_SHIPPING -> {
                    if ((cEventInfo.m_lParam4 != null) && ((cEventInfo.m_lParam4).length > 0)) {
                        if (m_OutDialog == null) {
                            m_OutDialog = OutDialog(
                                this@MainAct,
                                cEventInfo.m_lParam1.toString(),
                                cEventInfo.m_lParam4
                            )
                        } else {
                            m_OutDialog!!.setText(cEventInfo.m_lParam4)
                        }
                        m_OutDialog!!.cleanData()
                    } else {
                        if (m_OutDialog == null) {
                            m_OutDialog = OutDialog(
                                this@MainAct,
                                cEventInfo.m_lParam1.toString(),
                                getString(R.string.ui_base_notify_shipping)
                            )
                        } else {
                            m_OutDialog!!.setText(this@MainAct.getString(R.string.ui_base_notify_shipping))
                        }
                    }
                    m_OutDialog!!.setNumber(cEventInfo.m_lParam1.toString())
                    m_OutDialog!!.show()
                }

                TcnVendEventID.CMD_CARD_CONSUMED_SUCCESS -> {
                    Log.d("VendListener", "VendEvent 刷卡成功: ")
                    TcnVendIF.getInstance().reqShip(
                        cEventInfo.m_lParam1,
                        0,
                        PayMethod.PAYMETHED_MDB_CARD,
                        "0.01",
                        System.currentTimeMillis().toString() + ""
                    )
                }

                TcnVendEventID.COMMAND_SHIPMENT_SUCCESS -> {
                    shipMsgContent!!.append("货道:").append(cEventInfo.m_lParam1).append("出货成功")
                        .append("\n")
                    shipContent!!.text = shipMsgContent.toString()
                    if (null != m_OutDialog) {
                        m_OutDialog!!.cancel()
                    }
                    if (m_LoadingDialog == null) {
                        m_LoadingDialog = LoadingDialog(
                            this@MainAct,
                            getString(R.string.ui_base_notify_shipment_success),
                            getString(R.string.ui_base_notify_receive_goods)
                        )
                    } else {
                        m_LoadingDialog!!.setLoadText(getString(R.string.ui_base_notify_shipment_success))
                        m_LoadingDialog!!.setTitle(getString(R.string.ui_base_notify_receive_goods))
                    }
                    m_LoadingDialog!!.setShowTime(3)
                    m_LoadingDialog!!.show()
                }

                TcnVendEventID.COMMAND_SHIPMENT_FAILURE -> {
                    shipMsgContent!!.append("货道:").append(cEventInfo.m_lParam1).append("出货失败")
                        .append("\n")
                    shipContent!!.text = shipMsgContent.toString()
                    if (null != m_OutDialog) {
                        m_OutDialog!!.cancel()
                    }
                    if (null == m_LoadingDialog) {
                        m_LoadingDialog = LoadingDialog(
                            this@MainAct,
                            getString(R.string.ui_base_notify_shipment_fail),
                            getString(R.string.ui_base_notify_contact_merchant)
                        )
                    }
                    m_LoadingDialog!!.setLoadText(getString(R.string.ui_base_notify_shipment_fail))
                    m_LoadingDialog!!.setTitle(getString(R.string.ui_base_notify_contact_merchant))
                    m_LoadingDialog!!.setShowTime(3)
                    m_LoadingDialog!!.show()
                }

                TcnVendEventID.CMD_QUERY_STATUS_LIFTER -> if (cEventInfo.m_lParam1 == TcnVendEventResultID.STATUS_WAIT_TAKE_GOODS) {      //检测到取货口有货,提示客户取走商品
                    menu_lift_query_drive_fault!!.setButtonDisplayText(R.string.background_notify_receive_goods)
                    takeAwayGoodsMenu(cEventInfo, cEventInfo.m_lParam2, cEventInfo.m_lParam4)
                } else if (cEventInfo.m_lParam1 == TcnVendEventResultID.STATUS_FREE) {      //机器空闲状态（没有在运行）
                    menu_lift_query_drive_fault!!.setButtonDisplayText(cEventInfo.m_lParam4)
                    if (m_LoadingDialog != null) {
                        m_LoadingDialog!!.dismiss()
                    }
                } else if (cEventInfo.m_lParam1 == TcnVendEventResultID.STATUS_HEATING_START) {     //开始加热
                    if (null == m_OutDialog) {
                        m_OutDialog = OutDialog(this@MainAct, "", cEventInfo.m_lParam4)
                    }
                    m_OutDialog!!.setText(cEventInfo.m_lParam4)
                    m_OutDialog!!.show()
                } else if (cEventInfo.m_lParam1 == TcnVendEventResultID.STATUS_HEATING) {     //开始加热中
                    if ((m_OutDialog != null) && (!m_OutDialog!!.isShowing)) {
                        m_OutDialog!!.setText(getString(R.string.ui_base_tip_heating))
                        m_OutDialog!!.show()
                    }
                } else if (cEventInfo.m_lParam1 == TcnVendEventResultID.STATUS_HEATING_END) {     //加热完成
                    if (m_OutDialog != null) {
                        m_OutDialog!!.setSyTime(0, "")
                        m_OutDialog!!.setText(cEventInfo.m_lParam4)
                        m_OutDialog!!.show()
                    }
                } else if (TcnVendEventResultID.CMD_NO_DATA_RECIVE == cEventInfo.m_lParam1) {
                    if (m_OutDialog != null) {
                        m_OutDialog!!.dismiss()
                    }
                    TcnUtilityUI.getToast(
                        this@MainAct,
                        getString(R.string.background_drive_check_seriport)
                    )
                } else {
                }

                TcnVendEventID.CMD_TAKE_GOODS_FIRST -> if (cEventInfo.m_lParam1 == TcnVendEventResultID.STATUS_WAIT_TAKE_GOODS) {
                    takeAwayGoodsMenuTips(cEventInfo, cEventInfo.m_lParam2, cEventInfo.m_lParam4)
                } else if (cEventInfo.m_lParam1 == TcnVendEventResultID.STATUS_FREE) {      //机器空闲状态（没有在运行）
                    menu_lift_query_drive_fault!!.setButtonDisplayText(cEventInfo.m_lParam4)
                    if (m_LoadingDialog != null) {
                        m_LoadingDialog!!.dismiss()
                    }
                } else {
                }

                TcnVendEventID.CMD_LIFTER_UP -> if (TcnVendEventResultID.DO_START == cEventInfo.m_lParam1) {
                    setDialogShow()
                } else if (TcnVendEventResultID.DO_END == cEventInfo.m_lParam1) {
                    if (m_OutDialog != null) {
                        m_OutDialog!!.dismiss()
                    }
                } else if (TcnVendEventResultID.CMD_NO_DATA_RECIVE == cEventInfo.m_lParam1) {
                    if (m_OutDialog != null) {
                        m_OutDialog!!.dismiss()
                    }
                    TcnUtilityUI.getToast(
                        this@MainAct,
                        getString(R.string.background_drive_check_seriport)
                    )
                } else {
                }

                TcnVendEventID.CMD_CLEAN_FAULTS -> if (TcnVendEventResultID.STATUS_FREE == cEventInfo.m_lParam1) {
                    menu_lift_clear_drive_fault!!.setButtonDisplayText(cEventInfo.m_lParam4)
                } else if (TcnVendEventResultID.STATUS_BUSY == cEventInfo.m_lParam1) {
                    menu_lift_clear_drive_fault!!.setButtonDisplayText(R.string.background_notify_sys_busy)
                } else if (TcnVendEventResultID.STATUS_WAIT_TAKE_GOODS == cEventInfo.m_lParam1) {
                    menu_lift_clear_drive_fault!!.setButtonDisplayText(R.string.background_notify_receive_goods)
                } else if (TcnVendEventResultID.CMD_NO_DATA_RECIVE == cEventInfo.m_lParam1) {
                    if (m_OutDialog != null) {
                        m_OutDialog!!.dismiss()
                    }
                    TcnUtilityUI.getToast(
                        this@MainAct,
                        getString(R.string.background_drive_check_seriport)
                    )
                } else {
                }

                TcnVendEventID.CMD_QUERY_PARAMETERS -> {}
                TcnVendEventID.CMD_SET_LIGHT_OUT_STEP -> if (TcnVendEventResultID.CMD_NO_DATA_RECIVE == cEventInfo.m_lParam1) {
                    if (m_OutDialog != null) {
                        m_OutDialog!!.dismiss()
                    }
                    TcnUtilityUI.getToast(
                        this@MainAct,
                        getString(R.string.background_drive_check_seriport)
                    )
                } else {
                }

                TcnVendEventID.CMD_SET_PARAMETERS -> {
                    if (TcnVendEventResultID.CMD_NO_DATA_RECIVE == cEventInfo.m_lParam1) {
                        if (m_OutDialog != null) {
                            m_OutDialog!!.dismiss()
                        }
                        TcnUtilityUI.getToast(
                            this@MainAct,
                            getString(R.string.background_drive_check_seriport)
                        )
                    } else {
                    }
                    if (cEventInfo.m_lParam1 == 25) { // 温度设置
                    }
                }

                TcnVendEventID.CMD_REQ_PERMISSION -> {
                    TcnUtilityUI.getToast(this@MainAct, "请选择确定")
                    ActivityCompat.requestPermissions(
                        this@MainAct,
                        TcnVendIF.getInstance().getPermission(cEventInfo.m_lParam4),
                        126
                    )
                }

                TcnVendEventID.MDB_RECIVE_COIN_MONEY -> {
                    val strBalance = TcnVendIF.getInstance().balance //余额  Balance
                    Log.d("VendListener", "strBalance: $strBalance")
                    TcnUtilityUI.getToast(this@MainAct, cEventInfo.m_lParam4 + "元", 20)
                        .show() //yuan
                }

                TcnVendEventID.CMD_QUERY_SWIPE_STATUS -> Log.d(
                    "VendListener",
                    "MDB_RECIVE_COIN_MONEY: " + cEventInfo.m_lParam1
                )

                TcnVendEventID.CMD_GET_DISTANCE -> {}
                TcnVendEventID.CMD_GET_DISTANCE_2 -> {}
                TcnVendEventID.CMD_NO_DATA_RECIVE -> {}
                TcnVendEventID.CMD_READ_DOOR_STATUS -> if (TcnVendEventResultID.DO_CLOSE == cEventInfo.m_lParam1) {
                    TcnUtilityUI.getToast(this@MainAct, "关门")
                } else if (TcnVendEventResultID.DO_OPEN == cEventInfo.m_lParam1) {
                    TcnUtilityUI.getToast(this@MainAct, "开门")
                } else {
                }

                TcnVendEventID.TEMPERATURE_INFO -> if (!TextUtils.isEmpty(cEventInfo.m_lParam4)) {
                    if (m_Titlebar != null) {
                        m_Titlebar!!.setButtonName(cEventInfo.m_lParam4)
                    }
                }

                TcnVendEventID.CMD_QUERY_PICKUP_STATUS -> Log.i(TAG, "CMD_QUERY_PICKUP_STATUS")
                TcnVendEventID.CMD_START_CARD_PAY ->                     //开始刷卡支付
                    Log.i(TAG, "CMD_START_CARD_PAY---Start swiping your card ")

                TcnVendEventID.CMD_CARD_CONSUMED_FAIL ->                     //刷卡异常
                    Log.i(TAG, "CMD_CARD_CONSUMED_FAIL---failure")

                TcnVendEventID.CMD_LIFTER_BACK_HOME -> Log.d(TAG, "CMD_LIFTER_BACK_HOME")
                TcnVendEventID.CMD_CLAPBOARD_SWITCH -> Log.d(TAG, "CMD_CLAPBOARD_SWITCH")
                TcnVendEventID.CMD_OPEN_COOL -> Log.d(TAG, "CMD_OPEN_COOL")
                TcnVendEventID.CMD_OPEN_HEAT -> Log.d(TAG, "CMD_OPEN_HEAT")
                TcnVendEventID.CMD_CLOSE_COOL_HEAT -> Log.d(TAG, "CMD_CLOSE_COOL_HEAT")
                TcnVendEventID.CMD_FACTORY_RESET -> Log.d(
                    TAG,
                    "CMD_FACTORY_RESET---" + cEventInfo.m_lParam1
                )

                TcnVendEventID.CMD_QUERY_DRIVER_CMD -> Log.d(TAG, "CMD_QUERY_DRIVER_CMD")
                TcnVendEventID.CMD_SET_SWITCH_OUTPUT_STATUS -> Log.d(
                    TAG,
                    "CMD_SET_SWITCH_OUTPUT_STATUS"
                )

                else -> {}
            }
        }
    }

    companion object {
        private const val TAG = "MainAct"

        private const val CMD_SET_LIFT_RESTORE_FACTORY = 50
        private const val CMD_SET_LIFT_ID = 51
        private const val CMD_SET_LIFT_LIGHT_OUT_STEPS = 52
        private const val CMD_SET_PARAMETERS = 53

        private const val CMD_SET_SLOTNO_SPRING = 55
        private const val CMD_SET_SLOTNO_BELTS = 56
        private const val CMD_SET_SLOTNO_ALL_SPRING = 57
        private const val CMD_SET_SLOTNO_ALL_BELT = 58
        private const val CMD_SET_SLOTNO_SINGLE = 59
        private const val CMD_SET_SLOTNO_DOUBLE = 60
        private const val CMD_SET_SLOTNO_ALL_SINGLE = 61
        private const val CMD_SET_TEST_MODE = 62
        private const val CMD_TEMP_CONTROL_SELECT = 63
        private const val CMD_SET_GLASS_HEAT_OPEN = 64
        private const val CMD_SET_GLASS_HEAT_CLOSE = 65
    }
}
