package ru.blays.timetable.RecyclerViewItems

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.blays.timetable.R
import ru.blays.timetable.databinding.SimpleListItemBinding

class SimpleListItem(private val groupCode: String) : BindableItem<SimpleListItemBinding>() {

    override fun bind(viewBinding: SimpleListItemBinding, position: Int) {
        viewBinding.title.text = groupCode
    }

    override fun getLayout() = R.layout.simple_list_item

    override fun initializeViewBinding(view: View): SimpleListItemBinding {
        return SimpleListItemBinding.bind(view)
    }

}