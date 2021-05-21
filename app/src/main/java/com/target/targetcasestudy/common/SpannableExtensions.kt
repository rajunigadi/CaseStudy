package com.target.targetcasestudy.common

import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.widget.TextView

fun SpannableString.spanWith(target: String, apply: SpannableBuilder.() -> Unit) {
    val builder = SpannableBuilder()
    apply(builder)

    val start = this.indexOf(target)
    val end =  start + target.length

    setSpan(builder.what, start, end, builder.flags)
}

class SpannableBuilder {
    lateinit var what: Any
    var flags: Int = 0
}

fun TextView.applyStrikeSpannableString(text: String) {
    val spannedText = SpannableString(text)
    spannedText.spanWith(text) {
        what = StrikethroughSpan()
        flags = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    }
    this.text = spannedText
}