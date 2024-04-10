package com.strangenaut.attendance.home.model

data class Credentials (
    val host: String = "",
    val id: String = "",
    val token: String = ""
) {

    override fun toString(): String {
        return "$host/$id/$token"
    }
}