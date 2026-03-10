package com.example.urlshortenerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.urlshortenerapp.ui.LinksListScreen
import com.example.urlshortenerapp.ui.MainViewModel
import com.example.urlshortenerapp.ui.theme.UrlShortenerAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UrlShortenerAppTheme {
                val viewModel by viewModel<MainViewModel>()
                val linkListState by viewModel.linkListState.collectAsStateWithLifecycle()
                LinksListScreen(
                    modifier = Modifier.fillMaxSize(),
                    state = linkListState,
                    actions = viewModel
                )
            }
        }
    }
}
