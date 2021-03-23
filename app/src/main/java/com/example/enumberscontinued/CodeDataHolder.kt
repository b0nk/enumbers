package com.example.enumberscontinued

import android.view.View
import android.widget.ImageView
import android.widget.TextView

class CodeDataHolder(view: View) : UiHolder<ECode?>(view) {
        private val band: TextView
        private val codeId: TextView
        private val codeName: TextView
        private val codePurpose: TextView
        private val imageVegan: ImageView
        private val imageChild: ImageView
        private val imageAllergy: ImageView

        companion object {
            private const val DIMMER = 2
            private fun dimmed(color: Int): Int {
                val r = color shr 16 and 0xFF / DIMMER
                val g = color shr 8 and 0xFF / DIMMER
                val b = color and 0xFF / DIMMER
                return (0x8F shl 24) + (r shl 16) + (g shl 8) + b
            }
        }

        init {
            band = view.findViewById<View>(R.id.color_band) as TextView
            codeId = view.findViewById<View>(R.id.ecode) as TextView
            codeName = view.findViewById<View>(R.id.name) as TextView
            codePurpose = view.findViewById<View>(R.id.purpose) as TextView
            imageVegan = view.findViewById<View>(R.id.vegan) as ImageView
            imageChild = view.findViewById<View>(R.id.child) as ImageView
            imageAllergy = view.findViewById<View>(R.id.allergy) as ImageView
        }

    override fun fillView(eCode: ECode?): View {
        eCode?.let { band.setBackgroundColor(it.color) }
        codeId.setText("E" + eCode?.eCode)
        codeName.setText(eCode?.name)
        codePurpose.setText(eCode?.purpose)
        if (eCode?.hasExtra() == true) {
            band.setText("!")
            band.setTextColor(dimmed(eCode.color))
        } else {
            band.setText("")
        }
        imageVegan.setImageResource(if (eCode?.vegan == 0) R.drawable.veg_green_small else if (eCode!!.vegan == 2) R.drawable.veg_yellow_small else R.drawable.veg_red_small)
        imageChild.setImageResource(if (eCode.children == 0) R.drawable.child_green_small else R.drawable.child_red_small)
        imageAllergy.setImageResource(if (eCode.allergic) R.drawable.allergic_red_small else R.drawable.allergic_green_small)
        return view
    }
}