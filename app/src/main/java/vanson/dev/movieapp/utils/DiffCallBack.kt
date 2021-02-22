package vanson.dev.movieapp.utils

import androidx.recyclerview.widget.DiffUtil

class DiffCallBackCast<T>(
    private val oldItems: List<T>,
    private val newItems: List<T>,
    private val getIdItem: (T) -> Any
)  : DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        TODO("Not yet implemented")
    }

    override fun getNewListSize(): Int {
        TODO("Not yet implemented")
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }
}