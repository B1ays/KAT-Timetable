package ru.blays.timetable.UI.Screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import ru.blays.timetable.R
import ru.blays.timetable.UI.DataClasses.Animations
import ru.blays.timetable.UI.DataClasses.CardShape
import ru.blays.timetable.UI.DataClasses.DefaultPadding

class AboutScreen {

    @Composable
    fun Create() {
        val scrollState = rememberScrollState()
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .verticalScroll(state = scrollState)
            )
        {
            HeadItem()
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            AboutAuthor()
        }
    }

    @Composable
    private fun HeadItem() {
        val headText =
            "Приложение предназначено для удобного просмотра расписания, даже без интернета." +
                    "\nРазработано для всех студентов авиационного техникума.\nПриложение является учебным проектом с целью, как можно детальнее изучить разработку андроид приложений. Так что, если найдёте какие-то проблемы - пишите.\nПриложение будет улучшаться так быстро, как это возможно"
        var isExpanded by remember { mutableStateOf(false) }
        val onExpandChange = {
            isExpanded = !isExpanded
        }
        val modifier = Modifier.padding(6.dp)

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/+cV-dnkBU_rtjYjhi"))
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
                    .padding(
                        horizontal = DefaultPadding.CardHorizontalPadding,
                        vertical = DefaultPadding.CardVerticalPadding
                    )
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
                    .padding(
                        horizontal = DefaultPadding.CardHorizontalPadding,
                        vertical = DefaultPadding.CardVerticalPadding
                    )
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
                modifier = Modifier
                    .padding(
                        horizontal = DefaultPadding.CardHorizontalPadding,
                        vertical = DefaultPadding.CardVerticalPadding
                    )
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
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_github), contentDescription = "Icon",
                        modifier = Modifier.size(32.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary))

                    TextButton(onClick = { context.startActivity(intent) } ) {
                        Text(
                            text = "Исходный код на GitHub",
                            modifier = Modifier
                                .padding(start = 4.dp)
                        )
                    }
                }
            }

            Card(
                modifier = Animations.ModifierWithExpandAnimation
                    .padding(
                        horizontal = DefaultPadding.CardHorizontalPadding,
                        vertical = DefaultPadding.CardVerticalPadding
                    )
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
    private fun AboutAuthor() {

        val contactList = ContactList().List

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
            for (i in contactList.indices) {
                if (i < contactList.lastIndex) {
                    ContactItem(contact = contactList[i], CardShape.CardMid)
                } else if (i == contactList.lastIndex) {
                    ContactItem(contact = contactList[i], CardShape.CardEnd)
                }
            }
        }
    }

    @Composable
    private fun AuthorNick() {
        Card(
            modifier = Modifier
                .padding(
                    horizontal = DefaultPadding.CardHorizontalPadding,
                    vertical = DefaultPadding.CardVerticalPadding
                )
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
    private fun ContactItem(contact: Contact, cardShape: RoundedCornerShape) {

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
    data class Contact(
        val iconID: Int,
        val name: String,
        val link: String
    )

    inner class ContactList {
        val List = listOf(
            Contact(iconID = R.drawable.ic_vk, "Вконтакте", "https://vk.com/b1ays"),
            Contact(iconID = R.drawable.ic_telegram, "Telegram", "https://t.me/B1ays"),
            Contact(iconID = R.drawable.ic_4pda, "4PDA", "https://4pda.to/forum/index.php?showuser=7576426")
        )
    }
}