package com.lafa.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.lafa.R
import com.lafa.common.Constant
import com.lafa.common.Validation
import com.lafa.databinding.FragmentProfileBinding
import com.lafa.model.RegisterModel
import com.lafa.response.ChangePassword
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var viewModel: ProfileViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var authToken: String

    @Inject
    lateinit var validation: Validation


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        initView()
        clickedView()
        retrieveViewModel()

    }


    private fun initView() {
        sharedPreferences =
            requireActivity().getSharedPreferences(Constant.USER_PREF, Context.MODE_PRIVATE)
        authToken = sharedPreferences.getString("Auth Token", null).toString()
    }

    private fun clickedView() {
        binding
            .btnEdit
            .setOnClickListener {
                binding.editName.isEnabled = true
                binding.editEmail.isEnabled = true
                binding.editPhone.isEnabled = true
                binding.btnEdit.visibility = View.GONE
                binding.btnSave.visibility = View.VISIBLE
            }

        binding
            .btnEditPassword
            .setOnClickListener {
                binding.btnEditPassword.visibility = View.GONE
                binding.relativePassword.visibility = View.VISIBLE
                binding.btnSavePassword.visibility = View.VISIBLE
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
                        .into(binding.profileImg)
                    binding.editName.setText(it.dataLoginResponse.name)
                    binding.editEmail.setText(it.dataLoginResponse.email)
                    binding.editPhone.setText(it.dataLoginResponse.phone)
                } else {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }


        binding
            .btnSave
            .setOnClickListener {
                val name = binding.editName.text.toString()
                val phone = binding.editPhone.text.toString()
                val email = binding.editEmail.text.toString()

                val model = RegisterModel(name, phone, email, "123456789")

                viewModel
                    .updateProfile(authToken, model)
                    .observe(viewLifecycleOwner) {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
            }

        binding
            .btnSavePassword
            .setOnClickListener {

                val currentPassword = binding.editCurrentPassword.text.toString()
                val newPassword = binding.editNewPassword.text.toString()

                if (currentPassword.none()) {
                    binding.editCurrentPassword.requestFocus()
                    return@setOnClickListener
                }
                if (newPassword.none()) {
                    binding.editNewPassword.requestFocus()
                    return@setOnClickListener
                } else {
                    val change = ChangePassword(currentPassword, newPassword)

                    viewModel
                        .changePassword(authToken, change)
                        .observe(viewLifecycleOwner) {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                }
            }
    }

}