package com.example.employeestats.di

import com.example.employeestats.viewModel.ProfileViewModel
import com.example.employeestats.viewModel.EmployeesViewModel
import com.example.employeestats.viewModel.SpecialtyViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        SpecialtyViewModel(get())
    }

    viewModel {
        EmployeesViewModel(get())
    }

    viewModel {
        ProfileViewModel(get())
    }
}