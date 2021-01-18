package com.example.employeestats.viewModel

import androidx.lifecycle.ViewModel
import com.example.domain.repository.MainRepository

class ProfileViewModel(
    private val repository: MainRepository,
) : ViewModel() {

    fun getEmployee(employeeId: Int) = repository.getEmployeeBy(employeeId)
}