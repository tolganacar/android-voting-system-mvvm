package com.tolganacar.androidvotingsystem.repository

import com.tolganacar.androidvotingsystem.data.remote.api.RemoteDataStore
import com.tolganacar.androidvotingsystem.data.remote.model.AuthResponse
import com.tolganacar.androidvotingsystem.data.remote.model.DataStoreResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val remoteDataStore: RemoteDataStore,
) {

    suspend fun postLogin(emailText: String, passwordText: String): Flow<AuthResponse> {
        return flow {
            remoteDataStore.postLogin(emailText, passwordText)
                .collect {
                    emit(it)
                }
        }
    }

    suspend fun postRegister(
        tcNo: String,
        fullName: String,
        emailText: String,
        passwordText: String
    ): Flow<DataStoreResponse> {
        return flow {
            remoteDataStore.postRegister(tcNo, fullName, emailText, passwordText)
                .collect {
                    emit(it)
                }
        }
    }

}