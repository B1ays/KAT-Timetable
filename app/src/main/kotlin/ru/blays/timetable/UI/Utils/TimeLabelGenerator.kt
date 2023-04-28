package ru.blays.timetable.UI.Utils

import ru.blays.timetable.domain.models.GetSubjectsListModel

fun timeLabelGenerator(list: List<GetSubjectsListModel>) : Map<GetSubjectsListModel, String> {

    val map = mutableMapOf<GetSubjectsListModel, String>()

    for (subject in list) {
        when (subject.position.toInt()) {
            1 -> {
                map[subject] = "8:00"
            }
            2 -> {
                map[subject] = "9:30"
            }
            3 -> {
                if (subject.subtitle2.startsWith(prefix = "2", ignoreCase = true)) {
                    map[subject] = "11:50"
                } else {
                    map[subject] = "11:00"
                }
            }
            4 -> {
                map[subject] = "13:20"
            }
            5 -> {
                map[subject] = "14:50"
            }
            6 -> {
                map[subject] = "16:20"
            }
            7 -> {
                map[subject] = "17:50"
            }
        }
    }
    return map
}