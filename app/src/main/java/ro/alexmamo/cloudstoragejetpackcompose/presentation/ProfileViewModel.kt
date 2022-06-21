package ro.alexmamo.cloudstoragejetpackcompose.presentation

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ro.alexmamo.cloudstoragejetpackcompose.domain.model.Response
import ro.alexmamo.cloudstoragejetpackcompose.domain.model.Response.Success
import ro.alexmamo.cloudstoragejetpackcompose.domain.repository.ProfileImageRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: ProfileImageRepository
): ViewModel() {
    private val _addImageToStorageState = mutableStateOf<Response<Uri>>(Success(null))
    val addImageToStorageState: State<Response<Uri>> = _addImageToStorageState

    private val _addImageToDatabaseState = mutableStateOf<Response<Boolean>>(Success(null))
    val addImageToDatabaseState: State<Response<Boolean>> = _addImageToDatabaseState

    private val _getImageFromDatabaseState = mutableStateOf<Response<String>>(Success(null))
    val getImageFromDatabaseState: State<Response<String>> = _getImageFromDatabaseState

    fun addImageToStorage(imageUri: Uri) = viewModelScope.launch {
        repo.addImageToFirebaseStorage(imageUri).collect { response ->
            _addImageToStorageState.value = response
        }
    }

    fun addImageToDatabase(downloadUrl: Uri) = viewModelScope.launch {
        repo.addImageUrlToFirestore(downloadUrl).collect { response ->
            _addImageToDatabaseState.value = response
        }
    }

    fun getImageFromDatabase() = viewModelScope.launch {
        repo.getImageUrlFromFirestore().collect { response ->
            _getImageFromDatabaseState.value = response
        }
    }
}