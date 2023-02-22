package ro.alexmamo.cloudstoragejetpackcompose.presentation.components

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ro.alexmamo.cloudstoragejetpackcompose.components.ProgressBar
import ro.alexmamo.cloudstoragejetpackcompose.core.Utils.Companion.print
import ro.alexmamo.cloudstoragejetpackcompose.domain.model.Response.*
import ro.alexmamo.cloudstoragejetpackcompose.presentation.ProfileViewModel

@Composable
fun GetImageFromDatabase(
    viewModel: ProfileViewModel = hiltViewModel(),
    createProfileImageContent: @Composable (imageUrl: String) -> Unit
) {
    when(val getImageFromDatabaseResponse = viewModel.getImageFromDatabaseResponse) {
        is Loading -> ProgressBar()
        is Success -> getImageFromDatabaseResponse.data?.let { imageUrl ->
            createProfileImageContent(imageUrl)
        }
        is Failure -> print(getImageFromDatabaseResponse.e)
    }
}