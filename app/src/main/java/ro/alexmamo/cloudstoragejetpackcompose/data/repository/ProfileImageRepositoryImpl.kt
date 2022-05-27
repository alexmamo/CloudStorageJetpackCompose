package ro.alexmamo.cloudstoragejetpackcompose.data.repository

import android.net.Uri
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import ro.alexmamo.cloudstoragejetpackcompose.core.Constants.CREATED_AT
import ro.alexmamo.cloudstoragejetpackcompose.core.Constants.PROFILE_IMAGE_NAME
import ro.alexmamo.cloudstoragejetpackcompose.core.Constants.UID
import ro.alexmamo.cloudstoragejetpackcompose.core.Constants.URL
import ro.alexmamo.cloudstoragejetpackcompose.domain.model.Response.*
import ro.alexmamo.cloudstoragejetpackcompose.domain.repository.ProfileImageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileImageRepositoryImpl @Inject constructor(
    private val imagesStorageRef: StorageReference,
    private val imagesCollRef: CollectionReference
) : ProfileImageRepository {
    override suspend fun addImageToFirebaseStorage(imageUri: Uri) = flow {
        try {
            emit(Loading)
            val downloadUrl = imagesStorageRef.child(PROFILE_IMAGE_NAME)
                .putFile(imageUri).await()
                .storage.downloadUrl.await()
            emit(Success(downloadUrl))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }

    override suspend fun addImageToFirestore(downloadUrl: Uri) = flow {
        try {
            emit(Loading)
            imagesCollRef.document(UID).set(mapOf(
                URL to downloadUrl,
                CREATED_AT to FieldValue.serverTimestamp()
            )).await()
            emit(Success(true))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }

    override suspend fun getImageFromFirestore() = flow {
        try {
            emit(Loading)
            val url = imagesCollRef.document(UID).get().await().getString(URL)
            emit(Success(url))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }
}