package com.example.employeestats.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.domain.model.Employee
import com.example.domain.model.Specialty
import com.example.employeestats.R
import com.example.employeestats.databinding.EmployeeItemBinding
import com.example.employeestats.databinding.SpecialtyItemBinding
import com.example.employeestats.extensions.toAge
import com.example.employeestats.extensions.toCamelCase

class SpecialtyAdapter(var list: List<Specialty>) : BaseAdapter<SpecialtyItemBinding>() {

    fun setItems(items: List<Specialty>) {
        list = items
        notifyDataSetChanged()
    }

    override fun inflateLayout(inflater: LayoutInflater): SpecialtyItemBinding = SpecialtyItemBinding.inflate(inflater)

    override fun onBindViewHolder(holder: ViewHolder<SpecialtyItemBinding>, position: Int) {
        holder.vb.tvSpecialty.text = list[position].name
    }

    override fun getItemCount(): Int = list.size
}

class EmployeeAdapter(var list: List<Employee>) : BaseAdapter<EmployeeItemBinding>() {

    fun setItems(items: List<Employee>) {
        list = items
        notifyDataSetChanged()
    }

    override fun inflateLayout(inflater: LayoutInflater): EmployeeItemBinding = EmployeeItemBinding.inflate(inflater)

    override fun onBindViewHolder(holder: ViewHolder<EmployeeItemBinding>, position: Int) {
        holder.vb.tvEmployee.text = holder.vb.root.context.getString(
            R.string.employee_name,
            list[position].firstName?.toCamelCase(),
            list[position].lastName?.toCamelCase()
        )
        holder.vb.tvAge.text = list[position].birthday.toAge(holder.vb.root.context)
    }

    override fun getItemCount(): Int = list.size
}

abstract class BaseAdapter<VB : ViewBinding> : RecyclerView.Adapter<BaseAdapter.ViewHolder<VB>>() {

    class ViewHolder<VB : ViewBinding>(val vb: VB) : RecyclerView.ViewHolder(vb.root)

    protected abstract fun inflateLayout(inflater: LayoutInflater): VB

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder<VB> {
        val viewBinding = inflateLayout(LayoutInflater.from(viewGroup.context))

        return ViewHolder(viewBinding)
    }
}