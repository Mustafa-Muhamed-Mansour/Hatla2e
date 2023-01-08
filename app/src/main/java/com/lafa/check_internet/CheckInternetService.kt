package com.lafa.check_internet

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RelativeLayout
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.lafa.R


class CheckInternetService : BroadcastReceiver() {


    override fun onReceive(p0: Context, p1: Intent) {
        val dialog = Dialog(p0, android.R.style.Animation)
        dialog.setContentView(R.layout.cutsom_dialog_internet)
        val btnRetry = dialog.findViewById<MaterialButton>(R.id.btn_retry)
        val parentRelative =
            dialog.findViewById<RelativeLayout>(R.id.parent_relative_custom_dialog_internet)

        try {
            if (NetworkUtil.networkState(p0)) {
                dialog.dismiss()
            } else {
                dialog.show()
            }

        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

        btnRetry.setOnClickListener {
            try {
                if (NetworkUtil.networkState(p0)) {
                    dialog.dismiss()
//                    Navigation.findNavController(view).navigate(R.id.loginFragment)
                } else {
                    dialog.show()
                    Snackbar.make(parentRelative, "No Internet Connection", Snackbar.LENGTH_SHORT)
                        .show()
                }

            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }
    }
}