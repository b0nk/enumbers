package com.example.enumberscontinued

import android.view.View

/**
 * Basic interface for Holder. Should be used by Holder pattern implementation
 */
abstract class UiHolder<T>(protected val view: View) {
    /**
     * Fills view using supplied data object
     *
     * @param object data object
     */
    abstract fun fillView(`object`: T): View?
}