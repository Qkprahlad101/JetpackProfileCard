package com.example.jetpackprofilecard

import android.annotation.SuppressLint
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackprofilecard.ui.theme.MyTheme
import com.example.jetpackprofilecard.ui.theme.lightGreen
import com.example.jetpackprofilecard.ui.theme.veryLightGray

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AppBar()
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            color = Color.LightGray
        ) {
            Column(modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Top) {
                for(profile in userProfileList)
                    ProfileCard(profile)
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                Icons.Filled.Home,
                contentDescription = " ",
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        },
        title = { Text("Messaging App Users") }
    )
}

@Composable
fun ProfileCard(userProfile: UserProfile) {
    Card(
        shape = CircleShape,
        modifier = Modifier
            .padding(top = 4.dp, bottom = 2.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfilePicture(userProfile.drawableId, userProfile.onlineStatus)
            ProfileContent(userProfile.name, userProfile.onlineStatus)
        }
    }
}

@Composable
fun ProfilePicture(drawableId: Int, onlineStatus: Boolean) {
    Card(
        shape = CircleShape,
        border = BorderStroke(2.dp, if(onlineStatus) lightGreen else Color.Red),
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = " ",
            modifier = Modifier.size(72.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ProfileContent(userName: String, onlineStatus: Boolean) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = userName,
            color = if(onlineStatus) Color.Black else Color.Black.copy(alpha = 0.3f),
            style = MaterialTheme.typography.displaySmall
        )
        Text(
            text = if(onlineStatus) "Active" else "Away",
            color = if(onlineStatus) Color.Green.copy(alpha = 0.3f) else Color.Red.copy(alpha = 0.3f),
            style = MaterialTheme.typography.bodyMedium
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyTheme {
        MainScreen()
    }
}