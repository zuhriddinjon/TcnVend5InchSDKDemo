package com.tcn.sdk.lifthefansxdemo

import android.support.annotation.AnimRes
import android.support.annotation.LayoutRes
import android.support.annotation.StyleRes

/**
 * Created by songjiancheng on 2016/4/8.
 */
/*
 * @AnimatorRes
 * @AnimRes
 * @AnyRes
 * @ArrayRes
 * @AttrRes
 * @BoolRes
 * @ColorRes
 * @DimenRes
 * @DrawableRes
 * @FractionRes
 * @IdRes
 * @IntDef
 * @IntegerRes
 * @InterpolatorRes
 * @LayoutRes
 * @MenuRes
 * @NonNull
 * @Nullable
 * @PluralsRes
 * @RawRes
 * @StringDef
 * @StringRes
 * @StyleableRes
 * @StyleRes
 * @XmlRes
 */
object Resources {
    fun getAnimResourceID(@AnimRes resID: Int): Int {
        return resID
    }

    fun getLayoutResourceID(@LayoutRes resID: Int): Int {
        return resID
    }

    fun getStyleResourceID(@StyleRes resID: Int): Int {
        return resID
    }
}
