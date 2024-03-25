package com.bank.cardsapp.ui.list

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bank.cardsapp.R
import com.bank.cardsapp.domain.entity.Cards
import com.bank.cardsapp.ui.main.MainEvent
import com.bank.cardsapp.ui.main.MainViewModel
import com.bank.cardsapp.ui.util.ErrorMessage
import com.bank.cardsapp.ui.util.LoadingNextPageItem
import com.bank.cardsapp.ui.util.PageLoader
import com.bank.cardsapp.ui.util.app.AppPreferences
import com.bank.cardsapp.ui.util.theme.AppTheme
import java.util.Locale


@Composable
fun ListScreen(
    mainViewModel: MainViewModel,
    viewModel: ListViewModel = hiltViewModel()
) {
    val isOverlayVisible = remember { mutableStateOf(false) }
    val (selectedNumber, setSelectedNumber) = remember { mutableStateOf("") }
    val (selectedType, setSelectedType) = remember { mutableStateOf("") }
    val (selectedExpiryDate, setSelectedExpiryDate) = remember { mutableStateOf("") }
    val cardsPagingItems: LazyPagingItems<Cards> = viewModel.cardsState.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        Icons.Default.Menu,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(25.dp)
                    )
                }

                Text(
                    text = stringResource(id = R.string.app_name),
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                        .weight(1.0f),
                    textAlign = TextAlign.Center
                )

                IconButton(
                    onClick = {
                        if (mainViewModel.stateApp.theme == AppTheme.Light) {
                            AppPreferences.setTheme(AppTheme.Dark)
                            mainViewModel.onEvent(MainEvent.ThemeChange(AppTheme.Dark))
                        } else {
                            AppPreferences.setTheme(AppTheme.Light)
                            mainViewModel.onEvent(MainEvent.ThemeChange(AppTheme.Light))
                        }
                    }
                ) {
                    Icon(
                        painter = if (mainViewModel.stateApp.theme == AppTheme.Light)
                            painterResource(id = R.drawable.ic_dark_mode)
                        else
                            painterResource(id = R.drawable.ic_light_mode),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            item { Spacer(modifier = Modifier.padding(4.dp)) }
            items(cardsPagingItems.itemCount) { index ->
                val itemEntity = cardsPagingItems[index]
                Log.d("list",itemEntity?.iconResId.toString())
                if (itemEntity != null) {
                    ItemCards(
                        itemEntity,
                        onClick = {
                            setSelectedNumber(itemEntity.number)
                            setSelectedType(itemEntity.type)
                            setSelectedExpiryDate(itemEntity.expiryDate)
                            isOverlayVisible.value = true // Show the overlay
                        }
                    )
                }
            }

            cardsPagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = cardsPagingItems.loadState.refresh as LoadState.Error
                        item {
                            ErrorMessage(
                                modifier = Modifier.fillParentMaxSize(),
                                message = error.error.localizedMessage!!,
                                onClickRetry = { retry() })
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item { LoadingNextPageItem(modifier = Modifier) }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = cardsPagingItems.loadState.append as LoadState.Error
                        item {
                            ErrorMessage(
                                modifier = Modifier,
                                message = error.error.localizedMessage!!,
                                onClickRetry = { retry() })
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.padding(4.dp)) }
        }
    }

    if (isOverlayVisible.value) {
        CardDetailsOverlay(
            number = selectedNumber,
            type = selectedType,
            expiryDate = selectedExpiryDate,
            isVisible = isOverlayVisible.value,
            onDismissRequest = { isOverlayVisible.value = false }
        )
    }

}

@Composable
fun CardDetailsOverlay(
    number: String,
    type: String,
    expiryDate: String,
    isVisible: Boolean,
    onDismissRequest: () -> Unit
) {
    val formattedExpiryDate = expiryDate.split("-").let {
        if (it.size == 3) {
            // Format to "MM/yy"
            "${it[1]}/${it[0].substring(2)}"
        } else {
            expiryDate // Fallback to original string if not as expected
        }
    }
    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onDismissRequest
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Transparent)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {}
                    )
                    .padding(32.dp)
                    .aspectRatio(1.586f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.card),
                    contentDescription = "Credit Card",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = type.replace('_', ' ')
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                            style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(top = 11.dp, start = 20.dp)
                    )

                    Text(
                        text = number.filter { it.isDigit() }.chunked(4).joinToString("  "),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 22.sp, // Adjust font size for the card number
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 42.dp, start = 8.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp, end = 80.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Valid Thru: $formattedExpiryDate",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.White,
                                fontSize = 10.sp
                            ),
                        )
                    }
                }
            }
        }
    }
}


