package ru.blays.timetable.RecyclerViewItems

import android.view.View
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.BindableItem
import ru.blays.timetable.R
import ru.blays.timetable.databinding.MainItemBinding


class MainCardContainer(
    private val date: String? = "",
    private val items: List<BindableItem<*>>
) : BindableItem<MainItemBinding>() {

    override fun getLayout() = R.layout.main_item

    override fun bind(binding: MainItemBinding, position: Int) {
        binding.cardDate.text = date
        binding.rowRV.adapter = GroupieAdapter().apply { addAll(items) }
    }

    override fun initializeViewBinding(view: View): MainItemBinding {
        return MainItemBinding.bind(view)
    }
}