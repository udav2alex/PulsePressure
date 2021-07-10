package ru.gressor.pulsepressure.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.gressor.pulsepressure.databinding.MainFragmentBinding
import ru.gressor.pulsepressure.ui.BaseFragment

class MainFragment : BaseFragment<MainFragmentBinding>() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?) =
        MainFragmentBinding.inflate(inflater, container, false)

}