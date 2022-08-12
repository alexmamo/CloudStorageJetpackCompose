package ro.alexmamo.cloudstoragejetpackcompose.domain.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import ro.alexmamo.cloudstoragejetpackcompose.domain.model.Response

interface ProfileImageRepository {
    fun addImageToFirebaseStorage(imageUri: Uri): Flow<Response<Uri>>

    fun addImageUrlToFirestore(downloadUrl: Uri): Flow<Response<Boolean>>

    fun getImageUrlFromFirestore(): Flow<Response<String>>
}