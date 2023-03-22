package ru.blays.timetable.RecyclerViewItems

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.blays.timetable.ObjectBox.Boxes.SubjectsListBox
import ru.blays.timetable.R
import ru.blays.timetable.databinding.RowItemBinding

class RowItem(private val subjectsListBox: SubjectsListBox) : BindableItem<RowItemBinding>() {
    override fun bind(viewBinding: RowItemBinding, position: Int) {
        viewBinding.rowPosition.text = subjectsListBox.position
        viewBinding.rowSubjectName.text = subjectsListBox.subject
        viewBinding.rowLecturer.text = subjectsListBox.lecturer
        viewBinding.rowAuditory.text = subjectsListBox.auditory
    }

    override fun getLayout() = R.layout.row_item

    override fun initializeViewBinding(view: View): RowItemBinding {
        return RowItemBinding.bind(view)
    }

}