package com.example.employeestats.viewModel

import androidx.lifecycle.ViewModel
import com.example.domain.model.Employee
import com.example.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class EmployeesViewModel(
    private val repository: MainRepository,
) : ViewModel() {

    fun getEmployeesBy(specialtyId: Int): Flow<List<Employee>> = repository.getEmployees(specialtyId)
}