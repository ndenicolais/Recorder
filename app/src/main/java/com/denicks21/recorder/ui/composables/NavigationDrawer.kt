package com.denicks21.recorder.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.denicks21.recorder.navigation.NavScreens
import com.denicks21.recorder.R
import com.denicks21.recorder.ui.theme.GreyDark
import com.denicks21.recorder.ui.theme.YellowDark

private val screens = listOf(
    NavScreens.HomePage,
    NavScreens.RecorderPlayerPage,
    NavScreens.AudioPlayerPage,
    NavScreens.InfoPage
)

@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(GreyDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                color = YellowDark,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(YellowDark)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            screens.forEach { screen ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { onDestinationClicked(screen.route) })
                        .height(45.dp)
                        .padding(start = 10.dp)
                ) {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = screen.title,
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth()
                    )
                }
                Divider(
                    color = GreyDark,
                    thickness = 0.5.dp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Developed by DeNicks21 \n" +
                        "v." + com.denicks21.recorder.BuildConfig.VERSION_NAME,
                modifier = Modifier.padding(bottom = 5.dp),
                color = GreyDark,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}