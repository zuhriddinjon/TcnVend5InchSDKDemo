package controller

import android.content.res.Configuration
import com.tcn.lifthefansxboard.TcnService
import com.tcn.lifthefansxboard.control.TcnVendIF
import java.io.PrintWriter
import java.io.StringWriter


class VendService : TcnService() {
    private var m_UncaughHandler: Thread.UncaughtExceptionHandler? = null


    override fun onCreate() {
        super.onCreate()
        m_UncaughHandler = Thread.UncaughtExceptionHandler { thread, ex -> //任意一个线程异常后统一的处理
            val stringWriter = StringWriter()
            val writer = PrintWriter(stringWriter)
            ex.printStackTrace(writer) // 打印到输出流
            val exception = stringWriter.toString()
            stopSelf()
            TcnVendIF.getInstance().LoggerErrorForce(
                TAG,
                "setDefaultUncaughtExceptionHandler exception: $exception"
            )
        }
        /*捕捉异常，并将具体异常信息写入日志中 */
        Thread.setDefaultUncaughtExceptionHandler(m_UncaughHandler)

        TcnVendIF.getInstance().LoggerInfoForce(TAG, "onCreate()")
        VendIF.instance.initialize()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        TcnVendIF.getInstance()
            .LoggerInfoForce(TAG, "onConfigurationChanged newConfig: " + newConfig.orientation)
    }

    override fun onDestroy() {
        super.onDestroy()
        VendIF.instance.deInitialize()
        m_UncaughHandler = null
        Thread.setDefaultUncaughtExceptionHandler(null)
        TcnVendIF.getInstance().LoggerInfoForce(TAG, "onDestroy()")
    }

    companion object {
        private const val TAG = "VendService"
    }
}
