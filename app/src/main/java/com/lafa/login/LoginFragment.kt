package com.lafa.login

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.lafa.R
import com.lafa.check_internet.CheckInternetService
import com.lafa.check_internet.NetworkUtil
import com.lafa.common.Constant
import com.lafa.common.Validation
import com.lafa.databinding.FragmentLoginBinding
import com.lafa.model.LoginModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {


    private lateinit var binding: FragmentLoginBinding
    @Inject
    lateinit var viewModel: LoginViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    @Inject
    lateinit var validation: Validation
    private lateinit var broadcastReceiver: BroadcastReceiver


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initBroadCast()
        initView()
        clickedView()

    }

    private fun initBroadCast() {
        broadcastReceiver = CheckInternetService()
        checkInternet()
    }

    private fun checkInternet() {
        requireActivity().registerReceiver(
            broadcastReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }


    private fun initView() {
        sharedPreferences =
            requireActivity().getSharedPreferences(Constant.USER_PREF, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }


    private fun clickedView() {
        binding
            .btnLogin
            .setOnClickListener {
                val email = binding.editEmailLogin.text.toString()
                val password = binding.editPasswordLogin.text.toString()
                val status = NetworkUtil.networkState(requireContext())

                if (validation.checkEmail(requireContext(), email).none()) {
                    binding.editEmailLogin.requestFocus()
                    return@setOnClickListener
                }
                if (validation.checkPassword(requireContext(), password).none()) {
                    binding.editPasswordLogin.requestFocus()
                    return@setOnClickListener
                }
                if (status.not()) {
                    Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show()
                    viewModel.loginUser(LoginModel("", "")).removeObservers(viewLifecycleOwner)
                    return@setOnClickListener
                } else {
                    val model = LoginModel(email, password)
                    retrieveViewModel(model)
                }
            }

        binding
            .btnRegister
            .setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registerFragment)
            }
    }

    private fun retrieveViewModel(model: LoginModel) {
            viewModel
                .loginUser(model)
                .observe(viewLifecycleOwner)
                {
                    if (it != null) {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        if (!it.status) {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        else {
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                    } else {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unregisterReceiver(broadcastReceiver)
    }
}