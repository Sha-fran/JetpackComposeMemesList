package com.example.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel:MemesViewModel by viewModels()
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "list" ) {
                composable("list") {
                    MemesListScreen(
                        viewModel = viewModel,
                        onCardClick = {title, url -> navController.navigate("details?title=$title&url=$url")}
                    )
                }
                composable("details?title={title}&url={url}", arguments = listOf(
                        navArgument("title") {type = NavType.StringType},
                        navArgument("url") {type = NavType.StringType})
                ) {
                    MemesDetailScreen(
                        title = it.arguments?.getString("title") ?: "",
                        url = it.arguments?.getString("url") ?: "")
                }
            }
        }
    }

    @Composable
    fun MemesListScreen(viewModel:MemesViewModel, onCardClick: (String, String) -> Unit) {
        val memesState = viewModel.uiState.observeAsState().value
        var memesList = emptyList<Memes>()

        viewModel.getMemes()

        if (memesState is MemesViewModel.UiState.FilledList) {
            memesList = memesState.memes
        }
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(items = memesList) {
                MemeCard(
                    title = it.name,
                    imageUrl = it.url,
                    onCardClick = {title, url -> onCardClick(title, url)})
            }
        }
    }

    @Composable
    fun MemesDetailScreen(title: String, url:String) {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ){
                AsyncImage(model = url, contentDescription = "")
                Text(text = title)
        }
    }

    @Composable
    fun MemeCard(title:String, imageUrl:String, onCardClick: (String, String) -> Unit) {
        Card(
            modifier = Modifier
                .requiredHeight(150.dp)
                .padding(16.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true, color = Color.DarkGray),
                    onClick = { onCardClick(title, imageUrl) }),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "",
                    modifier = Modifier
                        .weight(1F)
                        .fillMaxHeight()
                )
                Column(
                    modifier = Modifier
                        .weight(2F)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = title, fontSize = 18.sp)
                }
            }
        }
    }
}

//val navController = rememberNavController()
//NavHost(navController = navController, startDestination = "list") {
//    composable("list") {
//        ListScreen(onItemClick = {string -> navController.navigate("details?index=$string")})
//    }
//
//    composable("details?index={index}",
//        listOf(navArgument("index") {
//            type = NavType.StringType })
//    ) {
//        DetailScreen(index = it.arguments?.getString("index")?:"")
//    }
//}

//@Composable
//fun ListScreen(onItemClick:(String) -> Unit) {
//    LazyColumn(
//        contentPadding = PaddingValues(8.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp),
//        modifier = Modifier.fillMaxSize())
//    {
//        items(1000) {index ->
//            Text(text = "Item #$index", modifier = Modifier.clickable {
//                onItemClick(index.toString())
//            })
//        }
//    }
//}
//
//@Composable
//fun DetailScreen(index:String) {
//    Box (
//        contentAlignment = Alignment.Center,
//        modifier = Modifier.fillMaxSize())
//    {
//        Text(text = "Index = $index")
//    }
//}


