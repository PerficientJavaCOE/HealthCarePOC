package com.perfm.finddoctorapp.apierror

data class ApiValidationError( val obj: String,  val message: String ): ApiSubError() {
     var field: String? = null
     var rejectedValue: Any? = null

     constructor(_obj: String, _message: String, _rejectedValue: Any?, _field: String): this(_obj, _message){
          rejectedValue = _rejectedValue
          field = _field
     }
}