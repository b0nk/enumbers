/*
 * This file is distributed under GPL v3 license 
 * http://www.gnu.org/licenses/gpl-3.0.html      
 *                                               
 * raven@acute-angle.net                         
 */
package com.example.enumberscontinued

import android.app.Activity
import android.app.SearchManager
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.enumberscontinued.GlobalCodeList.instance

class ECodeViewActivity : Activity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ecodeview)

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        val tvName = findViewById<View>(R.id.textName) as TextView
        val tvPurpose = findViewById<View>(R.id.textPurpose) as TextView
        val tvExtra = findViewById<View>(R.id.textExtra) as TextView
        val imVegan = findViewById<View>(R.id.img_v) as ImageView
        val imChild = findViewById<View>(R.id.img_ch) as ImageView
        val imAller = findViewById<View>(R.id.img_al) as ImageView
        val tvVegan = findViewById<View>(R.id.tv_v) as TextView
        val tvChild = findViewById<View>(R.id.tv_ch) as TextView
        val tvAller = findViewById<View>(R.id.tv_al) as TextView
        val layExtra = findViewById<View>(R.id.layExtra) as LinearLayout
        val extras = intent.extras
        val code: ECode?
        code = if (extras!!.containsKey(SearchManager.EXTRA_DATA_KEY)) {
            instance!!.find(extras.getString(SearchManager.EXTRA_DATA_KEY)!!)
        } else {
            this.intent.getParcelableExtra("ecode")
        }
        if (code == null) {
            Toast.makeText(this, "Fock", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        val eCode = findViewById<View>(R.id.ecode) as TextView
        eCode.text = "E" + code.eCode
        val colorBand = findViewById<View>(R.id.color_band)
        colorBand.setBackgroundColor(code.color)
        tvName.text = code.name
        tvPurpose.text = code.purpose
        tvExtra.setText(code.extra)
        layExtra.visibility = if (code.hasExtra()) View.VISIBLE else View.GONE
        imVegan.setImageResource(if (code.vegan == 0) R.drawable.veg_green else if (code.vegan == 2) R.drawable.veg_yellow else R.drawable.veg_red)
        imChild.setImageResource(if (code.children == 0) R.drawable.child_green else R.drawable.child_red)
        imAller.setImageResource(if (code.allergic) R.drawable.allergic_red else R.drawable.allergic_green)
        tvVegan.setText(if (code.vegan == 0) R.string.vegan2 else if (code.vegan == 2) R.string.v2 else R.string.v1)
        tvChild.setText(if (code.children == 0) R.string.child2 else if (code.children == 1) R.string.c1 else R.string.c2)
        tvAller.setText(if (code.allergic) R.string.a1 else R.string.allerg2)
        val close = findViewById<View>(R.id.close_btn) as Button
        close.setOnClickListener { finish() }
    }
}