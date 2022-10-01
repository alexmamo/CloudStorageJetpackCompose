package ro.alexmamo.cloudstoragejetpackcompose.domain.repository

import android.net.Uri
import ro.alexmamo.cloudstoragejetpackcompose.domain.model.Response

interface ProfileImageRepository {
    suspend fun addImageToFirebaseStorage(imageUri: Uri): Response<Uri>

    suspend fun addImageUrlToFirestore(downloadUrl: Uri): Response<Boolean>

    suspend fun getImageUrlFromFirestore(): Response<String>
}