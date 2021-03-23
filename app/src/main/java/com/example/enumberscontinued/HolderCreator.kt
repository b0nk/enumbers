package com.example.enumberscontinued

import android.view.View

/**
 * Interface of holder factory
 */
interface HolderCreator<T> {
    fun createHolder(view: View?): UiHolder<T>?
}