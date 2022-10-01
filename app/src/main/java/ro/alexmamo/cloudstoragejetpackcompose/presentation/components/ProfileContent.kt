package ro.alexmamo.cloudstoragejetpackcompose.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.alexmamo.cloudstoragejetpackcompose.core.Constants.OPEN_GALLERY

@Composable
fun ProfileContent(
    openGallery: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(bottom = 64.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = openGallery
        ) {
            Text(
                text = OPEN_GALLERY,
                fontSize = 18.sp
            )
        }
    }

    GetImageFromDatabase(
        createProfileImageContent = { imageUrl ->
            ProfileImageContent(imageUrl)
        }
    )
}