package com.ranjan.openweather.view.onboarding.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ranjan.openweather.R
import com.ranjan.openweather.common.EncryptedSharedPreference
import com.ranjan.openweather.databinding.FragmentLoginBinding
import com.ranjan.openweather.view.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    @Inject
    lateinit var preference: EncryptedSharedPreference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this@LoginFragment

        lifecycleScope.launch {
            viewModel.loginResponse.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED).collect {
                if (it) {
                    Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), DashboardActivity::class.java)
                    preference.putBoolean("isLogin", true)
                    startActivity(intent)
                    activity?.finish()
                }
            }
        }

        binding.registerUser.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}