package com.tolganacar.androidvotingsystem.domain.register

import com.tolganacar.androidvotingsystem.base.BaseUseCase
import com.tolganacar.androidvotingsystem.data.remote.model.DataStoreResponse
import com.tolganacar.androidvotingsystem.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<RegisterUseCase.Params, DataStoreResponse>() {

    data class Params(
        val tcNo: String,
        val fullName: String,
        val emailText: String,
        val passwordText: String
    )

    override suspend fun execute(params: Params): Flow<DataStoreResponse> =
        authRepository.postRegister(params.tcNo, params.fullName, params.emailText, params.passwordText)

}