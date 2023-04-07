package ru.blays.timetable.Compose.Screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.blays.timetable.Compose.helperClasses.Animations.ModifierWithExpandAnimation
import ru.blays.timetable.Compose.helperClasses.CardShape
import ru.blays.timetable.Compose.helperClasses.Contact
import ru.blays.timetable.Compose.helperClasses.ContactList

@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier.padding(10.dp)
    )
    {
        HeadItem()
        AboutAuthor()
    }
}

@Composable
fun HeadItem() {
    val headText =
        "Приложение предназначено для удобного просмотра расписания, даже без интернета." +
                "\nРазработано для всех студентов авиационного техникума.\nПриложение является учебным проектом с целью, как можно детальнее изучить разработку андроид приложений. Так что, если найдёте какие-то проблемы - пишите.\nПриложение будет улчшаться так быстро, как это возможно"
    var isExpanded by remember { mutableStateOf(false) }
    val onExpandChange = {
        isExpanded = !isExpanded
    }
    val modifier = Modifier.padding(6.dp)

    Column() {
        Text(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            text = "Описание",
            textAlign = TextAlign.Center,
            fontSize = 20.sp)

        Card(
            modifier = ModifierWithExpandAnimation
                .padding(12.dp)
                .fillMaxWidth(),
            shape = CardShape.CardStandalone
        ) {
            Text(
                modifier = modifier,
                text = headText,
                maxLines = if (isExpanded) Int.MAX_VALUE else 2)

            TextButton(onClick = onExpandChange) {
                Text(modifier = modifier, text = if (isExpanded) "Скрыть..." else "Показать полностью..." )
            }
        }
    }
}

@Composable
fun AboutAuthor() {
    Column {
        Text(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            text = "Контакты",
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )

        AuthorNick()
        for (i in ContactList.List.indices) {
            if (i < ContactList.List.lastIndex) {
                ContactItem(contact = ContactList.List[i], CardShape.CardMid)
            } else if (i == ContactList.List.lastIndex) {
                ContactItem(contact = ContactList.List[i], CardShape.CardEnd)
            }
        }
    }
}

@Composable
fun AuthorNick() {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 3.dp)
            .fillMaxWidth(),
        shape = CardShape.CardStart
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            text = "Сделано Blays." +
                    "\nСвязаться со мной можно здесь:"
        )
    }
}

@Composable
fun ContactItem(contact: Contact, cardShape: RoundedCornerShape) {

    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(contact.link))
    val context = LocalContext.current
    
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 3.dp)
            .fillMaxWidth(),
        shape = cardShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Image(
                imageVector = ImageVector.vectorResource(id = contact.iconID), contentDescription = "Icon",
                modifier = Modifier.size(32.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary))

            TextButton(onClick = { context.startActivity(intent)}) {
                Text(
                    text = "Я в ${contact.name}",
                    modifier = Modifier
                        .padding(start = 4.dp)
                )
            }
        }
    }

}