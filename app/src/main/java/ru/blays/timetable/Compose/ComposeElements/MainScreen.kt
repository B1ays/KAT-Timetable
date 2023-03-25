@file:OptIn(ExperimentalMaterial3Api::class)

package ru.blays.timetable.Compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.blays.timetable.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.objectBoxManager

@Preview
@Composable
fun RootElements() {
    val titleText by remember { mutableStateOf("Главная") }
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = titleText) })
    }
    ) {
        Frame(it)
    }
}

@Composable
fun Frame(paddingValues: PaddingValues) {
    Surface(
        modifier = Modifier.padding(paddingValues).fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        MaterialTheme {
            val groupList = objectBoxManager.getGroupListFromBox()
            SimpleList(list = groupList!!)
        }
    }
}

@Composable
fun SimpleList(list: List<GroupListBox>) {
    LazyColumn{
        itemsIndexed(list) {_, item ->
            SimpleCard(title = item)
        }
    }
}

@Composable
fun SimpleCard(title: GroupListBox) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp, vertical = 5.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Text(modifier = Modifier.padding(8.dp).fillMaxWidth(),text = title.groupCode, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.secondary)
    }
}