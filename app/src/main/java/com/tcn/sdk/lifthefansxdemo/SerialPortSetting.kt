package com.tcn.sdk.lifthefansxdemo

import android.os.Bundle
import android.preference.ListPreference
import android.preference.Preference.OnPreferenceChangeListener
import android.preference.PreferenceActivity
import android.view.View
import android_serialport_api.TCNSerialPortFinder
import com.tcn.lifthefansxboard.control.TcnShareUseData
import com.tcn.lifthefansxboard.control.TcnVendIF

class SerialPortSetting : PreferenceActivity() {
    private var mSerialPortFinder: TCNSerialPortFinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TcnVendIF.getInstance().LoggerDebug(TAG, "onCreate()")
        mSerialPortFinder = TcnVendIF.getInstance().serialPortFinder

        if (null == mSerialPortFinder) {
            TcnVendIF.getInstance().LoggerDebug(TAG, "onCreate() mSerialPortFinder null")
            return
        }


        addPreferencesFromResource(R.xml.serial_port_preferences)
        setContentView(R.layout.preference_set)

        TcnVendIF.getInstance().LoggerDebug(TAG, "onCreate() 2")

        // Devices
        val devices = findPreference("MAINDEVICE") as ListPreference
        val entries = mSerialPortFinder!!.allDevices
        val entryValues = mSerialPortFinder!!.allDevicesPath
        devices.entries = entries
        devices.entryValues = entryValues
        devices.summary = devices.value
        devices.onPreferenceChangeListener =
            OnPreferenceChangeListener { preference, newValue ->
                preference.summary = newValue as String
                true
            }
        devices.value = TcnShareUseData.getInstance().boardSerPortFirst

        // Baud rates
        val baudrates = findPreference("MAINBAUDRATE") as ListPreference
        baudrates.summary = baudrates.value
        baudrates.onPreferenceChangeListener =
            OnPreferenceChangeListener { preference, newValue ->
                preference.summary = newValue as String
                true
            }

        baudrates.value = TcnShareUseData.getInstance().boardBaudRate

        // Server Devices
        val serverPreference = findPreference("SERVERDEVICE") as ListPreference
        val entriesServer = mSerialPortFinder!!.allDevices
        val entryValuesServer = mSerialPortFinder!!.allDevicesPath
        serverPreference.entries = entriesServer
        serverPreference.entryValues = entryValuesServer
        serverPreference.summary = TcnShareUseData.getInstance().boardSerPortSecond
        serverPreference.onPreferenceChangeListener =
            OnPreferenceChangeListener { preference, newValue ->
                preference.summary = newValue as String
                TcnShareUseData.getInstance().boardSerPortSecond = newValue
                true
            }
        serverPreference.value = TcnShareUseData.getInstance().boardSerPortSecond
    }

    fun serial_back(v: View?) {
        TcnVendIF.getInstance().reqSlotNoInfoOpenSerialPort()
        this.finish()
    }

    companion object {
        private const val TAG = "SerialPortSetting"
    }
}
