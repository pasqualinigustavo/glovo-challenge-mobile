package com.glovomap.utils

import android.content.Context
import android.support.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import com.glovomap.R

object AlertUtils {

    fun showMessage(context: Context, @StringRes message: Int) {
        AlertUtils.showMessage(context, context.getString(message))
    }

    fun showMessage(context: Context, message: String) {
        AlertUtils.showDialog(context, context.getString(R.string.label_attention), message)
    }

    fun showWarning(context: Context, @StringRes message: Int) {
        AlertUtils.showDialog(context, context.getString(R.string.label_attention), context.getString(message))
    }

    private fun showDialog(context: Context?, title: String, message: String) {
        context.let {
            val b = MaterialDialog.Builder(context!!)
            b.title(title)
            b.cancelable(false)
            b.theme(Theme.LIGHT)
            b.content(message)
            b.positiveText(context.getString(R.string.label_ok))
            b.onPositive { dialog, which -> dialog.dismiss() }
            b.show()
        }
    }
}
