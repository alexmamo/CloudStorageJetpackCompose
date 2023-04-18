package ro.alexmamo.cloudstoragejetpackcompose.domain.repository

import android.net.Uri
import ro.alexmamo.cloudstoragejetpackcompose.domain.model.Response

typealias AddImageToStorageResponse = Response<Uri>
typealias AddImageUrlToFirestoreResponse = Response<Boolean>
typealias GetImageUrlFromFirestoreResponse = Response<String>

interface ProfileImageRepository {
    suspend fun addImageToFirebaseStorage(imageUri: Uri): AddImageToStorageResponse

    suspend fun addImageUrlToFirestore(downloadUrl: Uri): AddImageUrlToFirestoreResponse

    suspend fun getImageUrlFromFirestore(): GetImageUrlFromFirestoreResponse
}