package com.tcn.sdk.lifthefansxdemo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.gson.JsonObject
import com.tcn.lifthefansxboard.control.ConstantsDrive5inch
import com.tcn.lifthefansxboard.control.MessageFromDrive
import com.tcn.lifthefansxboard.control.MessageFromUI0203Crc
import com.tcn.lifthefansxboard.control.PayMethod
import com.tcn.lifthefansxboard.control.ShipSlotInfo
import com.tcn.lifthefansxboard.control.TcnDriveCmdType
import com.tcn.lifthefansxboard.control.TcnDrivesConstant
import com.tcn.lifthefansxboard.control.TcnShareUseData
import com.tcn.lifthefansxboard.control.TcnVendEventID
import com.tcn.lifthefansxboard.control.TcnVendEventResultID
import com.tcn.lifthefansxboard.control.TcnVendIF
import com.tcn.lifthefansxboard.control.TcnVendIF.VendEventListener
import com.tcn.lifthefansxboard.control.VendEventInfo
import com.tcn.sdk.lifthefansxdemo.Titlebar.TitleBarListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MenuSettingsStandJsActivity : TcnMainActivity() {
    private var singleitem = 0
    private var m_OutDialog: OutDialog? = null
    private var m_Titlebar: Titlebar? = null
    private var menu_ys_light_check: ButtonSwitch? = null

    private var menu_ys_query_drive_fault: ButtonEditSelectD? = null
    private var menu_ys_clear_drive_fault: ButtonEditSelectD? = null
    private var menu_ys_action: ButtonEditSelectD? = null
    private var menu_ys_query_drive_info: ButtonEditSelectD? = null
    private var menu_ys_query_param: ButtonEditSelectD? = null
    private var menu_ys_set_param_select: ButtonEditSelectD? = null

    private var tv_read_cmd: TextView? = null
    private var tv_read_string: TextView? = null
    private var main_serport: Button? = null
    private var menu_lift_ship_slot_test: ButtonEditSelectD? = null
    private var menu_lift_ship_slot: ButtonEditSelectD? = null
    private var menu_lift_reqselect: ButtonEditSelectD? = null
    private var menu_ys_query_drive_slot_info: ButtonEditSelectD? = null
    private var m_LoadingDialog: LoadingDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.background_menu_settings_layout_standjs_board)
        TcnVendIF.getInstance().LoggerDebug(TAG, "onCreate()")
        initView()
    }

    override fun onResume() {
        super.onResume()
        TcnVendIF.getInstance().LoggerDebug(TAG, "onResume()")
        EventBus.getDefault().register(this)
        TcnVendIF.getInstance().registerListener(m_vendListener)
    }

    override fun onPause() {
        super.onPause()
        TcnVendIF.getInstance().LoggerDebug(TAG, "onPause()")
        EventBus.getDefault().unregister(this)
        TcnVendIF.getInstance().unregisterListener(m_vendListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        TcnVendIF.getInstance().LoggerDebug(TAG, "onDestroy()")

        m_vendListener = null
        if (m_Titlebar != null) {
            m_Titlebar!!.removeButtonListener()
            m_Titlebar = null
        }
        if (main_serport != null) {
            main_serport!!.setOnClickListener(null)
            main_serport = null
        }

        if (menu_ys_query_drive_slot_info != null) {
            menu_ys_query_drive_slot_info!!.removeButtonListener()
            menu_ys_query_drive_slot_info!!.setOnClickListener(null)
            menu_ys_query_drive_slot_info = null
        }

        if (menu_ys_light_check != null) {
            menu_ys_light_check!!.removeButtonListener()
            menu_ys_light_check = null
        }
        if (menu_ys_query_drive_fault != null) {
            menu_ys_query_drive_fault!!.removeButtonListener()
            menu_ys_query_drive_fault = null
        }
        if (menu_ys_clear_drive_fault != null) {
            menu_ys_clear_drive_fault!!.removeButtonListener()
            menu_ys_clear_drive_fault = null
        }

        if (menu_ys_query_drive_info != null) {
            menu_ys_query_drive_info!!.removeButtonListener()
            menu_ys_query_drive_info = null
        }

        if (menu_ys_action != null) {
            menu_ys_action!!.removeButtonListener()
            menu_ys_action = null
        }

        if (menu_ys_query_param != null) {
            menu_ys_query_param!!.removeButtonListener()
            menu_ys_query_param = null
        }

        if (menu_ys_set_param_select != null) {
            menu_ys_set_param_select!!.removeButtonListener()
            menu_ys_set_param_select = null
        }
        if (m_OutDialog != null) {
            m_OutDialog!!.deInit()
            m_OutDialog = null
        }

        m_TitleBarListener = null
    }

    private fun initView() {
        main_serport = findViewById<View>(R.id.main_serport) as Button
        main_serport!!.setOnClickListener(m_ButtonClickListener)

        m_Titlebar = findViewById<View>(R.id.menu_setttings_titlebar) as Titlebar
        if (m_Titlebar != null) {
            m_Titlebar!!.setButtonType(Titlebar.BUTTON_TYPE_BACK)
            m_Titlebar!!.setButtonName(R.string.background_menu_settings)
            m_Titlebar!!.setTitleBarListener(m_TitleBarListener)
        }

        menu_ys_light_check = findViewById<View>(R.id.menu_ys_light_check) as ButtonSwitch
        if (menu_ys_light_check != null) {
            menu_ys_light_check!!.setButtonName(R.string.background_menu_drop_sensor_whole)
            menu_ys_light_check!!.setButtonListener(m_SwitchButtonListener)
            menu_ys_light_check!!.setTextSize(TcnVendIF.getInstance().getFitScreenSize(22))
            menu_ys_light_check!!.setSwitchState(TcnShareUseData.getInstance().isDropSensorCheck)
            menu_ys_light_check!!.visibility = View.GONE
        }

        menu_lift_ship_slot_test =
            findViewById<View>(R.id.menu_lift_ship_slot_test) as ButtonEditSelectD
        if (menu_lift_ship_slot_test != null) {
            menu_lift_ship_slot_test!!.setButtonType(ButtonEditSelectD.BUTTON_TYPE_EDIT_QUERY)
            menu_lift_ship_slot_test!!.setButtonName("Sinov yuk yo‘li")
            menu_lift_ship_slot_test!!.setButtonNameTextSize(
                TcnVendIF.getInstance().getFitScreenSize(20)
            )
            menu_lift_ship_slot_test!!.setButtonQueryText("Sinov")
            menu_lift_ship_slot_test!!.setButtonQueryTextSize(
                TcnVendIF.getInstance().getFitScreenSize(16)
            )
            menu_lift_ship_slot_test!!.setButtonQueryTextColor("#ffffff")
            menu_lift_ship_slot_test!!.setButtonDisplayTextColor("#4e5d72")
            menu_lift_ship_slot_test!!.setInputTypeInput(InputType.TYPE_CLASS_NUMBER)
            menu_lift_ship_slot_test!!.setButtonListener(m_ButtonEditClickListener)
        }

        menu_lift_ship_slot = findViewById<View>(R.id.menu_lift_ship_slot) as ButtonEditSelectD
        if (menu_lift_ship_slot != null) {
            menu_lift_ship_slot!!.setButtonType(ButtonEditSelectD.BUTTON_TYPE_EDIT_QUERY)
            menu_lift_ship_slot!!.setButtonName("Mahsulot chiqarish misoli")
            menu_lift_ship_slot!!.setButtonNameTextSize(
                TcnVendIF.getInstance().getFitScreenSize(20)
            )
            menu_lift_ship_slot!!.setButtonQueryText("Mahsulot chiqarish")
            menu_lift_ship_slot!!.setButtonQueryTextSize(
                TcnVendIF.getInstance().getFitScreenSize(16)
            )
            menu_lift_ship_slot!!.setButtonQueryTextColor("#ffffff")
            menu_lift_ship_slot!!.setButtonDisplayTextColor("#4e5d72")
            menu_lift_ship_slot!!.setInputTypeInput(InputType.TYPE_CLASS_NUMBER)
            menu_lift_ship_slot!!.setButtonListener(m_ButtonEditClickListener)
        }

        menu_lift_reqselect = findViewById<View>(R.id.menu_lift_reqselect) as ButtonEditSelectD
        if (menu_lift_reqselect != null) {
            menu_lift_reqselect!!.setButtonType(ButtonEditSelectD.BUTTON_TYPE_EDIT_QUERY)
            menu_lift_reqselect!!.setButtonName("Yuk yo‘lini tanlash")
            menu_lift_reqselect!!.setButtonNameTextSize(
                TcnVendIF.getInstance().getFitScreenSize(20)
            )
            menu_lift_reqselect!!.setButtonQueryText("Tanlash")
            menu_lift_reqselect!!.setButtonQueryTextSize(
                TcnVendIF.getInstance().getFitScreenSize(16)
            )
            menu_lift_reqselect!!.setButtonQueryTextColor("#ffffff")
            menu_lift_reqselect!!.setButtonDisplayTextColor("#4e5d72")
            menu_lift_reqselect!!.setInputTypeInput(InputType.TYPE_CLASS_NUMBER)
            menu_lift_reqselect!!.setButtonListener(m_ButtonEditClickListener)
        }

        menu_ys_query_drive_slot_info = findViewById(R.id.menu_ys_query_drive_slot_info)
        if (menu_ys_query_drive_slot_info != null) {
            menu_ys_query_drive_slot_info!!.setButtonType(ButtonEditSelectD.BUTTON_TYPE_QUERY)
            menu_ys_query_drive_slot_info!!.setButtonName("Yuk yo‘lini so‘rash")
            menu_ys_query_drive_slot_info!!.setButtonNameTextSize(
                TcnVendIF.getInstance().getFitScreenSize(20)
            )
            menu_ys_query_drive_slot_info!!.setButtonQueryText(R.string.background_drive_query)
            menu_ys_query_drive_slot_info!!.setButtonQueryTextColor("#ffffff")
            menu_ys_query_drive_slot_info!!.setButtonDisplayTextColor("#4e5d72")
            menu_ys_query_drive_slot_info!!.setButtonListener(m_ButtonEditClickListener)
        }

        menu_ys_query_drive_fault =
            findViewById<View>(R.id.menu_ys_query_drive_fault) as ButtonEditSelectD
        if (menu_ys_query_drive_fault != null) {
//			menu_ys_query_drive_fault.setButtonType(ButtonEditSelectD.BUTTON_TYPE_QUERY);
            menu_ys_query_drive_fault!!.setButtonType(ButtonEditSelectD.BUTTON_TYPE_SELECT_SECOND_QUERY)

            menu_ys_query_drive_fault!!.setButtonName(getString(R.string.background_drive_query_fault))
            menu_ys_query_drive_fault!!.setButtonNameTextSize(
                TcnVendIF.getInstance().getFitScreenSize(20)
            )
            menu_ys_query_drive_fault!!.setButtonQueryText(getString(R.string.background_drive_query))
            menu_ys_query_drive_fault!!.setButtonQueryTextColor("#ffffff")
            menu_ys_query_drive_fault!!.setButtonDisplayTextColor("#4e5d72")
            menu_ys_query_drive_fault!!.setButtonListener(m_ButtonEditClickListener)
        }


        menu_ys_clear_drive_fault =
            findViewById<View>(R.id.menu_ys_clear_drive_fault) as ButtonEditSelectD
        if (menu_ys_clear_drive_fault != null) {
            menu_ys_clear_drive_fault!!.setButtonType(ButtonEditSelectD.BUTTON_TYPE_QUERY)

            menu_ys_clear_drive_fault!!.setButtonName(getString(R.string.background_drive_clean_fault))
            menu_ys_clear_drive_fault!!.setButtonNameTextSize(
                TcnVendIF.getInstance().getFitScreenSize(20)
            )
            menu_ys_clear_drive_fault!!.setButtonQueryText(R.string.background_drive_clean)
            menu_ys_clear_drive_fault!!.setButtonQueryTextColor("#ffffff")
            menu_ys_clear_drive_fault!!.setButtonDisplayTextColor("#4e5d72")
            menu_ys_clear_drive_fault!!.setButtonListener(m_ButtonEditClickListener)
        }

        menu_ys_query_drive_info =
            findViewById<View>(R.id.menu_ys_query_drive_info) as ButtonEditSelectD
        if (menu_ys_query_drive_info != null) {
            menu_ys_query_drive_info!!.setButtonType(ButtonEditSelectD.BUTTON_TYPE_QUERY)

            menu_ys_query_drive_info!!.setButtonName("Drayver platasi dastur versiyasini so‘rash")
            menu_ys_query_drive_info!!.setButtonNameTextSize(
                TcnVendIF.getInstance().getFitScreenSize(20)
            )
            menu_ys_query_drive_info!!.setButtonQueryText(R.string.background_drive_query)
            menu_ys_query_drive_info!!.setButtonQueryTextColor("#ffffff")
            menu_ys_query_drive_info!!.setButtonDisplayTextColor("#4e5d72")
            menu_ys_query_drive_info!!.setButtonListener(m_ButtonEditClickListener)
        }

        menu_ys_action = findViewById<View>(R.id.menu_ys_action) as ButtonEditSelectD
        if (menu_ys_action != null) {
            menu_ys_action!!.setButtonType(ButtonEditSelectD.BUTTON_TYPE_SELECT_SECOND_INPUT_QUERY)

            menu_ys_action!!.setButtonQueryText(getString(R.string.background_lift_do_start))
            menu_ys_action!!.setButtonQueryTextColor("#ffffff")
            menu_ys_action!!.setButtonDisplayTextColor("#4e5d72")
            menu_ys_action!!.setButtonQueryTextSize(TcnVendIF.getInstance().getFitScreenSize(16))
            menu_ys_action!!.setInputTypeInput(InputType.TYPE_CLASS_NUMBER)
            menu_ys_action!!.setButtonListener(m_ButtonEditClickListener)
        }

        menu_ys_query_param = findViewById<View>(R.id.menu_ys_query_param) as ButtonEditSelectD
        if (menu_ys_query_param != null) {
            menu_ys_query_param!!.setButtonType(ButtonEditSelectD.BUTTON_TYPE_SELECT_SECOND_QUERY)

            menu_ys_query_param!!.setButtonNameTextSize(
                TcnVendIF.getInstance().getFitScreenSize(20)
            )
            menu_ys_query_param!!.setButtonName(getString(R.string.background_lift_query_params))
            menu_ys_query_param!!.setButtonQueryText(getString(R.string.background_drive_query))
            menu_ys_query_param!!.setButtonQueryTextColor("#ffffff")
            menu_ys_query_param!!.setButtonDisplayTextColor("#4e5d72")
            menu_ys_query_param!!.setButtonQueryTextSize(
                TcnVendIF.getInstance().getFitScreenSize(16)
            )
            menu_ys_query_param!!.setButtonEditTextSize(
                TcnVendIF.getInstance().getFitScreenSize(16)
            )
            menu_ys_query_param!!.setButtonEditTextSizeSecond(
                TcnVendIF.getInstance().getFitScreenSize(16)
            )
            menu_ys_query_param!!.setInputTypeInput(InputType.TYPE_CLASS_NUMBER)
            menu_ys_query_param!!.setButtonListener(m_ButtonEditClickListener)
        }

        menu_ys_set_param_select =
            findViewById<View>(R.id.menu_ys_set_param_select) as ButtonEditSelectD
        if (menu_ys_set_param_select != null) {
            menu_ys_set_param_select!!.setButtonType(ButtonEditSelectD.BUTTON_TYPE_SELECT_SECOND_INPUT_QUERY)

            menu_ys_set_param_select!!.setButtonNameTextSize(
                TcnVendIF.getInstance().getFitScreenSize(20)
            )
            menu_ys_set_param_select!!.setButtonName(getString(R.string.background_lift_set_params))
            menu_ys_set_param_select!!.setButtonQueryText(getString(R.string.background_drive_set))
            menu_ys_set_param_select!!.setButtonQueryTextColor("#ffffff")
            menu_ys_set_param_select!!.setButtonDisplayTextColor("#4e5d72")
            menu_ys_set_param_select!!.setButtonQueryTextSize(
                TcnVendIF.getInstance().getFitScreenSize(16)
            )
            menu_ys_set_param_select!!.setButtonEditTextSize(
                TcnVendIF.getInstance().getFitScreenSize(16)
            )
            menu_ys_set_param_select!!.setButtonEditTextSizeSecond(
                TcnVendIF.getInstance().getFitScreenSize(16)
            )
            menu_ys_set_param_select!!.setInputTypeInput(InputType.TYPE_CLASS_NUMBER)
            menu_ys_set_param_select!!.setButtonListener(m_ButtonEditClickListener)
        }

        tv_read_cmd = findViewById(R.id.tv_read_cmd)
        tv_read_string = findViewById(R.id.tv_read_string)
    }

    private fun setDialogShow() {
        if (m_OutDialog == null) {
            m_OutDialog = OutDialog(
                this@MenuSettingsStandJsActivity,
                "",
                getString(R.string.background_drive_setting)
            )
            m_OutDialog!!.setShowTime(10000)
        }

        if (m_OutDialog != null) {
            m_OutDialog!!.setShowTime(3)
            m_OutDialog!!.setTitle(getString(R.string.background_tip_wait_amoment))
            m_OutDialog!!.show()
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
        if ((selectData != null) && (!selectData.isEmpty())) {
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

    private var m_TitleBarListener: MenuSetTitleBarListener? = MenuSetTitleBarListener()

    private inner class MenuSetTitleBarListener : TitleBarListener {
        override fun onClick(v: View?, buttonId: Int) {
            if (Titlebar.BUTTON_ID_BACK == buttonId) {
                this@MenuSettingsStandJsActivity.finish()
            }
        }
    }

    private val m_SwitchButtonListener: SwitchButtonListener = SwitchButtonListener()

    private inner class SwitchButtonListener : ButtonSwitch.ButtonListener {
        override fun onSwitched(v: View?, isSwitchOn: Boolean) {
            val iId = v?.id
            if (R.id.menu_ys_light_check == iId) {
                TcnShareUseData.getInstance().isDropSensorCheck = isSwitchOn
            } else {
            }
        }
    }

    private val m_ButtonEditClickListener: ButtonEditClickListener = ButtonEditClickListener()

    private inner class ButtonEditClickListener : ButtonEditSelectD.ButtonListener {
        override fun onClick(v: View?, buttonId: Int) {
            if (null == v) {
                return
            }
            if (TcnUtilityUI.isFastClick) {
                return
            }
            val id = v.id
            if (R.id.menu_ys_query_drive_fault == id) {
                if (ButtonEditSelectD.BUTTON_ID_QUERY == buttonId) {
                    menu_ys_query_drive_fault!!.setButtonDisplayText("")
                    var strParamSecond = menu_ys_query_drive_fault!!.buttonEditTextSecond
                    if ((null == strParamSecond) || (strParamSecond.isEmpty())) {
                        TcnUtilityUI.getToast(
                            this@MenuSettingsStandJsActivity,
                            getString(R.string.background_lift_tips_select_floor_no)
                        )
                    } else {
                        if (strParamSecond.contains("~")) {
                            val index = strParamSecond.indexOf("~")
                            strParamSecond = strParamSecond.substring(0, index).trim { it <= ' ' }
                        }
                        EventBus.getDefault().post(
                            MessageFromUI0203Crc(
                                TcnDrivesConstant.BOARD_STAND_BOARD_JS,
                                0,
                                TcnDriveCmdType.CMD_QUERY_STATUS,
                                strParamSecond.toInt(),
                                -1,
                                -1,
                                -1,
                                false,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null
                            )
                        )
                    }
                } else if (ButtonEditSelectD.BUTTON_ID_SELECT == buttonId) {
//					showSelectDialog(-1,getString(R.string.background_drive_tips_select_cabinetno),menu_ys_query_drive_fault.getButtonEdit(), "",UIComBack.getInstance().getGroupListElevatorShow());
                } else if (ButtonEditSelectD.BUTTON_ID_SELECT_SECOND == buttonId) {
                    showSelectDialog(
                        -1,
                        getString(R.string.background_please_choose),
                        menu_ys_query_drive_fault!!.buttonEditSecond!!,
                        "",
                        TcnParamStandJs.STAND_ITEM_QUERY
                    )
                } else {
                }
            } else if (R.id.menu_lift_ship_slot_test == id) {
                if (ButtonEditSelectD.BUTTON_ID_QUERY == buttonId) {
                    val strParam = menu_lift_ship_slot_test!!.buttonEditInputText
                    if ((null == strParam) || (strParam.isEmpty())) {
                        TcnUtilityUI.getToast(
                            this@MenuSettingsStandJsActivity,
                            getString(R.string.background_drive_tips_input_slotno)
                        )
                    } else {
                        if (TcnVendIF.getInstance().isDigital(strParam)) {
                            val heatSeconds = 0 //isitish vaqti, birligi soniya, max 300 soniya
                            TcnVendIF.getInstance().reqShipTest(strParam.toInt(), heatSeconds)
                        } else {
                            TcnUtilityUI.getToast(
                                this@MenuSettingsStandJsActivity,
                                getString(R.string.background_drive_tips_input_slotno)
                            )
                        }
                    }
                }
            } else if (R.id.menu_lift_ship_slot == id) {
                if (ButtonEditSelectD.BUTTON_ID_QUERY == buttonId) {
                    val strParam = menu_lift_ship_slot!!.buttonEditInputText
                    if ((null == strParam) || (strParam.isEmpty())) {
                        TcnUtilityUI.getToast(
                            this@MenuSettingsStandJsActivity,
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
            } else if (R.id.menu_lift_reqselect == id) {
                if (ButtonEditSelectD.BUTTON_ID_QUERY == buttonId) {
                    val strParam = menu_lift_reqselect!!.buttonEditInputText
                    if ((null == strParam) || (strParam.isEmpty())) {
                        TcnUtilityUI.getToast(
                            this@MenuSettingsStandJsActivity,
                            getString(R.string.background_drive_tips_input_slotno)
                        )
                    } else {
                        TcnVendIF.getInstance().reqSelectSlotNo(strParam.toInt())
                    }
                }
            } else if (R.id.menu_ys_query_drive_info == id) {
                if (ButtonEditSelectD.BUTTON_ID_QUERY == buttonId) {
                    menu_ys_query_drive_info!!.setButtonDisplayText("")
                    EventBus.getDefault().post(
                        MessageFromUI0203Crc(
                            TcnDrivesConstant.BOARD_STAND_BOARD_JS,
                            0,
                            TcnDriveCmdType.CMD_QUERY_MACHINE_INFO,
                            -1,
                            -1,
                            -1,
                            -1,
                            false,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null
                        )
                    )
                }
            } else if (R.id.menu_ys_query_drive_slot_info == id) {
                TcnVendIF.getInstance().reqQueryWorkStatus(-1, 2)
            } else if (R.id.menu_ys_action == id) {
                // 动作执行
                if (ButtonEditSelectD.BUTTON_ID_QUERY == buttonId) {
                    menu_ys_action!!.setButtonDisplayText("")
                    val strParamSecond = menu_ys_action!!.buttonEditTextSecond
                    if ((null == strParamSecond) || (strParamSecond.isEmpty())) {
                        TcnUtilityUI.getToast(
                            this@MenuSettingsStandJsActivity,
                            getString(R.string.background_lift_tips_select_floor_no)
                        )
                    } else {
                        val jsonObjectParam: JsonObject? = null
                        if (strParamSecond.contains("~")) {
                            val index = strParamSecond.indexOf("~")

                            /* int indexNext = strParamSecond.indexOf("~", index + 1);
                            String paramKey = strParamSecond.substring(0, index).trim();
                            String paramValue = strParamSecond.substring(index + 1, indexNext).trim();

                            TcnBoardIF.getInstance().LoggerDebug(TAG, "menu_ys_query_param paramKey: " + paramKey + " paramValue: " + paramValue);

                            jsonObjectParam = new JsonObject();
                            jsonObjectParam.addProperty(paramKey, paramValue); */
                            val strDoType = strParamSecond.substring(0, index).trim { it <= ' ' }
                            if (TcnVendIF.getInstance().isNumeric(strDoType)) {
                                val doType = strDoType.toInt()
                                EventBus.getDefault().post(
                                    MessageFromUI0203Crc(
                                        TcnDrivesConstant.BOARD_STAND_BOARD_JS,
                                        0,
                                        TcnDriveCmdType.CMD_ACTION_DO,
                                        doType,
                                        -1,
                                        -1,
                                        -1,
                                        false,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        jsonObjectParam
                                    )
                                )
                            } else if (strParamSecond.contains("TestSelectGoods~")) {
                                val strSlotNo = menu_ys_action!!.buttonEditInputText
                                if (TcnVendIF.getInstance().isDigital(strSlotNo)) {
                                    EventBus.getDefault().post(
                                        MessageFromUI0203Crc(
                                            TcnDrivesConstant.BOARD_STAND_BOARD_JS,
                                            0,
                                            TcnDriveCmdType.CMD_SHOW_PAGE,
                                            2,
                                            strSlotNo?.toInt() ?: 1,
                                            -1,
                                            -1,
                                            false,
                                            null,
                                            null,
                                            null,
                                            null,
                                            null,
                                            jsonObjectParam
                                        )
                                    )
                                } else {
                                    EventBus.getDefault().post(
                                        MessageFromUI0203Crc(
                                            TcnDrivesConstant.BOARD_STAND_BOARD_JS,
                                            0,
                                            TcnDriveCmdType.CMD_SHOW_PAGE,
                                            2,
                                            -1,
                                            -1,
                                            -1,
                                            false,
                                            null,
                                            null,
                                            null,
                                            null,
                                            null,
                                            jsonObjectParam
                                        )
                                    )
                                }
                            } else if (strParamSecond.contains("TestShoppingCar~")) {
                                EventBus.getDefault().post(
                                    MessageFromUI0203Crc(
                                        TcnDrivesConstant.BOARD_STAND_BOARD_JS,
                                        0,
                                        TcnDriveCmdType.CMD_SHOW_PAGE,
                                        3,
                                        25,
                                        -1,
                                        -1,
                                        false,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        jsonObjectParam
                                    )
                                )
                            } else if (strParamSecond.contains("TestPickUpGoodsCode~")) {
                                EventBus.getDefault().post(
                                    MessageFromUI0203Crc(
                                        TcnDrivesConstant.BOARD_STAND_BOARD_JS,
                                        0,
                                        TcnDriveCmdType.CMD_SHOW_PAGE,
                                        4,
                                        -1,
                                        -1,
                                        -1,
                                        false,
                                        null,
                                        "12345678",
                                        null,
                                        null,
                                        null,
                                        jsonObjectParam
                                    )
                                )
                            } else if (strParamSecond.contains("TestWaitPay~")) {
                                EventBus.getDefault().post(
                                    MessageFromUI0203Crc(
                                        TcnDrivesConstant.BOARD_STAND_BOARD_JS,
                                        0,
                                        TcnDriveCmdType.CMD_SHOW_PAGE,
                                        5,
                                        -1,
                                        -1,
                                        -1,
                                        false,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        jsonObjectParam
                                    )
                                )
                            } else if (strParamSecond.contains("Title~")) {
                                EventBus.getDefault().post(
                                    MessageFromUI0203Crc(
                                        TcnDrivesConstant.BOARD_STAND_BOARD_JS,
                                        0,
                                        TcnDriveCmdType.CMD_SHOW_PAGE,
                                        6,
                                        -1,
                                        -1,
                                        -1,
                                        false,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        jsonObjectParam
                                    )
                                )
                            } else if (strParamSecond.contains("Tips~")) {
                                EventBus.getDefault().post(
                                    MessageFromUI0203Crc(
                                        TcnDrivesConstant.BOARD_STAND_BOARD_JS,
                                        0,
                                        TcnDriveCmdType.CMD_SHOW_PAGE,
                                        7,
                                        -1,
                                        -1,
                                        -1,
                                        false,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        jsonObjectParam
                                    )
                                )
                            } else if (strParamSecond.contains("Update~")) {
                                EventBus.getDefault().post(
                                    MessageFromUI0203Crc(
                                        TcnDrivesConstant.BOARD_STAND_BOARD_JS,
                                        0,
                                        TcnDriveCmdType.CMD_UPDATE_SOFT,
                                        -1,
                                        -1,
                                        -1,
                                        -1,
                                        false,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null
                                    )
                                )
                            } else {
                            }
                        }
                    }
                } else if (ButtonEditSelectD.BUTTON_ID_SELECT_SECOND == buttonId) {
                    showSelectDialog(
                        -1,
                        getString(R.string.background_please_choose),
                        menu_ys_action!!.buttonEditSecond!!,
                        "",
                        TcnParamStandJs.STAND_ITEM_ACTION
                    )
                }
            } else if (R.id.menu_ys_query_param == id) {
                if (ButtonEditSelectD.BUTTON_ID_QUERY == buttonId) {
                    menu_ys_query_param!!.setButtonDisplayText("")
                    val strParamSecond = menu_ys_query_param!!.buttonEditTextSecond
                    if ((null == strParamSecond) || (strParamSecond.isEmpty())) {
                        TcnUtilityUI.getToast(
                            this@MenuSettingsStandJsActivity,
                            getString(R.string.background_lift_tips_select_floor_no)
                        )
                    } else {
                        var jsonObjectParam: JsonObject? = null
                        if (strParamSecond.contains("~")) {
                            val index = strParamSecond.indexOf("~")
                            val indexNext = strParamSecond.indexOf("~", index + 1)
                            val paramKey = strParamSecond.substring(0, index).trim { it <= ' ' }
                            val paramValue =
                                strParamSecond.substring(index + 1, indexNext).trim { it <= ' ' }
                            var paramValueInt = -1
                            try {
                                paramValueInt = paramValue.toInt()
                            } catch (e: Exception) {
                                TcnUtilityUI.getToast(
                                    this@MenuSettingsStandJsActivity,
                                    getString(R.string.background_coffee_data_error)
                                )
                            }
                            TcnVendIF.getInstance().LoggerDebug(
                                TAG,
                                "menu_ys_query_param paramKey: $paramKey paramValue: $paramValue"
                            )

                            jsonObjectParam = JsonObject()
                            jsonObjectParam.addProperty(paramKey, paramValueInt)
                        }
                        EventBus.getDefault().post(
                            MessageFromUI0203Crc(
                                TcnDrivesConstant.BOARD_STAND_BOARD_JS,
                                0,
                                TcnDriveCmdType.CMD_QUERY_PARAMETERS,
                                -1,
                                -1,
                                -1,
                                -1,
                                false,
                                null,
                                null,
                                null,
                                null,
                                null,
                                jsonObjectParam
                            )
                        )
                    }
                } else if (ButtonEditSelectD.BUTTON_ID_SELECT == buttonId) {
//					showSelectDialog(-1,getString(R.string.background_drive_tips_select_cabinetno),menu_ys_query_param.getButtonEdit(), "",UIComBack.getInstance().getGroupListElevatorShow());
                } else if (ButtonEditSelectD.BUTTON_ID_SELECT_SECOND == buttonId) {
                    showSelectDialog(
                        -1,
                        getString(R.string.background_please_choose),
                        menu_ys_query_param!!.buttonEditSecond!!,
                        "",
                        TcnParamStandJs.STAND_ITEM_PARAM_QUERY
                    )
                } else {
                }
            } else if (R.id.menu_ys_set_param_select == id) {
                if (ButtonEditSelectD.BUTTON_ID_QUERY == buttonId) {
                    menu_ys_set_param_select!!.setButtonDisplayText("")
                    val strParamSecond = menu_ys_set_param_select!!.buttonEditTextSecond
                    if ((null == strParamSecond) || (strParamSecond.isEmpty())) {
                        TcnUtilityUI.getToast(
                            this@MenuSettingsStandJsActivity,
                            getString(R.string.background_lift_tips_select_floor_no)
                        )
                    } else {
                        var jsonObjectParam: JsonObject? = null
                        if (strParamSecond.contains("~")) {
                            val index = strParamSecond.indexOf("~")
                            val indexNext = strParamSecond.indexOf("~", index + 1)
                            val paramKey = strParamSecond.substring(0, index).trim { it <= ' ' }
                            var paramKey1: String? = null
                            val paramValue = menu_ys_set_param_select!!.buttonEditInputText
                            var paramValueint = -1
                            try {
                                paramValueint = paramValue?.toInt() ?: 1
                            } catch (e: Exception) {
                                TcnUtilityUI.getToast(
                                    this@MenuSettingsStandJsActivity,
                                    getString(R.string.background_coffee_data_error)
                                )
                            }

                            jsonObjectParam = JsonObject()
                            if (indexNext > 0) {
                                // 出现了两次
                                paramKey1 = strParamSecond.substring(index + 1, indexNext)
                                    .trim { it <= ' ' }
                                val jsonObjectParam1 = JsonObject()

                                jsonObjectParam1.addProperty(paramKey1, paramValueint)
                                jsonObjectParam.add(paramKey, jsonObjectParam1)
                            } else {
                                if (paramKey.startsWith("DRIVE")) {
                                    jsonObjectParam.addProperty(paramKey, paramValue)
                                } else if (paramKey.startsWith("COMB")) {
                                    jsonObjectParam.addProperty(paramKey, paramValue)
                                } else {
                                    if (TcnVendIF.getInstance().isDigital(paramValue)) {
                                        jsonObjectParam.addProperty(paramKey, paramValue)
                                    }
                                }
                            }

                            //							JsonObject jsonObjectParam1 = new JsonObject();
//							jsonObjectParam1.addProperty(paramKey1,paramValue1);
//							jsonObjectParam.addProperty(paramKey2,jsonObjectParam1.toString());
                        }
                        EventBus.getDefault().post(
                            MessageFromUI0203Crc(
                                TcnDrivesConstant.BOARD_STAND_BOARD_JS,
                                0,
                                TcnDriveCmdType.CMD_SET_PARAMETERS,
                                -1,
                                -1,
                                -1,
                                -1,
                                false,
                                null,
                                null,
                                null,
                                null,
                                null,
                                jsonObjectParam
                            )
                        )
                    }
                } else if (ButtonEditSelectD.BUTTON_ID_SELECT == buttonId) {
//					showSelectDialog(-1,getString(R.string.background_drive_tips_select_cabinetno),menu_ys_query_param.getButtonEdit(), "",UIComBack.getInstance().getGroupListElevatorShow());
                } else if (ButtonEditSelectD.BUTTON_ID_SELECT_SECOND == buttonId) {
                    showSelectDialog(
                        -1,
                        getString(R.string.background_please_choose),
                        menu_ys_set_param_select!!.buttonEditSecond!!,
                        "",
                        TcnParamStandJs.STAND_ITEM_PARAM_SET
                    )
                } else {
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: MessageFromDrive) {
        TcnVendIF.getInstance().LoggerDebug(
            TAG,
            ("onEventMainThread() getMsgType: " + event.msgType + " getIntData1: " + event.intData1 + " getIntData2: " + event.intData2
                    + " getStringData1: " + event.stringData1 + " getStringData2: " + event.stringData2 + " getJsonObject: " + event.jsonObject)
        )
        when (event.msgType) {
            ConstantsDrive5inch.CMD_SEND_TO_UI_STRING -> if (event.intData1 == 1) {
                // 具体的指令
                if (tv_read_cmd != null) {
                    tv_read_cmd!!.text = event.stringData1
                }
            } else if (event.intData1 == 2) {
                // 解析后的json
                if (tv_read_string != null) {
                    if (event.stringData1 != null) {
                        tv_read_string!!.text = event.stringData1
                    }
                }
            }

            ConstantsDrive5inch.CMD_QUERY_PARAMETERS -> {
                var sq = ""
                when (event.stringData1) {
                    "CHTP" -> sq =
                        event.intData1.toString() + ";" + event.intData2 + ";" + event.intData3 + ";" + event.intData4

                    "CHLEDP" -> sq = event.intData1.toString() + ";" + event.intData2
                    "CHGLP" -> sq = event.intData1.toString() + ";" + event.intData2
                    "CHMDBP" -> sq = event.jsonObject.asString
                    "CHSLP" -> sq =
                        event.intData1.toString() + ";" + event.intData2 + ";" + event.intData3 + ";" + event.intData4
                }
                if (menu_ys_query_param != null) {
                    menu_ys_query_param!!.setButtonDisplayText(sq)
                }
            }

            ConstantsDrive5inch.CMD_SET_PARAMETERS -> {}
            ConstantsDrive5inch.CMD_SET_PERIPHERAL -> {}
            ConstantsDrive5inch.CMD_SET_ISSUED -> {}
        }
    }

    private val m_ButtonClickListener: ButtonClickListener = ButtonClickListener()

    private inner class ButtonClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            val mId = view.id
            TcnVendIF.getInstance().LoggerDebug(TAG, "onClick()")
            if (R.id.main_serport == mId) {
                TcnVendIF.getInstance().LoggerDebug(TAG, "onClick() to SerialPortSetting")
                val `in` = Intent(
                    this@MenuSettingsStandJsActivity,
                    SerialPortSetting::class.java
                )
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

    private var m_vendListener: VendListener? = VendListener()

    private inner class VendListener : VendEventListener {
        @SuppressLint("LongLogTag")
        override fun VendEvent(cEventInfo: VendEventInfo?) {
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
                    this@MenuSettingsStandJsActivity,
                    "选货成功"
                )

                TcnVendEventID.COMMAND_SELECT_FAIL -> TcnUtilityUI.getToast(
                    this@MenuSettingsStandJsActivity,
                    "选货失败"
                )

                TcnVendEventID.COMMAND_SHIPPING -> {
                    if ((cEventInfo.m_lParam4 != null) && (!(cEventInfo.m_lParam4).isEmpty())) {
                        if (m_OutDialog == null) {
                            m_OutDialog = OutDialog(
                                this@MenuSettingsStandJsActivity,
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
                                this@MenuSettingsStandJsActivity,
                                cEventInfo.m_lParam1.toString(),
                                getString(R.string.ui_base_notify_shipping)
                            )
                        } else {
                            m_OutDialog!!.setText(this@MenuSettingsStandJsActivity.getString(R.string.ui_base_notify_shipping))
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
                    if (null != m_OutDialog) {
                        m_OutDialog!!.cancel()
                    }
                    if (m_LoadingDialog == null) {
                        m_LoadingDialog = LoadingDialog(
                            this@MenuSettingsStandJsActivity,
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
                    if (null != m_OutDialog) {
                        m_OutDialog!!.cancel()
                    }
                    if (null == m_LoadingDialog) {
                        m_LoadingDialog = LoadingDialog(
                            this@MenuSettingsStandJsActivity,
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
                    menu_ys_query_drive_fault!!.setButtonDisplayText(R.string.background_notify_receive_goods)
                    takeAwayGoodsMenu(cEventInfo, cEventInfo.m_lParam2, cEventInfo.m_lParam4)
                } else if (cEventInfo.m_lParam1 == TcnVendEventResultID.STATUS_FREE) {      //机器空闲状态（没有在运行）
                    menu_ys_query_drive_fault!!.setButtonDisplayText(cEventInfo.m_lParam4)
                    if (m_LoadingDialog != null) {
                        m_LoadingDialog!!.dismiss()
                    }
                } else if (cEventInfo.m_lParam1 == TcnVendEventResultID.STATUS_HEATING_START) {     //开始加热
                    if (null == m_OutDialog) {
                        m_OutDialog =
                            OutDialog(this@MenuSettingsStandJsActivity, "", cEventInfo.m_lParam4)
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
                        this@MenuSettingsStandJsActivity,
                        getString(R.string.background_drive_check_seriport)
                    )
                } else {
                }

                TcnVendEventID.CMD_TAKE_GOODS_FIRST -> if (cEventInfo.m_lParam1 == TcnVendEventResultID.STATUS_WAIT_TAKE_GOODS) {
                    takeAwayGoodsMenuTips(cEventInfo, cEventInfo.m_lParam2, cEventInfo.m_lParam4)
                } else if (cEventInfo.m_lParam1 == TcnVendEventResultID.STATUS_FREE) {      //机器空闲状态（没有在运行）
                    menu_ys_query_drive_fault!!.setButtonDisplayText(cEventInfo.m_lParam4)
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
                        this@MenuSettingsStandJsActivity,
                        getString(R.string.background_drive_check_seriport)
                    )
                } else {
                }

                TcnVendEventID.CMD_CLEAN_FAULTS -> if (TcnVendEventResultID.STATUS_FREE == cEventInfo.m_lParam1) {
                    menu_ys_clear_drive_fault!!.setButtonDisplayText(cEventInfo.m_lParam4)
                } else if (TcnVendEventResultID.STATUS_BUSY == cEventInfo.m_lParam1) {
                    menu_ys_clear_drive_fault!!.setButtonDisplayText(R.string.background_notify_sys_busy)
                } else if (TcnVendEventResultID.STATUS_WAIT_TAKE_GOODS == cEventInfo.m_lParam1) {
                    menu_ys_clear_drive_fault!!.setButtonDisplayText(R.string.background_notify_receive_goods)
                } else if (TcnVendEventResultID.CMD_NO_DATA_RECIVE == cEventInfo.m_lParam1) {
                    if (m_OutDialog != null) {
                        m_OutDialog!!.dismiss()
                    }
                    TcnUtilityUI.getToast(
                        this@MenuSettingsStandJsActivity,
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
                        this@MenuSettingsStandJsActivity,
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
                            this@MenuSettingsStandJsActivity,
                            getString(R.string.background_drive_check_seriport)
                        )
                    } else {
                    }
                    if (cEventInfo.m_lParam1 == 25) { // 温度设置
                    }
                }

                TcnVendEventID.CMD_REQ_PERMISSION -> {
                    TcnUtilityUI.getToast(this@MenuSettingsStandJsActivity, "请选择确定")
                    ActivityCompat.requestPermissions(
                        this@MenuSettingsStandJsActivity,
                        TcnVendIF.getInstance().getPermission(cEventInfo.m_lParam4),
                        126
                    )
                }

                TcnVendEventID.MDB_RECIVE_COIN_MONEY -> {
                    val strBalance = TcnVendIF.getInstance().balance //余额  Balance
                    Log.d("VendListener", "strBalance: $strBalance")
                    TcnUtilityUI.getToast(
                        this@MenuSettingsStandJsActivity,
                        cEventInfo.m_lParam4 + "元",
                        20
                    ).show() //yuan
                }

                TcnVendEventID.CMD_QUERY_SWIPE_STATUS -> Log.d(
                    "VendListener",
                    "MDB_RECIVE_COIN_MONEY: " + cEventInfo.m_lParam1
                )

                TcnVendEventID.CMD_GET_DISTANCE -> {}
                TcnVendEventID.CMD_GET_DISTANCE_2 -> {}
                TcnVendEventID.CMD_NO_DATA_RECIVE -> {}
                TcnVendEventID.CMD_READ_DOOR_STATUS -> if (TcnVendEventResultID.DO_CLOSE == cEventInfo.m_lParam1) {
                    TcnUtilityUI.getToast(this@MenuSettingsStandJsActivity, "关门")
                } else if (TcnVendEventResultID.DO_OPEN == cEventInfo.m_lParam1) {
                    TcnUtilityUI.getToast(this@MenuSettingsStandJsActivity, "开门")
                } else {
                }

                TcnVendEventID.TEMPERATURE_INFO -> if (!TextUtils.isEmpty(cEventInfo.m_lParam4)) {
                    if (m_Titlebar != null) {
                        m_Titlebar!!.setButtonName(cEventInfo.m_lParam4)
                    }
                }

                TcnVendEventID.CMD_QUERY_PICKUP_STATUS -> Log.i(TAG, "CMD_QUERY_PICKUP_STATUS")
                TcnVendEventID.CMD_START_CARD_PAY ->                    //开始刷卡支付
                    Log.i(TAG, "CMD_START_CARD_PAY---Start swiping your card ")

                TcnVendEventID.CMD_CARD_CONSUMED_FAIL ->                    //刷卡异常
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

    companion object {
        private const val TAG = "MenuSettingsStandJsActivity"
    }
}
