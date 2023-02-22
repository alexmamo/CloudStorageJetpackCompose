package ro.alexmamo.cloudstoragejetpackcompose.data.repository

import android.net.Uri
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import ro.alexmamo.cloudstoragejetpackcompose.core.Constants.CREATED_AT
import ro.alexmamo.cloudstoragejetpackcompose.core.Constants.IMAGES
import ro.alexmamo.cloudstoragejetpackcompose.core.Constants.PROFILE_IMAGE_NAME
import ro.alexmamo.cloudstoragejetpackcompose.core.Constants.UID
import ro.alexmamo.cloudstoragejetpackcompose.core.Constants.URL
import ro.alexmamo.cloudstoragejetpackcompose.domain.model.Response.Failure
import ro.alexmamo.cloudstoragejetpackcompose.domain.model.Response.Success
import ro.alexmamo.cloudstoragejetpackcompose.domain.repository.AddImageToStorageResponse
import ro.alexmamo.cloudstoragejetpackcompose.domain.repository.AddImageUrlToFirestoreResponse
import ro.alexmamo.cloudstoragejetpackcompose.domain.repository.GetImageUrlFromFirestoreResponse
import ro.alexmamo.cloudstoragejetpackcompose.domain.repository.ProfileImageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileImageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val db: FirebaseFirestore
) : ProfileImageRepository {
    override suspend fun addImageToFirebaseStorage(imageUri: Uri): AddImageToStorageResponse {
        return try {
            val downloadUrl = storage.reference.child(IMAGES).child(PROFILE_IMAGE_NAME)
                .putFile(imageUri).await()
                .storage.downloadUrl.await()
            Success(downloadUrl)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun addImageUrlToFirestore(downloadUrl: Uri): AddImageUrlToFirestoreResponse {
        return try {
            db.collection(IMAGES).document(UID).set(mapOf(
                URL to downloadUrl,
                CREATED_AT to FieldValue.serverTimestamp()
            )).await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun getImageUrlFromFirestore(): GetImageUrlFromFirestoreResponse {
        return try {
            val imageUrl = db.collection(IMAGES).document(UID).get().await().getString(URL)
            Success(imageUrl)
        } catch (e: Exception) {
            Failure(e)
        }
    }
}