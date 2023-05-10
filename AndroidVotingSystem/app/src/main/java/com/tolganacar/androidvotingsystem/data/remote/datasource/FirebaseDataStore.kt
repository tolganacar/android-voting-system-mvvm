package com.tolganacar.androidvotingsystem.data.remote.datasource

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tolganacar.androidvotingsystem.data.remote.model.AuthResponse
import com.tolganacar.androidvotingsystem.data.remote.model.DataStoreResponse
import com.tolganacar.androidvotingsystem.data.remote.api.RemoteDataStore
import com.tolganacar.androidvotingsystem.util.Failure
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class FirebaseDataStore @Inject constructor() : RemoteDataStore {

    private val auth = Firebase.auth

    override suspend fun postLogin(
        email: String,
        password: String
    ): Flow<AuthResponse> = callbackFlow {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(
                        AuthResponse(
                            task.result?.user?.email,
                            task.result?.user?.displayName
                        )
                    ).isSuccess
                    close()
                } else {
                    close(Failure.DataStoreFailure(task.exception?.localizedMessage))
                }
            }


        awaitClose()
    }

    override suspend fun postRegister(
        tcNo: String,
        fullName: String,
        email: String,
        password: String
    ): Flow<DataStoreResponse> = callbackFlow {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(DataStoreResponse(true)).isSuccess
                    close()
                } else {
                    close(Failure.DataStoreFailure(task.exception?.localizedMessage))

                }
            }

        awaitClose()
    }

}