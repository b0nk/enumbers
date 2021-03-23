/*
 * This file is distributed under GPL v3 license 
 * http://www.gnu.org/licenses/gpl-3.0.html      
 *                                               
 * raven@acute-angle.net                         
 */
package com.example.enumberscontinued

import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
import java.util.*

class ECodeHandler(private val list: ECodeList) : DefaultHandler() {
    private var currentState = -1
    private var currentPurposeId = -1
    private val language: String
    private var currentECode: ECode? = null
    private val purposes: HashMap<Int, String?>
    private var currentString = StringBuilder()
    @Throws(SAXException::class)
    override fun endElement(uri: String, localName: String, qName: String) {
        super.endElement(uri, localName, qName)
        if (TAG_ENUMBER == localName) {
            list.add(currentECode)
            currentState = -1
        }
        when (currentState) {
            STATE_PURPOSE -> if (localName == language) purposes[currentPurposeId] = currentString.toString()
            STATE_NAME -> if (localName == language) currentECode!!.name = currentString.toString()
            STATE_EXTRA -> if (localName == language) currentECode?.extra = currentString.toString()
        }
    }

    @Throws(SAXException::class)
    override fun startDocument() {
        super.startDocument()
        list.clear()
    }

    @Throws(SAXException::class)
    override fun characters(ch: CharArray, start: Int, length: Int) {
        super.characters(ch, start, length)
        val value = String(ch, start, length).trim { it <= ' ' }
        if ("\n" == value || "" == value) return
        currentString.append(value)
    }

    @Throws(SAXException::class)
    override fun startElement(uri: String, localName: String, qName: String,
                              attributes: Attributes) {
        super.startElement(uri, localName, qName, attributes)
        currentString = StringBuilder()
        if (TAG_PURPOSE == localName) {
            if (currentState == STATE_ENUMBER) {
                val purposeId = attributes
                        .getValue("code").toInt()
                if (purposes.containsKey(purposeId)) currentECode!!.purpose = purposes[purposeId] else currentECode!!.purpose = null
            } else {
                currentState = STATE_PURPOSE
                currentPurposeId = attributes.getValue("id").toInt()
            }
        } else if (TAG_ENUMBER == localName) {
            currentState = STATE_ENUMBER
            currentECode = ECode()
            currentECode!!.eCode = attributes.getValue("id")
            currentECode!!.allergic = false
        } else if (TAG_DANGER == localName) {
            currentECode!!.setDanger(attributes
                    .getValue("value").toInt())
        } else if (TAG_VEGAN == localName) {
            currentECode!!.vegan = attributes.getValue("value").toInt()
        } else if (TAG_CHILD == localName) {
            currentECode!!.children = attributes
                    .getValue("value").toInt()
        } else if (TAG_ALLERGIC == localName) {
            currentECode!!.allergic = true
        } else if (TAG_NAME == localName) {
            currentState = STATE_NAME
        } else if (TAG_EXTRA == localName) {
            currentState = STATE_EXTRA
        }
    }

    companion object {
        private const val TAG_PURPOSE = "purpose"
        private const val TAG_ENUMBER = "enumber"
        private const val TAG_DANGER = "danger"
        private const val TAG_VEGAN = "veg"
        private const val TAG_CHILD = "child"
        private const val TAG_NAME = "name"
        private const val TAG_EXTRA = "extra"
        private const val TAG_ALLERGIC = "allergic"
        private const val STATE_PURPOSE = 0
        private const val STATE_ENUMBER = 1
        private const val STATE_NAME = 2
        private const val STATE_EXTRA = 3
    }

    init {
        language = Locale.getDefault().language
        purposes = HashMap()
    }
}