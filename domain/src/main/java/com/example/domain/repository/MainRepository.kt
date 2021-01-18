package com.example.domain.repository

import com.example.domain.model.Employee
import com.example.domain.model.Specialty
import com.example.domain.response.ResponseWrapper
import kotlinx.coroutines.flow.Flow


interface MainRepository {

    fun getSpecialties(): Flow<ResponseWrapper<List<Specialty>>>
    fun getEmployees(specialtyId: Int): Flow<List<Employee>>
    fun getEmployeeBy(employeeId: Int): Flow<Employee>
}