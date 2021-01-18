package com.example.employeestats.viewModel

import androidx.lifecycle.ViewModel
import com.example.domain.model.Specialty
import com.example.domain.repository.MainRepository
import com.example.domain.response.ResponseWrapper
import kotlinx.coroutines.flow.Flow

class SpecialtyViewModel(
    private val repository: MainRepository,
) : ViewModel(){

    fun getSpecialties(): Flow<ResponseWrapper<List<Specialty>>> = repository.getSpecialties()
}
