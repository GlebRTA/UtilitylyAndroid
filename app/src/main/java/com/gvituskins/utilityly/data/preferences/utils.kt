package com.gvituskins.utilityly.data.preferences
import android.content.SharedPreferences

inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = edit()
    operation(editor)
    editor.apply()
}
