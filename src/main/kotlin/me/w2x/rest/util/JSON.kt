package me.w2x.rest.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

object JSON {

    private val jsonMapper by lazy {
        ContextWrapper.getBean(ObjectMapper::class.java)
    }

    fun <T> parseJson(jsonStr: String?): T? {
        if (jsonStr == null) {
            return null
        }

        return jsonMapper.readValue<T>(
            jsonStr,
            object : TypeReference<T>() {}
        )
    }

    fun toJson(obj: Any?): String {
        return jsonMapper.writeValueAsString(obj)
    }

    fun readTree(jsonStr: String?): JsonNode? {
        if (jsonStr == null) {
            return null
        }

        return jsonMapper.readTree(jsonStr)
    }
}