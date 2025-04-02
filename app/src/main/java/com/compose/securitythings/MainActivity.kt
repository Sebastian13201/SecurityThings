package com.compose.securitythings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.compose.securitythings.data.UiState
import com.compose.securitythings.presentation.CatsViewModel
import com.compose.securitythings.ui.theme.SecurityThingsTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: CatsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SecurityThingsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(

                        modifier = Modifier.padding(innerPadding),
                        viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier,viewModel: CatsViewModel) {
    val cats by viewModel.cats.collectAsState()

    when(val state = cats){
        is UiState.LOADING->{
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is UiState.SUCCESS->{
            LazyColumn {
                items(state.data) {  cat ->
                    Box(modifier.border(1.dp, Color.Gray)) {
                        Column(modifier.fillMaxWidth().padding(7.dp)) {
                            Text(text = "Id: ${cat.id}")
                            Text(text = "Description: ${cat.breeds?.firstOrNull()?.description ?: "No Description"}")
                            AsyncImage(
                                model = "${cat.url}",
                                contentDescription = "${cat.id}- image"
                            )

                        }
                    }
                }
            }
        }
        is UiState.ERROR->{
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "${state.message}")
            }
        }
    }
}

