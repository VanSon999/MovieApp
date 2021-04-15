package vanson.dev.movieapp.Utils

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

/**
 * 重写布局管理器实现自适应高度
 * Created by nyw on 2017/6/4.
 */
class SyLinearLayoutManager : LinearLayoutManager {
    private val childDimensions = IntArray(2)
    private val childSize = DEFAULT_CHILD_SIZE
    private val hasChildSize = false

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    ) {
    }

    private val mMeasuredDimension = IntArray(2)
    override fun onMeasure(
        recycler: Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {
        val widthMode = View.MeasureSpec.getMode(widthSpec)
        val heightMode = View.MeasureSpec.getMode(heightSpec)
        val widthSize = View.MeasureSpec.getSize(widthSpec)
        val heightSize = View.MeasureSpec.getSize(heightSpec)
        var width = 0
        var height = 0
        Log.d("state:", state.toString())
        for (i in 0 until itemCount) {
            try {
                measureScrapChild(
                    recycler, i,
                    widthSpec,
                    View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                    mMeasuredDimension
                )
            } catch (e: IndexOutOfBoundsException) {
                e.printStackTrace()
            }
            if (orientation == HORIZONTAL) {
                width = width + mMeasuredDimension[0]
                if (i == 0) {
                    height = mMeasuredDimension[1]
                }
            } else {
                height = height + mMeasuredDimension[1]
                if (i == 0) {
                    width = mMeasuredDimension[0]
                }
            }
        }
        when (widthMode) {
            View.MeasureSpec.EXACTLY, View.MeasureSpec.AT_MOST, View.MeasureSpec.UNSPECIFIED -> {
            }
        }
        when (heightMode) {
            View.MeasureSpec.EXACTLY -> height = heightSize
            View.MeasureSpec.AT_MOST, View.MeasureSpec.UNSPECIFIED -> {
            }
        }
        setMeasuredDimension(widthSpec, height)
    }

    private fun measureScrapChild(
        recycler: Recycler,
        position: Int,
        widthSpec: Int,
        heightSpec: Int,
        measuredDimension: IntArray
    ) {
        val view = recycler.getViewForPosition(position)

        // For adding Item Decor Insets to view
//        super.measureChildWithMargins(view, 0, 0);
        if (view != null) {
            val p = view.layoutParams as RecyclerView.LayoutParams
            val childHeightSpec = ViewGroup.getChildMeasureSpec(
                heightSpec,
                paddingTop + paddingBottom, p.height
            )
            view.measure(widthSpec, childHeightSpec)
            measuredDimension[0] = view.measuredWidth + p.leftMargin + p.rightMargin
            measuredDimension[1] = view.measuredHeight + p.bottomMargin + p.topMargin
            recycler.recycleView(view)
        }
    }

    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
//        Logger.e("SyLinearLayoutManager state:" + state.toString());
        super.onLayoutChildren(recycler, state)
    }

//    override fun onScrollStateChanged(state: Int) {
//        requestLayout()
//        super.onScrollStateChanged(state)
//    }

    companion object {
        private const val CHILD_WIDTH = 0
        private const val CHILD_HEIGHT = 1
        private const val DEFAULT_CHILD_SIZE = 100
    }
}