package ro.alexmamo.cloudstoragejetpackcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ro.alexmamo.cloudstoragejetpackcompose.presentation.components.ProfileImageScreen

@AndroidEntryPoint
class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileImageScreen()
        }
    }
}