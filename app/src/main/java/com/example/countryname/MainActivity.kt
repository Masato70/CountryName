package com.example.countryname

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.countryname.ui.theme.CountryNameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountryNameTheme() {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        FirstScreen(onContinueClicked = { shouldShowOnboarding = false })
    } else {
        CountryNames()
    }
}

@Composable
fun FirstScreen(onContinueClicked: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "ようこそ！")
            Button(onClick = onContinueClicked) {
                Text(text = "進める")
            }
        }
    }
}

@Composable
fun CountryNames(names: List<String> = listOf("アメリカ", "イギリス", "イタリア", "日本", "韓国", "中国")) {
    Surface {
        LazyColumn(modifier = Modifier.padding(vertical = 2.dp)) {
            items(items = names) { name ->
                CountryNamesCard(name)
            }
        }
    }
}

@Composable
fun CountryNamesCard(name: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CountryCardContent(name)
    }


}

@Composable
fun CountryCardContent(name: String) {

    var expanded by remember { mutableStateOf(false) }

    Surface {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 100,
                        delayMillis = 50,
                        easing = LinearEasing
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f)
            ) {
                Text(
                    name,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )

                if (expanded) {
                    Text(
                        text = "国名です",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
            }

            Button(onClick = { expanded = !expanded }) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)

                )
                if (!expanded) Text(text = "説明を見る") else Text(text = "説明を閉じる")

            }
        }
    }
}


@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark_Mode"
)

@Preview(showBackground = true, widthDp = 320)
@Composable
fun Light_Mode() {
    CountryNameTheme {
        CountryNames()
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 200)
@Composable
fun DefaultPreview() {
    CountryNameTheme {
        MyApp()
    }
}