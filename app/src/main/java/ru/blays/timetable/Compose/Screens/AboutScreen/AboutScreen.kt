package ru.blays.timetable.Compose.Screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.blays.timetable.BuildConfig
import ru.blays.timetable.Compose.HelperClasses.Animations.ModifierWithExpandAnimation
import ru.blays.timetable.Compose.HelperClasses.CardShape
import ru.blays.timetable.Compose.HelperClasses.Contact
import ru.blays.timetable.Compose.HelperClasses.ContactList
import ru.blays.timetable.Compose.HelperClasses.DefaultPadding
import ru.blays.timetable.R

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

    val intent = Intent(/* action = */ Intent.ACTION_VIEW, /* uri = */ Uri.parse("https://t.me/+cV-dnkBU_rtjYjhi"))
    val context = LocalContext.current

    Column {
        Text(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            text = "О приложении ",
            textAlign = TextAlign.Center,
            fontSize = 20.sp)

        Card(
            modifier = Modifier
                .padding(horizontal = DefaultPadding.CardHorizontalPadding, vertical = DefaultPadding.CardVerticalPadding)
                .fillMaxWidth(),
            shape = CardShape.CardStart
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Icon",
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            color = Color(0xFF80BAE2),
                            shape = CircleShape
                        ),
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "КАТ - Расписание",
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Версия: ${BuildConfig.VERSION_NAME} - ${BuildConfig.BUILD_TYPE}",
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .padding(horizontal = DefaultPadding.CardHorizontalPadding, vertical = DefaultPadding.CardVerticalPadding)
                .fillMaxWidth(),
            shape = CardShape.CardMid
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_telegram), contentDescription = "Icon",
                    modifier = Modifier.size(32.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary))

                TextButton(onClick = { context.startActivity(intent) } ) {
                    Text(
                        text = "Канал в Telegram",
                        modifier = Modifier
                            .padding(start = 4.dp)
                    )
                }
            }
        }

        Card(
            modifier = ModifierWithExpandAnimation
                .padding(horizontal = DefaultPadding.CardHorizontalPadding, vertical = DefaultPadding.CardVerticalPadding)
                .fillMaxWidth(),
            shape = CardShape.CardEnd
        ) {
            Text(
                modifier = modifier,
                text = headText,
                maxLines = if (isExpanded) Int.MAX_VALUE else 2)


            Text(modifier = modifier
                .clip(CircleShape)
                .toggleable(value = isExpanded) { onExpandChange() },
                text = if (isExpanded) "Скрыть..." else "Показать полностью...",
                color = MaterialTheme.colorScheme.primary
            )
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
            .padding(horizontal = DefaultPadding.CardHorizontalPadding, vertical = DefaultPadding.CardVerticalPadding)
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
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Image(
                imageVector = ImageVector.vectorResource(id = contact.iconID), contentDescription = "Icon",
                modifier = Modifier.size(32.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary))

            TextButton(onClick = { context.startActivity(intent) } ) {
                Text(
                    text = "Я в ${contact.name}",
                    modifier = Modifier
                        .padding(start = 4.dp)
                )
            }
        }
    }
}