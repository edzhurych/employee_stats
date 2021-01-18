package com.example.employeestats.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

abstract class BaseFragment<VM : ViewModel, VB : ViewBinding>(
    clazz: KClass<VM>
) : Fragment(), CoroutineScope {
    private val job: Job by lazy { SupervisorJob() }
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    protected val vm: VM by viewModel(clazz = clazz)
    protected lateinit var vb: VB

    protected abstract fun inflateLayout(): VB
    protected open fun initialize() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = inflateLayout()
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}