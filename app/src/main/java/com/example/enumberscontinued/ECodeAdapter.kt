/*
 * This file is distributed under GPL v3 license 
 * http://www.gnu.org/licenses/gpl-3.0.html      
 *                                               
 * raven@acute-angle.net                         
 */
package com.example.enumberscontinued

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

abstract class ECodeAdapter(context: Context, list: ECodeList) : BaseAdapter(), HolderCreator<ECode?> {
    private val list: ECodeList
    private val context: Context
    override fun getView(position: Int, convertView: View, parent: ViewGroup): View? {
        val holder: UiHolder<ECode?>? = ListViewUtils.createHolder(context, this, R.layout.ecode, convertView)
        val code: ECode? = list[position]
        return holder?.fillView(code)
    }

    override fun getItem(position: Int): ECode? {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun createHolder(view: View): CodeDataHolder {
        return CodeDataHolder(view)
    }

    init {
        this.list = list
        this.context = context
    }
}