package vanson.dev.movieapp.Utils

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerView(cxt : Context, att: AttributeSet) : RecyclerView(cxt , att) {
    var maxHeight = 0
    var widthSize = 0
    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        if(maxHeight != 0){
            super.onMeasure(widthSpec, maxHeight)
        }else{
            super.onMeasure(widthSpec, heightSpec)
        }
        if(maxHeight < measuredHeight){
            maxHeight = measuredHeight
        }
        widthSize = widthSpec
        super.onMeasure(widthSpec, maxHeight)
    }

    override fun onScrolled(dx: Int, dy: Int) {
        measure(widthSize, maxHeight)
        super.onScrolled(dx, dy)
    }
//    override fun onChildAttachedToWindow(child: View) {
//
//        super.onChildAttachedToWindow(child)
//    }
}