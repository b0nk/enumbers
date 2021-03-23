package com.example.enumberscontinued

import android.content.Context
import android.view.View

object ListViewUtils {
    /**
     * Creates or retrieves holder. If necessary will inflate view and and create holder for that view
     *
     * @param context     used by inflator
     * @param creator     factory for creating holder
     * @param layoutResId list item layout id
     * @param reuseView   View to reuse
     * @return
     */
    fun <T> createHolder(
        context: Context?,
        creator: HolderCreator<T>,
        layoutResId: Int,
        reuseView: View?
    ): UiHolder<T?>? {
        val resultView: View
        val holder: UiHolder<T?>?
        if (reuseView == null) {
            resultView = View.inflate(context, layoutResId, null)
            holder = creator.createHolder(resultView)
            resultView.tag = holder
        } else {
            resultView = reuseView
            holder = resultView.tag as UiHolder<T?>
        }
        return holder
    }
}