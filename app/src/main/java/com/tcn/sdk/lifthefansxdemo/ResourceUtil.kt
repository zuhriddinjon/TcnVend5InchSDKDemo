package com.tcn.sdk.lifthefansxdemo

import android.content.Context

/**
 * Created by Administrator on 2016/4/18.
 */
object ResourceUtil {
    fun getLayoutId(paramContext: Context, paramString: String?): Int {
        return paramContext.resources.getIdentifier(
            paramString, "layout",
            paramContext.packageName
        )
    }

    fun getStringId(paramContext: Context, paramString: String?): Int {
        return paramContext.resources.getIdentifier(
            paramString, "string",
            paramContext.packageName
        )
    }

    fun getDrawableId(paramContext: Context, paramString: String?): Int {
        return paramContext.resources.getIdentifier(
            paramString,
            "drawable", paramContext.packageName
        )
    }

    fun getStyleId(paramContext: Context, paramString: String?): Int {
        return paramContext.resources.getIdentifier(
            paramString,
            "style", paramContext.packageName
        )
    }

    fun getId(paramContext: Context, paramString: String?): Int {
        return paramContext.resources.getIdentifier(paramString, "id", paramContext.packageName)
    }

    fun getColorId(paramContext: Context, paramString: String?): Int {
        return paramContext.resources.getIdentifier(
            paramString,
            "color", paramContext.packageName
        )
    }

    fun getArrayId(paramContext: Context, paramString: String?): Int {
        return paramContext.resources.getIdentifier(
            paramString,
            "array", paramContext.packageName
        )
    }
}
