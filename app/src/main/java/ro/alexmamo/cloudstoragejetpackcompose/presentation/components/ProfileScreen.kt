package ro.alexmamo.cloudstoragejetpackcompose.presentation.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult.ActionPerformed
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ro.alexmamo.cloudstoragejetpackcompose.components.ProgressBar
import ro.alexmamo.cloudstoragejetpackcompose.core.Constants.ALL_IMAGES
import ro.alexmamo.cloudstoragejetpackcompose.core.Constants.DISPLAY_IT_MESSAGE
import ro.alexmamo.cloudstoragejetpackcompose.core.Constants.IMAGE_SUCCESSFULLY_ADDED_MESSAGE
import ro.alexmamo.cloudstoragejetpackcompose.core.Utils.Companion.print
import ro.alexmamo.cloudstoragejetpackcompose.domain.model.Response.*
import ro.alexmamo.cloudstoragejetpackcompose.presentation.ProfileViewModel

@Composable
fun ProfileImageScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val galleryLauncher =  rememberLauncherForActivityResult(GetContent()) { imageUri ->
        imageUri?.let {
            viewModel.addImageToStorage(imageUri)
        }
    }

    Scaffold(
        content = { padding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(padding)
            ) {
                ProfileContent(
                    openGallery = {
                        galleryLauncher.launch(ALL_IMAGES)
                    }
                )
            }
        },
        scaffoldState = scaffoldState
    )

    when(val addImageToStorageResponse = viewModel.addImageToStorageResponse) {
        is Loading -> ProgressBar()
        is Success -> addImageToStorageResponse.data?.let { downloadUrl ->
            LaunchedEffect(downloadUrl) {
                viewModel.addImageToDatabase(downloadUrl)
            }
        }
        is Failure -> LaunchedEffect(Unit) {
            print(addImageToStorageResponse.e)
        }
    }

    fun showSnackBar() = coroutineScope.launch {
        val result = scaffoldState.snackbarHostState.showSnackbar(
            message = IMAGE_SUCCESSFULLY_ADDED_MESSAGE,
            actionLabel = DISPLAY_IT_MESSAGE
        )
        if (result == ActionPerformed) {
            viewModel.getImageFromDatabase()
        }
    }

    when(val addImageToDatabaseResponse = viewModel.addImageToDatabaseResponse) {
        is Loading -> ProgressBar()
        is Success -> addImageToDatabaseResponse.data?.let { isImageAddedToDatabase ->
            if (isImageAddedToDatabase) {
                LaunchedEffect(isImageAddedToDatabase) {
                    showSnackBar()
                }
            }
        }
        is Failure -> LaunchedEffect(Unit) {
            print(addImageToDatabaseResponse.e)
        }
    }
}