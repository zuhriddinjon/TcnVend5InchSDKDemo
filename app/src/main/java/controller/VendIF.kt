package controller

import android.os.Message
import com.tcn.lifthefansxboard.control.MsgTrade
import com.tcn.lifthefansxboard.control.TcnComDef
import com.tcn.lifthefansxboard.control.TcnComResultDef
import com.tcn.lifthefansxboard.control.TcnVendIF
import com.tcn.lifthefansxboard.control.TcnVendIF.CommunicationListener

/**
 * Created by Administrator on 2016/6/30.
 */
class VendIF {
    fun initialize() {
        registerListener()
    }


    fun deInitialize() {
        unregisterListener()
    }

    fun registerListener() {
        TcnVendIF.getInstance().setOnCommunicationListener(m_CommunicationListener)
    }

    fun unregisterListener() {
        TcnVendIF.getInstance().setOnCommunicationListener(null)
    }

    private fun OnSelectedSlotNo(slotNo: Int) {
    }

    //驱动板上报过来的数据 slotNo:货道号     status:0 货道状态正常        255：货道号不存在（检测不到该货道）
    fun OnUploadSlotNoInfo(finish: Boolean, slotNo: Int, status: Int) {
        TcnVendIF.getInstance().LoggerDebug(
            TAG,
            "OnUploadSlotNoInfo,slotNo=$slotNo;status=$status"
        )
    }

    //驱动板上报过来的数据 slotNo:货道号     status:0 货道状态正常     4：没有检测到掉货      255：货道号不存在（检测不到该货道）
    fun OnUploadSlotNoInfoSingle(finish: Boolean, slotNo: Int, status: Int) {
        TcnVendIF.getInstance().LoggerInfoForce(
            TAG,
            "OnUploadSlotNoInfoSingle finish: $finish slotNo: $slotNo status: $status"
        )
    }

    //出货状态返回    slotNo： 货道号    shipStatus： 出货状态    status: 货道状态正常    支付订单号（出货接口传入，原样返回） amount：支付金额（出货接口传入，原样返回）
    private fun OnShipWithMethod(
        slotNo: Int,
        shipStatus: Int,
        errCode: Int,
        tradeNo: String,
        amount: String
    ) {
        TcnVendIF.getInstance().LoggerInfoForce(
            TAG,
            ("OnShipWithMethod slotNo: " + slotNo + " shipStatus: " + shipStatus + " errCode: " + errCode
                    + " tradeNo: " + tradeNo + " amount: " + amount)
        )

        if (TcnComResultDef.SHIP_SHIPING == shipStatus) {   //出货中
        } else if (TcnComResultDef.SHIP_SUCCESS == shipStatus) {   //出货成功
        } else if (TcnComResultDef.SHIP_FAIL == shipStatus) {    //出货失败
        } else {
        }
    }

    private fun OnDoorSwitch(door: Int) {
    }

    private fun OnSelectedGoods(slotNoOrKey: Int, price: String) {
    }

    private fun OnShipForTestSlot(slotNo: Int, errCode: Int, shipStatus: Int) {
        TcnVendIF.getInstance().LoggerInfoForce(
            TAG,
            "OnShipForTestSlot slotNo: $slotNo errCode: $errCode shipStatus: $shipStatus"
        )
    }

    //    private void OnUploadGoodsInfo(int slotNo, int finish, Coil_info slotInfo) {
    //
    //    }
    /*
     * 此处监听底层发过来的数据，此处接收数据位于线程内
     */
    private val m_CommunicationListener: VendCommunicationListener = VendCommunicationListener()

