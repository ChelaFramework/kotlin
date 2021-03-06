package chela.kotlin.view.property

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import chela.kotlin.android.ChApp

object PropImage:Property(){
    fun image(view: View, v:Any){
        if(view !is ImageView) return
        when(v){
            is Int -> view.setImageResource(v)
            is String -> view.setImageDrawable(ChApp.drawable(v))
            is Drawable -> view.setImageDrawable(v)
            is Bitmap -> view.setImageBitmap(v)
        }
    }
}