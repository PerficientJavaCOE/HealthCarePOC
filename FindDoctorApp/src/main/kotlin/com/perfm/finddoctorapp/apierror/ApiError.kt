package com.perfm.finddoctorapp.apierror

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver
import com.perfm.finddoctorapp.util.LowerCaseClassNameResolver
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import java.time.LocalDateTime
import java.util.function.Consumer

data class ApiError(var status: HttpStatus)  {

    var message: String? = null
    var debugMessage: String? = null

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    var timestamp: LocalDateTime? = null

    var subErrors = ArrayList<ApiSubError>()

    constructor(_status: HttpStatus, _ex: Throwable ): this(_status) {
        message = "Unexpected error"
        debugMessage = _ex.localizedMessage
    }

    constructor(_status: HttpStatus,_message: String, _ex: Throwable ): this(_status, _ex) {
        message = _message
    }

    init {
        timestamp = LocalDateTime.now()
    }

    private fun addSubError(subError: ApiSubError?){
        if (subError != null) subErrors.add(subError)
    }

    private fun addValidationError(obj: String, field: String, rejectedValue: Any, message: String) {
        addSubError(ApiValidationError(obj, field, rejectedValue, message))
    }

    private fun addValidationError(obj: String, message: String) {
        addSubError(ApiValidationError(obj, message))
    }

    private fun addValidationError(fieldError: FieldError) {
        this.addValidationError(
                fieldError.objectName,
                fieldError.field,
                fieldError.rejectedValue!!,
                fieldError.defaultMessage!!)
    }

    fun addValidationErrors(fieldErrors: List<FieldError?>) {
        fieldErrors.forEach(Consumer { fieldError: FieldError? -> this.addValidationError(fieldError!!) })
    }

    private fun addValidationError(objectError: ObjectError) {
        this.addValidationError(
                objectError.objectName,
                objectError.defaultMessage!!)
    }

    fun addValidationError(globalErrors: List<ObjectError?>) {
        globalErrors.forEach(Consumer { objectError: ObjectError? -> this.addValidationError(objectError!!) })
    }

}