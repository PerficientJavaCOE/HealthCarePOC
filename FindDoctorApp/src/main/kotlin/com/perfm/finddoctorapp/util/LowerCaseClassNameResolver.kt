package com.perfm.finddoctorapp.util

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase

class LowerCaseClassNameResolver: TypeIdResolverBase() {
    override fun idFromValue(p0: Any): String? {
        return idFromValueAndType(p0, p0.javaClass)
    }

    override fun idFromValueAndType(p0: Any, p1: Class<*>): String? {
        return idFromValue(p0)
    }

    override fun getMechanism(): JsonTypeInfo.Id {
        return JsonTypeInfo.Id.MINIMAL_CLASS
    }

}