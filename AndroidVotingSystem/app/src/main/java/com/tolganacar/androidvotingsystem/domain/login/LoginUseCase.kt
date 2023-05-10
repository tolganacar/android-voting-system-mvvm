package com.tolganacar.androidvotingsystem.domain.login

import com.tolganacar.androidvotingsystem.base.BaseUseCase
import com.tolganacar.androidvotingsystem.data.remote.model.AuthResponse
import com.tolganacar.androidvotingsystem.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<LoginUseCase.Params, AuthResponse>() {

    data class Params(
        val emailText: String,
        val passwordText: String
    )

    override suspend fun execute(params: Params): Flow<AuthResponse> =
        authRepository.postLogin(params.emailText, params.passwordText)

}