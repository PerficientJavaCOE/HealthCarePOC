package com.perfm.finddoctorapp.exception

import com.perfm.finddoctorapp.apierror.ApiError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(DetailsNotFoundException::class)
    fun handleNotFoundException(ex: DetailsNotFoundException): ResponseEntity<Any> {
        val apiError = ApiError(HttpStatus.NOT_FOUND)
        apiError.message = ex.message
        return buildResponseEntity(apiError)
    }

    @ExceptionHandler(DoctorDetailNotValidExceptions::class)
    fun handleDetailsNotValidException(ex: DoctorDetailNotValidExceptions): ResponseEntity<Any> {
        val apiError = ApiError(HttpStatus.BAD_REQUEST)
        apiError.message = ex.message
        return buildResponseEntity(apiError)
    }

    @ExceptionHandler(HospitalDetailNotValidException::class)
    fun handleHospitalDetailNotValidException(ex: HospitalDetailNotValidException): ResponseEntity<Any> {
        val apiError = ApiError(HttpStatus.BAD_REQUEST)
        apiError.message = ex.message
        return buildResponseEntity(apiError)
    }

    private fun buildResponseEntity(apiError: ApiError): ResponseEntity<Any> = ResponseEntity(apiError, apiError.status)
}