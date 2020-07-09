package com.perfm.finddoctorapp.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Doctor Details Not Valid")
class DoctorDetailNotValidExceptions(val msg: String): RuntimeException(msg){}

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Doctor Details Not Found")
class DoctorNotFoundException(val msg: String): RuntimeException(msg){}

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Details Not Found")
class DetailsNotFoundException(val msg: String): RuntimeException(msg){}

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Hospital Details Not Found")
class HospitalNotFoundException(val msg: String): RuntimeException(msg){}

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Hospital Details Not Valid")
class HospitalDetailNotValidException(val msg: String): RuntimeException(msg){}