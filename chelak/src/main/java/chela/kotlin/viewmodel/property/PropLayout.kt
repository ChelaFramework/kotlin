package chela.kotlin.viewmodel.property

import android.view.View
import android.view.ViewGroup

object PropLayout:Property(){
    @JvmStatic private inline fun param(view: View, block:(ViewGroup.LayoutParams)->ViewGroup.LayoutParams){
        view.layoutParams = block(view.layoutParams)
    }
    @JvmStatic fun width(view: View, v:Any){
        if(v !is Int) return
        param(view){
            it.width = v
            it
        }
    }
    @JvmStatic fun height(view: View, v:Any){
        if(v !is Int) return
        param(view){
            it.height = v
            it
        }
    }
    @JvmStatic fun margin(view: View, v:Any){
        if(v !is String) return
        param(view){
            val p:ViewGroup.MarginLayoutParams = it as? ViewGroup.MarginLayoutParams ?:
                ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT)
            val a = v.split(" ").map{it.toInt()}
            p.setMargins(a[3], a[0], a[1], a[2])//left,top,right,bottom
            p
        }
    }
}