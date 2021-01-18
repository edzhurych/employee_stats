package com.example.data.repository

import com.example.data.db.MainDao
import com.example.data.db.asEmployee
import com.example.data.db.asSpecialty
import com.example.data.network.NetworkApi
import com.example.domain.model.Employee
import com.example.domain.model.Specialty
import com.example.domain.repository.MainRepository
import com.example.domain.response.ResponseWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

internal class MainRepositoryImpl(
    private val api: NetworkApi,
    private val mainDao: MainDao,
) : MainRepository {

    override fun getSpecialties(): Flow<ResponseWrapper<List<Specialty>>> = flow {
        emit(ResponseWrapper.Loading(true))

        val response = api.getEmployees()
        emit(ResponseWrapper.Loading(false))
        if (response.isSuccessful) {
            mainDao.insertData2(response.body()?.response ?: listOf())
            emit(ResponseWrapper.Success(mainDao.getSpecialties().map { Specialty(it.specialtyId, it.name) }))
        } else {
            emit(ResponseWrapper.Failure(response.code(), response.errorBody().toString()))
        }
    }.flowOn(Dispatchers.IO)

    override fun getEmployees(specialtyId: Int): Flow<List<Employee>> = mainDao.getEmployeesBy(specialtyId).distinctUntilChanged().map {
        it.asEmployee()
    }

    override fun getEmployeeBy(employeeId: Int): Flow<Employee> = flow {
        val flow = mainDao.getEmployeeBy(employeeId).map {
            val specialty = mainDao.getSpecialtiesFor(it.employeeId)
            it.asEmployee().apply {
                this.specialty = specialty.asSpecialty()
            }
        }
        emitAll(flow)
    }.flowOn(Dispatchers.IO)
}
