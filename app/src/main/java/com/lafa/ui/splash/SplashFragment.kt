package com.lafa.ui.splash

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.lafa.R
import com.lafa.databinding.FragmentSplashBinding
import com.lafa.check_internet.CheckInternetService
//import com.lafa.check_internet.CheckInternetService
import com.lafa.check_internet.NetworkUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {


    private lateinit var binding: FragmentSplashBinding

    @Inject
    lateinit var viewModel: SplashViewModel
    private lateinit var broadcastReceiver: BroadcastReceiver


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initBroadCast()
        getAnimation()


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

    private fun getAnimation() {
        val status = NetworkUtil.networkState(requireContext())
        if (status.not()) {
            viewModel.getAnimation().removeObservers(viewLifecycleOwner)
        } else {
            viewModel.getAnimation().observe(viewLifecycleOwner) {
                if (it == true) {
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_splashFragment_to_loginFragment)
                } else {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unregisterReceiver(broadcastReceiver)
    }
}