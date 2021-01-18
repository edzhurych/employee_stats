package com.example.employeestats.ui

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.employeestats.adapters.EmployeeAdapter
import com.example.employeestats.databinding.FragmentEmployeesBinding
import com.example.employeestats.listeners.ClickListener
import com.example.employeestats.listeners.RecyclerTouchListener
import com.example.employeestats.viewModel.EmployeesViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EmployeesFragment : BaseFragment<EmployeesViewModel, FragmentEmployeesBinding>(EmployeesViewModel::class) {

    private val mAdapter by lazy { EmployeeAdapter(listOf()) }
    private val args: EmployeesFragmentArgs by navArgs()

    override fun inflateLayout(): FragmentEmployeesBinding = FragmentEmployeesBinding.inflate(layoutInflater)

    override fun initialize() {
        with(args.specialtyId) {
            launch {
                vm.getEmployeesBy(this@with).collect {
                    mAdapter.setItems(it)
                    with(vb.rvEmployee) {
                        adapter = mAdapter
                        addOnItemTouchListener(
                            RecyclerTouchListener(
                                context,
                                this,
                                object : ClickListener {
                                    override fun onClick(view: View, position: Int) {
                                        val action = EmployeesFragmentDirections.actionEmployeesFragmentToEmployDetailsFragment(
                                            mAdapter.list[position].employeeId
                                        )
                                        findNavController().navigate(action)
                                    }

                                    override fun onLongClick(view: View, position: Int) {

                                    }
                                })
                        )
                    }
                }
            }
        }
    }
}