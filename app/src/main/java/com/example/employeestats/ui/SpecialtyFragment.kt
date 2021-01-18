package com.example.employeestats.ui

import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.domain.response.ResponseWrapper
import com.example.employeestats.R
import com.example.employeestats.adapters.SpecialtyAdapter
import com.example.employeestats.databinding.FragmentSpecialtyBinding
import com.example.employeestats.listeners.ClickListener
import com.example.employeestats.listeners.RecyclerTouchListener
import com.example.employeestats.viewModel.SpecialtyViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SpecialtyFragment :
    BaseFragment<SpecialtyViewModel, FragmentSpecialtyBinding>(SpecialtyViewModel::class) {

    private val mAdapter: SpecialtyAdapter by lazy { SpecialtyAdapter(arrayListOf()) }

    override fun inflateLayout(): FragmentSpecialtyBinding =
        FragmentSpecialtyBinding.inflate(layoutInflater)

    override fun initialize() {
        launch {
            vm.getSpecialties().collect {
                when (it) {
                    is ResponseWrapper.Loading -> {
                        vb.pbSpecialty.isVisible = it.isLoading
                    }
                    is ResponseWrapper.Success -> {
                        mAdapter.setItems(it.value)
                        with(vb.rvSpecialty) {
                            adapter = mAdapter
                            addOnItemTouchListener(
                                RecyclerTouchListener(
                                    context,
                                    this,
                                    object : ClickListener {
                                        override fun onClick(view: View, position: Int) {
                                            val action = SpecialtyFragmentDirections.actionSpecialtyFragmentToEmployeesFragment(
                                                mAdapter.list[position].specialtyId ?: return
                                            )
                                            findNavController().navigate(action)
                                        }

                                        override fun onLongClick(view: View, position: Int) {

                                        }
                                    })
                            )
                        }
                    }
                    is ResponseWrapper.Failure -> {
                        Toast.makeText(requireContext(), R.string.error_message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}