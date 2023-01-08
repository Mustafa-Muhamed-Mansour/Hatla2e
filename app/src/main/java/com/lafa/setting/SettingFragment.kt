package com.lafa.setting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.lafa.R
import com.lafa.adapter.ContactAdapter
import com.lafa.databinding.FragmentSettingBinding
import com.lafa.interfaces.ContactDetail
import com.lafa.model.ContactModel
import com.lafa.question.QuestionFragment
import com.lafa.repositories.SettingRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : Fragment(), ContactDetail {

    private lateinit var binding: FragmentSettingBinding

    @Inject
    lateinit var viewModel: SettingViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var authToken: String
    private lateinit var contactAdapter: ContactAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        initView()
        initAdapter()
        clickedViews()
        retrieveViewModel()

    }

    private fun initView() {
        sharedPreferences = requireActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        authToken = sharedPreferences.getString("Auth Token", null).toString()
    }

    private fun initAdapter() {
        contactAdapter = ContactAdapter(this@SettingFragment)
    }

    private fun clickedViews() {
        binding
            .cardProfile
            .setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_settingFragment_to_profileFragment)
            }

        binding
            .cardQuestion
            .setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_settingFragment_to_questionFragment)
            }

        binding
            .cardComplaint
            .setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_settingFragment_to_complaintFragment)
            }

        binding
            .cardLogout
            .setOnClickListener {
                viewModel
                    .logoutUser(authToken)
                    .observe(viewLifecycleOwner)
                    {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(requireView())
                            .navigate(R.id.action_settingFragment_to_loginFragment)
                    }

            }
    }

    private fun retrieveViewModel() {
        viewModel
            .profileUser(authToken)
            .observe(viewLifecycleOwner)
            {

                if (it.dataLoginResponse.token == authToken) {
                    Glide
                        .with(requireView())
                        .load(it.dataLoginResponse.image)
                        .placeholder(R.drawable.ic_profile)
                        .error(R.drawable.ic_profile)
                        .fitCenter()
                        .into(binding.imgUserProfile)
                    binding.txtUserName.text = it.dataLoginResponse.name
                    binding.txtUserPhone.text = it.dataLoginResponse.phone
                } else {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }

        viewModel
            .getContact()
            .observe(viewLifecycleOwner)
            {
                contactAdapter.differ.submitList(it.dataContactResponse.contactModel)
                binding.rVContact.apply {
                    this.adapter = contactAdapter
                    this.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    this.addItemDecoration(
                        DividerItemDecoration(
                            requireContext(),
                            DividerItemDecoration.HORIZONTAL
                        )
                    )
                }
            }

    }

    override fun clickContactOfDetail(contactModel: ContactModel) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            this.data = Uri.parse(contactModel.value)
        }
        startActivity(intent)
    }
}