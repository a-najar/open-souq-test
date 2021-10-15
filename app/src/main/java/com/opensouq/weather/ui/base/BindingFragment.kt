package com.opensouq.weather.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding


typealias Layout<B> = (LayoutInflater, ViewGroup?, Boolean) -> B

abstract class BindingFragment<B : ViewBinding, V : ViewModel>(
    private val layout: Layout<B>,
    private val viewModelClass: Class<V>
) : Fragment() {

    abstract fun viewCreated(binding: B, savedInstanceState: Bundle?)

    private var _binding: B? = null
    val binding: B
        get() = _binding!!
    lateinit var viewModel: V
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[viewModelClass]
        return layout(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated(binding, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}