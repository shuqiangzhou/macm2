package com.mail.comm.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImaUitlsX {

    companion object {

        fun disImg(context: Context, imgUrl: String, imgv: ImageView) {
            Glide.with(context)
                .load(imgUrl)
                .into(imgv)
        }
    }

}