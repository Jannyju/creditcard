package com.bank.cardsapp.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bank.cardsapp.domain.entity.Cards
import com.bank.cardsapp.ui.util.theme.AppTheme
import com.bank.cardsapp.ui.util.theme.CardsTheme
import kotlin.random.Random

@Composable
fun ItemCards(itemEntity: Cards, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter = painterResource(id = itemEntity.iconResId)

            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "ID: ${itemEntity.id}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formatCardNumber(itemEntity.number),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

fun formatCardNumber(number: String): String {
    val parts = number.split("-")
    if (parts.size == 4) {
        return "${parts[0]} **** **** ${parts[3]}"
    }
    return number
}

@Preview(showBackground = true)
@Composable
fun ItemCardsPreview() {
    CardsTheme(appTheme = AppTheme.Default) {
        Surface {
            ItemCards(
                itemEntity = Cards(
                    id = 7295,
                    uid = "",
                    type = "discover",
                    number = "1211-1221-1234-2201",
                    expiryDate = "2026-03-23",
                    iconResId = 1
                ),
                onClick = {},
            )
        }
    }
}
