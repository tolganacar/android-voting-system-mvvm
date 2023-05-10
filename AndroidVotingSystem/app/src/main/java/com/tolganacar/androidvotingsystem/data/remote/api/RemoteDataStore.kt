package com.tolganacar.androidvotingsystem.data.remote.api

import com.tolganacar.androidvotingsystem.data.remote.model.AuthResponse
import com.tolganacar.androidvotingsystem.data.remote.model.DataStoreResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataStore {
    suspend fun postLogin(email: String, password: String): Flow<AuthResponse>
    suspend fun postRegister(tcNo: String, fullName: String, email: String, password: String): Flow<DataStoreResponse>
}