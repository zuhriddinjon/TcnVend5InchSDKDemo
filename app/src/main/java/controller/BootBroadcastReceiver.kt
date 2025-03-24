package controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * 描述：
 * 作者：Jiancheng,Song on 2016/5/31 15:30
 * 邮箱：m68013@qq.com
 */
class BootBroadcastReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent) {
        if (null == context) {
            return
        }
        if (action_boot == intent.action) {
            val intentService = Intent(context, VendService::class.java)
            context.startService(intentService)
        }
    }

    companion object {
        private const val action_boot = "android.intent.action.BOOT_COMPLETED"
    }
}
