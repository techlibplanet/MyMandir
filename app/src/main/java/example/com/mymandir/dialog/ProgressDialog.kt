package com.example.mayank.kwizzapp.dialog

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import example.com.mymandir.R

class ProgressDialog {

    private lateinit var activity: Activity
    private lateinit var dialog: Dialog
    private var progressBar: ProgressBar? = null

    fun showProgressDialog(activity: Activity){
        this.activity = activity
        dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.progress_dialog_layout)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar = dialog.findViewById(R.id.progressBar)
        progressBar?.visibility = View.VISIBLE
        dialog.show()
    }



    fun hideProgressDialog(){
        progressBar?.visibility = View.GONE
        dialog.dismiss()
    }
}