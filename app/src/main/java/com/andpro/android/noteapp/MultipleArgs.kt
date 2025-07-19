package com.andpro.android.noteapp

import android.graphics.Bitmap
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
sealed class MultipleArgs : Parcelable {
    companion object {
        fun isEmpty(theclass: MultipleArgs, _str: String = ""): Boolean {
            when (theclass) {
                is MultipleArgs.Image -> {
                    if (theclass.title == "" || theclass.comment == "" || _str == "true") {
                        return true
                    } else {
                        return false
                    }
                }

                is MultipleArgs.Text -> {
                    if (theclass.title == "" || theclass.content == "" ) {
                        return true
                    } else {
                        return false
                    }
                }
            }
        }
    }

    data class Text(val title: String, val content: String) : MultipleArgs()
    data class Image(val title: String, val comment: String, val image: Bitmap) : MultipleArgs()
}