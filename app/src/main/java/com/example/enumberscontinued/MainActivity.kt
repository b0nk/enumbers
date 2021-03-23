/*
 * This file is distributed under GPL v3 license
 * http://www.gnu.org/licenses/gpl-3.0.html
 *
 * raven@acute-angle.net
 */
package org.uaraven.e

import android.R
import android.app.ActionBar
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ListView
import androidx.core.content.ContextCompat
import com.example.enumberscontinued.Consts
import com.example.enumberscontinued.ECode
import com.example.enumberscontinued.ECodeAdapter
import com.example.enumberscontinued.ECodeViewActivity
import com.example.enumberscontinued.GlobalCodeList.init
import com.example.enumberscontinued.GlobalCodeList.instance
import java.util.*

open class MainActivity :  TextWatcher {
    private var searchText: EditText? = null
    private val textChangeHandler = Handler {
        searchForECodes()
        true
    }

    /**
     * Called when the activity is first created.
     */
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        val actionBar: ActionBar = getSupportActionBar()
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.setCustomView(R.layout.search_view)
        searchText = actionBar.getCustomView().findViewById(R.id.text_search)
        searchText!!.addTextChangedListener(this)
        if (Consts.ACTION_VIEW_E_CODE.equals(Intent.getIntent().action)) {
            val i = Intent(this, ECodeViewActivity::class.java)
            i.putExtra(
                SearchManager.EXTRA_DATA_KEY,
                Intent.getIntent().getStringExtra(SearchManager.EXTRA_DATA_KEY)
            )
            ContextCompat.startActivity(i)
            finish()
        }
        init(ApplicationProvider.getApplicationContext<Context>())
        this.setListAdapter(ECodeAdapter(this, instance!!))
    }

    private fun createCodeList(searchString: String): Set<String?> {
        val result: MutableSet<String?> = HashSet()
        val tokens = searchString.split(" ").toTypedArray()
        for (token in tokens) {
            if (TextUtils.isDigitsOnly(token)) {
                result.add(token)
            } else {
                result.addAll(instance!!.textSearch(token))
            }
        }
        return result
    }

    private fun searchForECodes() {
        val text = searchText!!.text.toString().trim { it <= ' ' }
        val codes = createCodeList(text)
        this.setListAdapter(ECodeAdapter(this, instance!!.filter(codes)))
    }

    protected fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val code = getListAdapter().getItem(position) as ECode
        val intent = Intent(this, ECodeViewActivity::class.java)
        intent.putExtra("ecode", code)
        ContextCompat.startActivity(intent)
    }

    override fun afterTextChanged(s: Editable) {
        textChangeHandler.removeMessages(0)
        textChangeHandler.sendEmptyMessageDelayed(0, 200)
    }

    override fun beforeTextChanged(
        s: CharSequence, start: Int, count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
}