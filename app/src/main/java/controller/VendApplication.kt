package controller

import com.tcn.lifthefansxboard.control.TcnShareUseData
import com.tcn.lifthefansxboard.control.TcnVendApplication


/**
 * 描述：
 * 作者：Jiancheng,Song on 2016/5/31 15:53
 * 邮箱：m68013@qq.com
 */
class VendApplication : TcnVendApplication() {
    override fun onCreate() {
        super.onCreate()
        //        TcnShareUseData.getInstance().setBoardSerPortBodyCheck("/dev/ttysWK1");   //设置人体感应距离串口
//
//          TcnShareUseData.getInstance().setBoardSerPortBodyCheck2("/dev/ttysWK3");   //设置人体感应距离串口
        TcnShareUseData.getInstance().boardSerPortFirst = "/dev/ttyS1" //此处主板串口接安卓哪个串口，就填哪个串口


        //        TcnShareUseData.getInstance().setBoardSerPortFirst("/dev/ttymxc1");


        //先运行程序之后,进行扫描授权，授权才能使用，每台机器都必须先授权。
        /*******************************      故障代码表见 VendIF  这个文件  */


        /*
        一、配置串口
        比如：
        TcnShareUseData.getInstance().setBoardSerPortFirst("/dev/ttyS1");


        二、重启

        三、重启  等 TcnComDef.COMMAND_SLOTNO_INFO 接收到所有货道的状态

        四、选货
        发选货命令 TcnVendIF.getInstance().reqSelectSlotNo();
        收到TcnVendEventID.COMMAND_SELECT_GOODS 通知之后，表示选货成功，该货道正常，可以购买

        五、出货：
        int slotNo = Integer.valueOf(strParam);//出货的货道号
        String shipMethod = PayMethod.PAYMETHED_WECHAT; //出货方法,微信支付出货，此处自己可以修改。
        String amount = "0.1";    //支付的金额（元）,自己修改
        String tradeNo = "1811020095201811150126888";//支付订单号，每次出货，订单号不能一样，此处自己修改。
        int heatSeconds = 0;   //此处加热时间自己设置,单位秒，最大不超过300秒
        TcnVendIF.getInstance().reqShip(slotNo,heatSeconds,shipMethod,amount,tradeNo);


        出货结果返回
        case TcnVendEventID.COMMAND_SHIPPING: //出货中  commodity is dispensed successfully
        case TcnVendEventID.COMMAND_SHIPMENT_SUCCESS: //出货成功 commodity is dispensed successfully
        case TcnVendEventID.COMMAND_SHIPMENT_FAILURE: //出货失败  commodity delivery failed

        case TcnVendEventID.CMD_QUERY_STATUS_LIFTER:
             cEventInfo.m_lParam1 == TcnVendEventResultID.STATUS_WAIT_TAKE_GOODS  检测到取货口有货,提示客户取走商品
             cEventInfo.m_lParam1 == TcnVendEventResultID.STATUS_FREE  机器空闲状态（没有在运行）
             cEventInfo.m_lParam1 == TcnVendEventResultID.STATUS_HEATING_START  开始加热
             cEventInfo.m_lParam1 == TcnVendEventResultID.STATUS_HEATING_END  加热完成


        MainAct中：
        注册底层监听：Register the underlying listener
        TcnVendIF.getInstance().registerListener(m_vendListener);

        取消监听： Cancel listening
        TcnVendIF.getInstance().unregisterListener(m_vendListener);


        常用命令如下： Common commands are as follows:

        出货：TcnVendIF.getInstance().reqShip(slotNo,shipMethod,amount,tradeNo);
        测试出货：TcnVendIF.getInstance().reqShipTest(Integer.parseInt(strParam));
        选货（只有选货成功，才能代表该货道可以购买）：TcnVendIF.getInstance().reqSelectSlotNo(Integer.valueOf(strParam));

        */
        /******************** 5寸屏主板用着MDB主板通讯控制现金和刷卡 */
//        初始化设置：
//        TcnShareUseData.getInstance().setMdbBoardType(TcnDriveType.MACHINE_TYPE_5INCH);
//        TcnShareUseData.getInstance().setCashPayOpen(true);
//        TcnShareUseData.getInstance().setBoardSerPortMDB("/dev/ttyS3");    //MDB串口设置  主板接哪个串口就设置为哪个串口

        //MDB消费的时候：
        //如果出货方式调用的是PayMethod.PAYMETHED_CASH 则上报给MDB的出货结果会内部调用，即内部已经调用了TcnVendIF.getInstance().reqUploadMDBCashPay();
        // 如果出货方式不是 PayMethod.PAYMETHED_CASH,可以自己调用TcnVendIF.getInstance().reqUploadMDBCashPay();
//        TcnVendIF.getInstance().ship(1, PayMethod.PAYMETHED_CASH,"0.2","2589764523");

        //如果出货方式调用的是PayMethod.PAYMETHED_MDB_CARD 则上报给MDB的出货结果会内部调用，即内部已经调用了TcnVendIF.getInstance().reqUploadMDBCardPay();
        // 如果出货方式不是 PayMethod.PAYMETHED_MDB_CARD,可以自己调用TcnVendIF.getInstance().reqUploadMDBCardPay();
//        TcnVendIF.getInstance().ship(1, PayMethod.PAYMETHED_MDB_CARD,"0.2","2589764523");

//        TcnVendIF.getInstance().reqSelectSlotNo(); //选货成功之后，返回通知
//        case TcnVendEventID.COMMAND_SELECT_GOODS:  //选货成功  Select commodity successfully
//            TcnUtilityUI.getToast(MainAct.this, "选货成功"); //Select commodity successfully
//              此时可以调用刷卡支付：
//        TcnVendIF.getInstance().reqCardPay();    //请求刷卡
//        TcnVendIF.getInstance().isCardCanPay();
//        TcnVendIF.getInstance().reqStopCardPay();     //取消刷卡
//        TcnVendIF.getInstance().isUploadingMDBPay();  //正在上报消费结果
//            break;
//     TcnVendIF.getInstance().payOutCashBalance();  //退掉剩余的币
//        TcnVendIF.getInstance().getBalance()         //显示余额
//        case TcnVendEventID.CMD_CARD_CONSUMED_SUCCESS:    //msg.arg1:货道号 刷卡消费成功 card pay success
        //              此时可以调用出货指令：TcnVendIF.getInstance().ship(1, PayMethod.PAYMETHED_MDB_CARD,"0.2","2589764523");
//            break;
//        case TcnVendEventID.CMD_CARD_CONSUMED_FAIL: 刷卡消费失败
//            break;

//        TcnVendIF.getInstance().reqSetParameters(0,24,String.valueOf(0));   //关闭制冷或加热
//        TcnVendIF.getInstance().reqSetParameters(0,24,String.valueOf(1));   //打开制冷
    }
}
