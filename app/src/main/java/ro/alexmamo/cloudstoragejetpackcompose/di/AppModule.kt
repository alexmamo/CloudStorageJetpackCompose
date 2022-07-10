package ro.alexmamo.cloudstoragejetpackcompose.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ro.alexmamo.cloudstoragejetpackcompose.data.repository.ProfileImageRepositoryImpl
import ro.alexmamo.cloudstoragejetpackcompose.domain.repository.ProfileImageRepository

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideFirebaseStorage() =  Firebase.storage

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideProfileImageRepository(
        storage: FirebaseStorage,
        db: FirebaseFirestore
    ): ProfileImageRepository = ProfileImageRepositoryImpl(
        storage = storage,
        db = db
    )
}