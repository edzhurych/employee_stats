package com.example.domain.model

data class Employee(
    val employeeId: Int,
    val firstName: String?,
    val lastName: String?,
    val birthday: String?,
    val avatarUrl: String?,
    var specialty: List<Specialty>?,
)

data class Specialty(
    val specialtyId: Int?,
    val name: String?,
) {
    override fun toString(): String {
        return name ?: ""
    }
}

