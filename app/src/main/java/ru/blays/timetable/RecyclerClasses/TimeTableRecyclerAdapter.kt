package ru.blays.timetable.RecyclerClasses

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.blays.timetable.databinding.RecyclerItemBinding

class TimeTableRecyclerAdapter(private val context: Context, private val subjectList:MutableList<String>)
    : RecyclerView.Adapter<TimeTableRecyclerAdapter.TimetableViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimetableViewHolder {
        val binding = RecyclerItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return TimetableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimetableViewHolder, position: Int) {
        val subjectItem = subjectList[position]
        holder.bind(subjectItem)
    }

    override fun getItemCount(): Int {
        return subjectList.size
    }


    class TimetableViewHolder(recyclerBinding: RecyclerItemBinding)
        : RecyclerView.ViewHolder(recyclerBinding.root){

        private val binding = recyclerBinding

        fun bind(cell: String){
            binding.textView1.text = cell/*.position*/
            /*binding.textView2.text = cell.subjectName*/
        }

    }
}