    private inner class VendCommunicationListener : CommunicationListener {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                TcnComDef.COMMAND_SELECT_SLOTNO -> OnSelectedSlotNo(msg.arg1)
                TcnComDef.COMMAND_SLOTNO_INFO -> OnUploadSlotNoInfo(
                    msg.obj as Boolean,
                    msg.arg1,
                    msg.arg2
                )

                TcnComDef.COMMAND_SLOTNO_INFO_SINGLE -> OnUploadSlotNoInfoSingle(
                    msg.obj as Boolean,
                    msg.arg1,
                    msg.arg2
                ) //底层上报上来的货道状态
                TcnComDef.COMMAND_SHIPMENT_CASHPAY -> {
                    val mMsgToSendcash = msg.obj as MsgTrade
                    OnShipWithMethod(
                        msg.arg1,
                        msg.arg2,
                        mMsgToSendcash.errCode,
                        mMsgToSendcash.tradeNo,
                        mMsgToSendcash.amount
                    )
                    if (TcnComResultDef.SHIP_SUCCESS == msg.arg2) {
//                        TcnVendIF.getInstance().payOutCashBalance(msg.arg1, mMsgToSendcash.getTradeNo());   //
                        TcnVendIF.getInstance().payOutCashBalance() //退掉剩余的币
                    }
                }

                TcnComDef.COMMAND_SHIPMENT_WECHATPAY -> {
                    val mMsgToSendWx = msg.obj as MsgTrade
                    OnShipWithMethod(
                        msg.arg1,
                        msg.arg2,
                        mMsgToSendWx.errCode,
                        mMsgToSendWx.tradeNo,
                        mMsgToSendWx.amount
                    )
                }

                TcnComDef.COMMAND_SHIPMENT_ALIPAY -> {
                    val mMsgToSendAli = msg.obj as MsgTrade
                    OnShipWithMethod(
                        msg.arg1,
                        msg.arg2,
                        mMsgToSendAli.errCode,
                        mMsgToSendAli.tradeNo,
                        mMsgToSendAli.amount
                    )
                }

                TcnComDef.COMMAND_SHIPMENT_GIFTS -> {
                    val mMsgToSendGifts = msg.obj as MsgTrade
                    OnShipWithMethod(
                        msg.arg1,
                        msg.arg2,
                        mMsgToSendGifts.errCode,
                        mMsgToSendGifts.tradeNo,
                        mMsgToSendGifts.amount
                    )
                }

                TcnComDef.COMMAND_SHIPMENT_REMOTE -> {
                    val mMsgToSendRemote = msg.obj as MsgTrade
                    OnShipWithMethod(
                        msg.arg1,
                        msg.arg2,
                        mMsgToSendRemote.errCode,
                        mMsgToSendRemote.tradeNo,
                        mMsgToSendRemote.amount
                    )
                }

                TcnComDef.COMMAND_SHIPMENT_VERIFY -> {
                    val mMsgToSendVerfy = msg.obj as MsgTrade
                    OnShipWithMethod(
                        msg.arg1,
                        msg.arg2,
                        mMsgToSendVerfy.errCode,
                        mMsgToSendVerfy.tradeNo,
                        mMsgToSendVerfy.amount
                    )
                }

                TcnComDef.COMMAND_SHIPMENT_BANKCARD_ONE -> {
                    val mMsgToSendBankcard = msg.obj as MsgTrade
                    OnShipWithMethod(
                        msg.arg1,
                        msg.arg2,
                        mMsgToSendBankcard.errCode,
                        mMsgToSendBankcard.tradeNo,
                        mMsgToSendBankcard.amount
                    )
                }

                TcnComDef.COMMAND_SHIPMENT_BANKCARD_TWO -> {
                    val mMsgToSendBankcardTwo = msg.obj as MsgTrade
                    OnShipWithMethod(
                        msg.arg1,
                        msg.arg2,
                        mMsgToSendBankcardTwo.errCode,
                        mMsgToSendBankcardTwo.tradeNo,
                        mMsgToSendBankcardTwo.amount
                    )
                }

                TcnComDef.COMMAND_SHIPMENT_TCNCARD_OFFLINE -> {
                    val mMsgToSendBankcardOffLine = msg.obj as MsgTrade
                    OnShipWithMethod(
                        msg.arg1,
                        msg.arg2,
                        mMsgToSendBankcardOffLine.errCode,
                        mMsgToSendBankcardOffLine.tradeNo,
                        mMsgToSendBankcardOffLine.amount
                    )
                }

                TcnComDef.COMMAND_SHIPMENT_TCNCARD_ONLINE -> {
                    val mMsgToSendBankcardOnLine = msg.obj as MsgTrade
                    OnShipWithMethod(
                        msg.arg1,
                        msg.arg2,
                        mMsgToSendBankcardOnLine.errCode,
                        mMsgToSendBankcardOnLine.tradeNo,
                        mMsgToSendBankcardOnLine.amount
                    )
                }

                TcnComDef.COMMAND_SHIPMENT_OTHER_PAY -> {
                    val mMsgToSendBankcardPay = msg.obj as MsgTrade
                    OnShipWithMethod(
                        msg.arg1,
                        msg.arg2,
                        mMsgToSendBankcardPay.errCode,
                        mMsgToSendBankcardPay.tradeNo,
                        mMsgToSendBankcardPay.amount
                    )
                }

                TcnComDef.CMD_TEST_SLOT -> OnShipForTestSlot(msg.arg1, msg.arg2, msg.obj as Int)
                else -> {}
            }
        }
    }

    companion object {
        private const val TAG = "VendIF"
        private var m_Instance: VendIF? = null

        @get:Synchronized
        val instance: VendIF
            /**************************  故障代码表 ****************************
             * 1   升降电机断线
             * 2   升降电机过流
             * 3   升降超时
             * 4   平移电机断线
             * 5   平移电机过流
             * 6   平移电机超时
             * 7   平台电机断线
             * 8   平台电机过流
             * 9   平台电机超时
             * 10  移动平台有商品
             * 11  运行过程中跌落
             * 12  微波加热顶不到位
             * 13  货掉不出
             * 14  升降电机堵转
             * 15  平移电机堵转
             * 16  升降电机超出最大步数
             * 17  平移电机超出最大步数
             * 18  加热平台升降电机断线
             * 19  加热平台升降电机过流
             * 20  加热平台升降电机超时
             * 21 磁控管1完全没有工作
             * 22 磁控管2完全没有工作
             * 23 磁控管1和磁控管2完全没有工作
             * 24 磁控管3完全没有工作
             * 25 磁控管1和磁控管3完全没有工作
             * 26 磁控管2和磁控管3完全没有工作
             * 27 磁控管1磁控管2和磁控管3完全没有工作
             *
             * 28 磁控管1工作一定时间后停止工作（可能过热保护/电池保护）
             * 29 磁控管2工作一定时间后停止工作（可能过热保护/电池保护）
             * 30 磁控管1和磁控管2工作一定时间后停止工作（可能过热保护/电池保护）
             * 31 磁控管3工作一定时间后停止工作（可能过热保护/电池保护）
             * 32 磁控管1和磁控管3工作一定时间后停止工作（可能过热保护/电池保护）
             * 33 磁控管2和磁控管3工作一定时间后停止工作（可能过热保护/电池保护）
             * 34 磁控管1磁控管2和磁控管3工作一定时间后停止工作（可能过热保护/电池保护）
             *
             * 35 磁控管1控制继电器粘连
             * 36 磁控管2控制继电器粘连
             * 37 磁控管3控制继电器粘连
             *
             * 40 货道故障/没有找到对应货道层
             *
             * 51  取货门电机断线
             * 52  取货门电机过流
             * 53  取货门电机超时
             * 54  防夹手光检被挡住
             * 55	取货口推货电机断线
             * 56	取货口推货电机过流
             * 57	取货口推货电机超时
             * 58  取货口推货电机堵转
             * 59	出货皮带电机断线
             * 60  出货皮带电机过流
             *
             * 64  无效货道
             *
             * 71  转盘电机断线
             * 72  转盘电机过流
             * 73  转盘电机超时
             * 74  转盘电机堵转
             *
             *
             * 80  转动超时
             *
             * 101 取货口有商品
             * 102 加热平台没有检测到商品（可能卡在侧门）
             *
             * 240 存储芯片故障
             * 255 通信故障
             *
             */
            get() {
                if (null == m_Instance) {
                    m_Instance = VendIF()
                }
                return m_Instance!!
            }
    }
}
