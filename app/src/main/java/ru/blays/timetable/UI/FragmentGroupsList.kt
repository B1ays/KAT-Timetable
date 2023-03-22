package ru.blays.timetable.UI

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupieAdapter
import ru.blays.timetable.ObjectBox.Boxes.GroupListBox_
import ru.blays.timetable.R
import ru.blays.timetable.RecyclerViewItems.SimpleListItem
import ru.blays.timetable.databinding.FragmentMainScreenListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentGroupsList.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentGroupsList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainScreenListBinding.inflate(inflater, container, false)
        val groupList = mutableListOf<SimpleListItem>()
        val get = groupListBox.all
        for (i in get.indices) { groupList.add(SimpleListItem(groupCode = get[i].groupCode, onClick = ::onClick)) }
        binding.simpleListRV.adapter = GroupieAdapter().apply { addAll(groupList) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).changeTitle(resources.getString(R.string.Toolbar_MainScreen_title))
    }

    private fun onClick(groupCode: String) {
        Log.d("tapLog", "tapped: $groupCode")
        val builder = groupListBox.query(GroupListBox_.groupCode.equal(groupCode)).build().find()
        val fragment = FragmentWeekTimeTable.newInstance(
            builder[0].href
        )
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.content, fragment).
            commit()
    }


        companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment FragmentGroupsList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            FragmentGroupsList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}