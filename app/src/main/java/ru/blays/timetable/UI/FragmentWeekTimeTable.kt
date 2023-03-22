package ru.blays.timetable.UI

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupieAdapter
import kotlinx.coroutines.*
import ru.blays.timetable.ObjectBox.Boxes.GroupListBox_
import ru.blays.timetable.RecyclerViewItems.MainCardContainer
import ru.blays.timetable.RecyclerViewItems.RowItem
import ru.blays.timetable.databinding.FragmentWeekTimetableBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val HREF = "href"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentWeekTimeTable.newInstance] factory method to
 * create an instance of this fragment.
 */
@OptIn(DelicateCoroutinesApi::class)
class FragmentWeekTimeTable : Fragment() {
    // TODO: Rename and change types of parameters
    private var href: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            href = it.getString(HREF)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWeekTimetableBinding.inflate(inflater, container, false)
        Log.d("getSubjectsList", "return View")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = runBlocking {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentWeekTimetableBinding.bind(view)
        var timetable = mutableListOf<MainCardContainer>()
        Log.d("getSubjectsList", "Launch coroutine")
        val job = launch { timetable = getSubjectsList() }
        job.join()
        Log.d("getSubjectsList", "Init Recycler")
        binding.mainRV.adapter = GroupieAdapter().apply { addAll(timetable) }
    }


    private fun getHREF(): String? = requireArguments().getString(HREF)

    private suspend fun getSubjectsList(): MutableList<MainCardContainer> {
        val timetable = mutableListOf<MainCardContainer>()
        var subjectsList = mutableListOf<RowItem>()
        var builder = groupListBox.query(GroupListBox_.href.equal(getHREF()!!)).build().find()
        (activity as MainActivity).changeTitle(builder[0].groupCode)
        if (builder[0].days.isEmpty()) {
            Log.d("getSubjectsList", "Start runBlocking")
            val job = GlobalScope.launch {
                    htmlParser.getTimeTable(getHREF()!!)
                    Log.d("getSubjectsList", "End runBlocking")
                    builder = groupListBox.query(GroupListBox_.href.equal(getHREF()!!)).build().find()
                }
            job.join()
        }
            for (i in builder[0].days.indices) {
                val d = builder[0].days[i].day
                for (s in builder[0].days[i].subjects.indices) {
                    val sb = builder[0].days[i].subjects[s]
                    Log.d("getSubjectsList", sb.toString())
                    subjectsList.add(RowItem(sb))
                }
                timetable.add(MainCardContainer(date = d, subjectsList))
                subjectsList = mutableListOf()
            }
        Log.d("getSubjectsList", "return timetable")
        Log.d("getSubjectsList", timetable.toString())
        return timetable
    }

        companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment FragmentWeekTimeTable.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            FragmentWeekTimeTable().apply {
                arguments = Bundle().apply {
                    putString(HREF, param1)
                }
            }
    }
}