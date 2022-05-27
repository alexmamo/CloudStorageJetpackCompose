package ro.alexmamo.cloudstoragejetpackcompose.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ro.alexmamo.cloudstoragejetpackcompose.core.Constants.IMAGES
import ro.alexmamo.cloudstoragejetpackcompose.data.repository.ProfileImageRepositoryImpl
import ro.alexmamo.cloudstoragejetpackcompose.domain.repository.ProfileImageRepository

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideImagesStorageReference() =  Firebase.storage.reference.child(IMAGES)

    @Provides
    fun provideImagesCollectionReference() = Firebase.firestore.collection(IMAGES)

    @Provides
    fun provideProfileImageRepository(
        imagesStorageRef: StorageReference,
        imagesCollRef: CollectionReference
    ): ProfileImageRepository = ProfileImageRepositoryImpl(
        imagesStorageRef = imagesStorageRef,
        imagesCollRef = imagesCollRef
    )
}