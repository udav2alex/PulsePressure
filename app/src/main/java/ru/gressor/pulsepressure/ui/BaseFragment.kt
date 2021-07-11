package ru.gressor.pulsepressure.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding> : Fragment() {
    private var _binding: B? = null
    protected val binding: B get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        getBinding(inflater, container)
            .also { _binding = it }
            .root

    protected abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): B

    fun showError(e: Throwable) {
        activity?.let {
            Toast.makeText(it, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}