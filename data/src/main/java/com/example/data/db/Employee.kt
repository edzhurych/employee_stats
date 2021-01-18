package com.example.data.db

import androidx.room.*
import com.google.gson.annotations.SerializedName


internal data class EmployeesResponse(
    @SerializedName("response")
    val response: List<Employee>
)

@Entity
internal data class Employee(
    @PrimaryKey
    var employeeId: Int = 0,
    @SerializedName("f_name")
    val firstName: String?,
    @SerializedName("l_name")
    val lastName: String?,
    @SerializedName("birthday")
    val birthday: String?,
    @SerializedName("avatr_url")
    val avatarUrl: String?,
) {
    @Ignore
    @Relation(
        parentColumn = "employeeId",
        entityColumn = "specialtyId"
    )
    @SerializedName("specialty")
    var specialty: List<Specialty>? = null
}

@Entity
internal data class Specialty(
    @PrimaryKey
    @SerializedName("specialty_id")
    var specialtyId: Int,
    @SerializedName("name")
    val name: String?,
)

@Entity(
    tableName = "relations",
    primaryKeys = ["employeeId", "specialtyId"],
    indices = [Index("employeeId"), Index("specialtyId")],
    foreignKeys = [
        ForeignKey(
            entity = Employee::class,
            parentColumns = ["employeeId"],
            childColumns = ["employeeId"],
        ),
        ForeignKey(
            entity = Specialty::class,
            parentColumns = ["specialtyId"],
            childColumns = ["specialtyId"],
        )
    ]
)
internal data class EmployeeToSpecialty(
    val employeeId: Int,
    val specialtyId: Int,
)


internal fun List<Employee>.relations(): List<EmployeeToSpecialty> {
    val set = hashSetOf<EmployeeToSpecialty>()
    forEach { e ->
        e.specialty?.forEach { s ->
            set.add(EmployeeToSpecialty(e.employeeId, s.specialtyId))
        }
    }
    return set.toList()
}

internal fun List<Employee>.asEmployee(): List<com.example.domain.model.Employee> {
    return arrayListOf<com.example.domain.model.Employee>().apply {
        this@asEmployee.forEach {
            this.add(
                com.example.domain.model.Employee(
                    employeeId = it.employeeId,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    birthday = it.birthday,
                    avatarUrl = it.avatarUrl,
                    specialty = it.specialty?.asSpecialty()
                )
            )
        }
    }
}

internal fun List<Specialty>.asSpecialty(): List<com.example.domain.model.Specialty> {
    return arrayListOf<com.example.domain.model.Specialty>().apply {
        this@asSpecialty.forEach {
            this.add(
                com.example.domain.model.Specialty(
                    specialtyId = it.specialtyId,
                    name = it.name
                )
            )
        }
    }
}

fun <T> T.insertOrUpdate(checkItemById: ((T) -> Any?), insert: ((T) -> Unit), update: ((T) -> Unit)) {
    if (checkItemById(this) == null) {
        insert(this)
    } else {
        update (this)
    }
}

fun <T> T.insertIfNotExist(checkItemById: ((T) -> Any?), insert: ((T) -> Unit)) {
    if (checkItemById(this) == null) {
        insert(this)
    }
}

internal fun Employee.asEmployee(): com.example.domain.model.Employee {
    return com.example.domain.model.Employee(
        employeeId = this.employeeId,
        firstName = this.firstName,
        lastName = this.lastName,
        birthday = this.birthday,
        avatarUrl = this.avatarUrl,
        specialty = this.specialty?.asSpecialty(),
    )
}