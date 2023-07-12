package com.example.jetpackprofilecard


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetpackprofilecard.ui.theme.MyTheme
import com.example.jetpackprofilecard.ui.theme.lightGreen
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                UserApplication()
            }
        }
    }
}


@Composable
fun UserApplication(userProfile: List<UserProfile> = userProfileList) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "users_list") {
        composable("users_list") {
            UserListScreen(userProfile, navController)
        }
        composable(
            route = "user_details/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.IntType
            })
        ) {navStackBackEntry ->
            UserProfileDetailsScreen(navStackBackEntry.arguments!!.getInt("userId"))
        }
    }
}

@Composable
fun UserListScreen(
    userProfile: List<UserProfile> = userProfileList,
    navController: NavHostController?
) {
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
            LazyColumn {
                items(userProfile) { user ->
                    ProfileCard(userProfile = user) {
                        navController?.navigate("user_details/${user._id}")  // select navigate having parameter "route" only else it will fail
                    }
                }
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
fun ProfileCard(userProfile: UserProfile, clickAction: () -> Unit) {
    Card(
        shape = CircleShape,
        modifier = Modifier
            .padding(top = 4.dp, bottom = 2.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .clickable { clickAction.invoke() }
            .wrapContentHeight(align = Alignment.Top)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfilePicture(userProfile.pictureUrl, userProfile.status, 72.dp)
            ProfileContent(userProfile.name, userProfile.status, Alignment.Start)
        }
    }
}

@Composable
fun ProfilePicture(pictureUrl: String, onlineStatus: Boolean, imageSize: Dp) {
    Card(
        shape = CircleShape,
        border = BorderStroke(2.dp, if (onlineStatus) lightGreen else Color.Red),
        modifier = Modifier.padding(16.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(pictureUrl)
                .crossfade(true)
                .build(),
            modifier = Modifier.size(imageSize),
            contentDescription = null,
        )
    }
}


@Composable
fun ProfileContent(userName: String, onlineStatus: Boolean, horizontalAlign: Alignment.Horizontal) {
    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = horizontalAlign
    ) {
        Text(
            text = userName,
            color = if (onlineStatus) Color.Black else Color.Black.copy(alpha = 0.3f),
            style = MaterialTheme.typography.displaySmall
        )
        Text(
            text = if (onlineStatus) "Active" else "Away",
            color = if (onlineStatus) Color.Black else Color.Red.copy(alpha = 0.3f),
            style = MaterialTheme.typography.bodyMedium
        )
    }

}

@Composable
fun UserProfileDetailsScreen(userId: Int) {
    val userProfile = userProfileList.first{userProfile ->
        userId == userProfile._id
    }
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                ProfilePicture(userProfile.pictureUrl, userProfile.status, 240.dp)
                ProfileContent(userProfile.name, userProfile.status, Alignment.CenterHorizontally)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfileDetailsPreview() {
    MyTheme {
        UserProfileDetailsScreen(userId = 0)
    }
}

@Preview(showBackground = true)
@Composable
fun UserListPreview() {
    MyTheme {
        UserListScreen(userProfile = userProfileList, null)
    }
}