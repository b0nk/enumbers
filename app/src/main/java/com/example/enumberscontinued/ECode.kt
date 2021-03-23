/*
 * This file is distributed under GPL v3 license
 * http://www.gnu.org/licenses/gpl-3.0.html
 *
 * raven@acute-angle.net
 */
package com.example.enumberscontinued

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils

class ECode : Comparable<ECode?>, Parcelable {
    var eCode: String? = null
    var name: String? = null
    var purpose: String? = null
    private var danger = 0
    var extra: String? = null
    var vegan = 0
    var children = 0
    var allergic = false

    constructor() {}
    private constructor(`in`: Parcel) {
        eCode = `in`.readString()
        name = `in`.readString()
        purpose = `in`.readString()
        danger = `in`.readInt()
        extra = `in`.readString()
        vegan = `in`.readInt()
        children = `in`.readInt()
        allergic = `in`.readInt() != 0
    }

    fun safeForVegans(): Boolean {
        return vegan == 0
    }

    fun safeForChildren(): Boolean {
        return children == 0
    }

    fun safeForAllergic(): Boolean {
        return !allergic
    }

    fun hasExtra(): Boolean {
        return !TextUtils.isEmpty(extra)
    }

    fun init(row: Array<String>) {
        eCode = row[0]
        name = row[1]
        purpose = if (row.size > 2) row[2] else ""
        danger = if (row.size > 3) row[3].toInt() else UNKNOWN
        if (row.size > 4) extra = row[4]
    }

    override operator fun compareTo(another: ECode?): Int {
        var st1 = eCode
        var st2 = another?.eCode
        if (st1!![st1.length - 1] > '9') st1 = st1.substring(0, st1.length - 1)
        if (st2!![st2.length - 1] > '9') st2 = st2.substring(0, st2.length - 1)
        return if (st1 == st2) eCode!!.compareTo(another?.eCode!!) else Integer.valueOf(st1).compareTo(Integer.valueOf(st2))
    }

    val color: Int
        get() = colors[danger]

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(eCode)
        dest.writeString(name)
        dest.writeString(purpose)
        dest.writeInt(danger)
        dest.writeString(extra)
        dest.writeInt(vegan)
        dest.writeInt(children)
        dest.writeInt(if (allergic) 1 else 0)
    }

    fun getDanger(): Int {
        return danger
    }

    fun setDanger(danger: Int) {
        var danger = danger
        if (danger < 0 || danger > 3) danger = 4
        this.danger = danger
    }

    companion object {
        const val UNKNOWN = 4
        const val PERMITTED = 0
        const val UNPERMITTED = 1 // SLIGHT DANGER
        const val MOSTLY_SAFE = 2 // Usually safe !!! Value changed!
        const val DANGEROUS = 3 // DANGEROUS, AVOID AT ALL COST
        val colors = intArrayOf(-0x8a2e8b, -0xc3, -0x402e8b, -0xffe6, -0x333334)
        @JvmField val CREATOR: Parcelable.Creator<ECode?> = object : Parcelable.Creator<ECode?> {
            override fun createFromParcel(`in`: Parcel): ECode? {
                return ECode(`in`)
            }

            override fun newArray(size: Int): Array<ECode?> {
                return arrayOfNulls(size)
            }
        }
    }
}