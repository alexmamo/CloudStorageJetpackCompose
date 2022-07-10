package ro.alexmamo.cloudstoragejetpackcompose.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ro.alexmamo.cloudstoragejetpackcompose.components.ProgressBar
import ro.alexmamo.cloudstoragejetpackcompose.core.Constants.OPEN_GALLERY
import ro.alexmamo.cloudstoragejetpackcompose.domain.model.Response.*
import ro.alexmamo.cloudstoragejetpackcompose.presentation.ProfileViewModel

@Composable
fun ProfileImageContent(
    viewModel: ProfileViewModel = hiltViewModel(),
    openGallery: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(bottom = 64.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = {
                openGallery()
            }
        ) {
            Text(
                text = OPEN_GALLERY,
                fontSize = 18.sp
            )
        }
    }

    when(val getImageFromDatabaseResponse = viewModel.getImageFromDatabaseState.value) {
        is Loading -> ProgressBar()
        is Success -> getImageFromDatabaseResponse.data?.let { imageUrl ->
            ProfileImageContent(imageUrl)
        }
        is Failure -> LaunchedEffect(Unit) {
            print(getImageFromDatabaseResponse.e)
        }
    }
}