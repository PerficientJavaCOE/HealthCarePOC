package com.perfm.finddoctorapp.exception

import org.apache.commons.lang3.StringUtils
import java.util.stream.IntStream

class EntityNotFoundException : RuntimeException(){

    fun EntityNotFoundException(clazz: Class<*>, vararg searchParamsMap: String?) {
        var message: String? = generateMessage(clazz.simpleName, toMap<String, String>(String::class.java, String::class.java, *searchParamsMap))
        throw RuntimeException(message)
    }
/*
    @JvmOverloads  constructor(clazz: Class<*>, vararg searchParamsMap: String?){
        var message: String? = generateMessage(clazz.simpleName, toMap<String, String>(String::class.java, String::class.java, *searchParamsMap))
        super(message)
    }*/

    private fun generateMessage(entity: String, searchParams: Map<String, String>?): String? {
        return StringUtils.capitalize(entity) +
                " was not found for parameters " +
                searchParams
    }
    private fun <K, V> toMap(
            keyType: Class<K>, valueType: Class<V>, vararg entries: Any?): Map<K, V>? {
        require(entries.size % 2 != 1) { "Invalid entries" }
        return IntStream.range(0, entries.size / 2).map { i: Int -> i * 2 }
                .collect({ HashMap() },
                        { m: java.util.HashMap<K, V>, i: Int -> m[keyType.cast(entries[i])] = valueType.cast(entries[i + 1]) }) { obj: java.util.HashMap<K, V>, m: java.util.HashMap<K, V>? -> obj.putAll(m!!) }
    }
}