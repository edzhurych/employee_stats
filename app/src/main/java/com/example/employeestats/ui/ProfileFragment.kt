package com.example.employeestats.ui

import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.example.employeestats.R
import com.example.employeestats.databinding.FragmentProfileBinding
import com.example.employeestats.extensions.parseBirthday
import com.example.employeestats.extensions.toAge
import com.example.employeestats.extensions.toCamelCase
import com.example.employeestats.viewModel.ProfileViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>(ProfileViewModel::class) {

    private val args: ProfileFragmentArgs by navArgs()

    override fun inflateLayout(): FragmentProfileBinding = FragmentProfileBinding.inflate(layoutInflater)

    override fun initialize() {
        launch {
            vm.getEmployee(args.employeeId).collect {
                vb.tvName.text = getString(
                    R.string.employee_name,
                    it.firstName?.toCamelCase(),
                    it.lastName?.toCamelCase(),
                )
                vb.tvBirthday.text = it.birthday.parseBirthday()
                val age = it.birthday?.toAge(requireContext())
                vb.tvAge.text = age
                vb.tvAge.isVisible = !age.isNullOrEmpty()

                vb.tvSpecialty.text = it.specialty?.joinToString(", ")
            }
        }
    }
}