package com.tcn.sdk.lifthefansxdemo

import com.tcn.lifthefansxboard.control.TcnConstantLift
import com.tcn.lifthefansxboard.control.TcnVendIF

class UIComBack {
    private val m_GrpShowListAll: MutableList<GropInfoBack> = ArrayList()
    private val m_GrpShowListHefanZp: MutableList<GropInfoBack> = ArrayList()


    fun getGroupHefanZpId(data: String?): Int {
        var iId = -1
        if ((null == data) || (data.length < 1)) {
            val GrpShowListHefanZp = TcnVendIF.getInstance().groupListHefanZp
            if ((GrpShowListHefanZp != null) && (GrpShowListHefanZp.size > 0)) {
                iId = GrpShowListHefanZp[0].id
            }
            return iId
        }

        for (info in m_GrpShowListHefanZp) {
            if (data == info.showText) {
                iId = info.grpID
            }
        }
        return iId
    }


    val isMutiGrpHefanZp: Boolean
        get() {
            var bRet = false
            val mGroupInfoList =
                TcnVendIF.getInstance().groupListHefanZp
            if ((mGroupInfoList != null) && (mGroupInfoList.size > 1)) {
                bRet = true
            }
            return bRet
        }

    val groupListAll: List<GropInfoBack>
        get() {
            m_GrpShowListAll.clear()
            val mGroupInfoList =
                TcnVendIF.getInstance().groupListAll
            if ((mGroupInfoList != null) && (mGroupInfoList.size > 1)) {
                for (i in mGroupInfoList.indices) {
                    val info = mGroupInfoList[i]
                    val mGropInfoBack = GropInfoBack()
                    mGropInfoBack.iD = i
                    mGropInfoBack.grpID = info.id
                    if (info.id == 0) {
                        mGropInfoBack.showText = "主柜"
                    } else {
                        mGropInfoBack.showText = "副柜" + info.id
                    }
                    m_GrpShowListAll.add(mGropInfoBack)
                }
            }
            return m_GrpShowListAll
        }

    val groupListHefanZpShow: Array<String?>?
        get() {
            val m_RetList: MutableList<String?> =
                ArrayList()
            if (m_GrpShowListHefanZp.size < 1) {
                val mGroupInfoList =
                    TcnVendIF.getInstance().groupListHefanZp
                if ((mGroupInfoList != null) && (mGroupInfoList.size > 1)) {
                    for (i in mGroupInfoList.indices) {
                        val info = mGroupInfoList[i]
                        val mGropInfoBack = GropInfoBack()
                        mGropInfoBack.iD = i
                        mGropInfoBack.grpID = info.id
                        if (info.id == 0) {
                            mGropInfoBack.showText = "主柜"
                        } else {
                            mGropInfoBack.showText = "副柜" + info.id
                        }
                        m_GrpShowListHefanZp.add(mGropInfoBack)
                    }
                }
            }

            for (info in m_GrpShowListHefanZp) {
                m_RetList.add(info.showText)
            }
            if (m_RetList.size < 1) {
                return null
            }
            val strArry =
                arrayOfNulls<String>(m_RetList.size)
            for (i in m_RetList.indices) {
                strArry[i] = m_RetList[i]
            }
            return strArry
        }

    fun getQueryParameters(data: String?): Int {
        var iAddress = -1
        if ((null == data) || (data.length < 1)) {
            return iAddress
        }

        for (i in (TcnConstantLift.LIFT_QUERY_PARAM_STAND).indices) {
            if ((TcnConstantLift.LIFT_QUERY_PARAM_STAND[i]) == data) {
                iAddress = i
                break
            }
        }

        return iAddress
    }

    val isGroupListAllMuti: Boolean
        get() {
            var bRet = false
            val mGroupInfoList =
                TcnVendIF.getInstance().groupListAll
            if ((mGroupInfoList != null) && (mGroupInfoList.size > 1)) {
                bRet = true
            }

            return bRet
        }

    companion object {
        private const val TAG = "UIComBack"
        var HEAT_COOL_OFF_SWITCH_SELECT: Array<String> = arrayOf("制冷", "加热", "关闭")
        private var m_Instance: UIComBack? = null

        @get:Synchronized
        val instance: UIComBack
            get() {
                if (null == m_Instance) {
                    m_Instance = UIComBack()
                }
                return m_Instance!!
            }
    }
}
