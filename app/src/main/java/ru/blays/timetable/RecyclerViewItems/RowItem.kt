package ru.blays.timetable.RecyclerViewItems

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.blays.timetable.Models.SecTableModel
import ru.blays.timetable.R
import ru.blays.timetable.databinding.RowItemBinding

class RowItem(private val secTableModel: SecTableModel) : BindableItem<RowItemBinding>() {
    override fun bind(viewBinding: RowItemBinding, position: Int) {
        viewBinding.rowPosition.text = secTableModel.position
        viewBinding.rowSubjectName.text = secTableModel.subject
        viewBinding.rowLecturer.text = secTableModel.lecturer
        viewBinding.rowAuditory.text = secTableModel.auditory
    }

    override fun getLayout() = R.layout.row_item

    override fun initializeViewBinding(view: View): RowItemBinding {
        return RowItemBinding.bind(view)
    }

}