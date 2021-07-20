/*
 * Copyright 2021 UET-AILAB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.modelviewintent.default_act_mvi

import android.app.Dialog
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.example.modelviewintent.R
import kotlinx.android.synthetic.main.dialog_default.*

class DefaultDialog(
    val activity: DefaultActivity,
    val listener: OnSaveDefaultListener
) : Dialog(activity) {
    companion object {
        val TAG = "DefaultDialog"
    }

    interface OnSaveDefaultListener {
        fun onSaveDefault()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // retrieve display dimensions
        val displayRectangle = Rect()
        val window: Window = activity.window
        window.decorView.getWindowVisibleDisplayFrame(displayRectangle)

        // inflate and adjust layout
        val inflater : LayoutInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout: View = inflater.inflate(R.layout.dialog_default, null)
        layout.minimumWidth = (displayRectangle.width() * 0.9f).toInt()
        layout.minimumHeight = (displayRectangle.height() * 0.9f).toInt()

        setContentView(layout)
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        setContentView(R.layout.dialog_save_annotate)

        bt_cancel.setOnClickListener { cancel() }
        bt_ok.setOnClickListener { onSaveClicked() }

    }


    private fun onSaveClicked() {
        listener.onSaveDefault()
        dismiss()
    }

}