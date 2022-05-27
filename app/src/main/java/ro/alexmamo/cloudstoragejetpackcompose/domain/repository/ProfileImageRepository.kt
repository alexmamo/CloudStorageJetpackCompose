package ro.alexmamo.cloudstoragejetpackcompose.domain.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import ro.alexmamo.cloudstoragejetpackcompose.domain.model.Response

interface ProfileImageRepository {
    suspend fun addImageToFirebaseStorage(imageUri: Uri): Flow<Response<Uri>>

    suspend fun addImageToFirestore(downloadUrl: Uri): Flow<Response<Boolean>>

    suspend fun getImageFromFirestore(): Flow<Response<String>>
}