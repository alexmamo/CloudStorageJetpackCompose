package ro.alexmamo.cloudstoragejetpackcompose.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ro.alexmamo.cloudstoragejetpackcompose.core.Constants.OPEN_GALLERY
import ro.alexmamo.cloudstoragejetpackcompose.domain.model.Response.*
import ro.alexmamo.cloudstoragejetpackcompose.presentation.ProfileImageViewModel

@Composable
fun ProfileImageContent(
    viewModel: ProfileImageViewModel = hiltViewModel(),
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
        is Success -> {
            val imageUrl = getImageFromDatabaseResponse.data
            imageUrl?.let {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.padding(top = 64.dp).clip(CircleShape).width(160.dp).height(160.dp)
                    )
                }
            }
        }
        is Failure -> LaunchedEffect(Unit) {
            print(getImageFromDatabaseResponse.e)
        }
    }
}