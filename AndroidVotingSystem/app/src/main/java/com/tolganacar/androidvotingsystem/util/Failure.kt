package com.tolganacar.androidvotingsystem.util

import java.io.IOException

sealed class Failure : IOException() {
    class ApiError(var code: Int, override var message: String) : Failure()
    class DataStoreFailure(override var message: String? = "Unknown Error") : Failure()
    class UnknownError(val exception: Exception) : Failure()
    class TimeOutError(override var message: String?) : Failure()
    class CustomException(override var message: String?) : Failure()
    class NoConnectivityError(override val message: String?) : Failure()
    object EmptyResponse : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}