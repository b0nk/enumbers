/*
 * This file is distributed under GPL v3 license 
 * http://www.gnu.org/licenses/gpl-3.0.html      
 *                                               
 * raven@acute-angle.net                         
 */
package com.example.enumberscontinued

import android.content.Context
import android.util.Log
import org.xml.sax.SAXException
import java.io.IOException
import java.io.InputStream
import java.util.*
import javax.xml.parsers.ParserConfigurationException
import javax.xml.parsers.SAXParserFactory

open class ECodeList : ArrayList<ECode?>() {
    fun load(ctx: Context) {
        var `is`: InputStream? = null
        this.clear()
        try {
            val mgr = ctx.assets
            `is` = mgr.open("e.xml")
            loadFromStream(`is`)
        } catch (e: Exception) {
            Log.e("E", "Failed to load data", e)
        } finally {
            if (`is` != null) {
                try {
                    `is`.close()
                } catch (ignored: IOException) {
                }
            }
        }
    }

    @Throws(ParserConfigurationException::class, SAXException::class, IOException::class)
    private fun loadFromStream(`is`: InputStream) {
        val factory = SAXParserFactory.newInstance()
        val parser = factory.newSAXParser()
        parser.parse(`is`, ECodeHandler(this))
    }

    fun find(code: String): ECode? {
        val lcode = code.toLowerCase()
        for (ecode in this) {
            if (ecode?.eCode!!.contains(code)) {
                return ecode
            }
            if (ecode.name != null) {
                if (ecode.name!!.toLowerCase().contains(lcode)) return ecode
            }
        }
        return null
    }

    fun filter(codes: Collection<String?>): ECodeList {
        val result = ECodeList()
        for (code in this) {
            for (token in codes) if (code?.eCode!!.contains(token!!)) {
                result.add(code)
                break
            }
        }
        return result
    }

    fun textSearch(text: String?): List<String?> {
        val result: MutableList<String?> = LinkedList()
        for (code in this) {
            /*
             * int d = LevenshteinDistance.possibleMatch(text.toLowerCase(),
             * code.name.toLowerCase()); if (d <= 2) result.add(code.eCode);
             */
            if (text != null && code != null && code.name != null) if (code.name!!.toLowerCase().contains(text.toLowerCase())) result.add(code.eCode)
        }
        return result
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}