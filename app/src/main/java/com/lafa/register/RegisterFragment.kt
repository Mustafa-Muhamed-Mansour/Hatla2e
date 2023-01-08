package com.lafa.register

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.lafa.R
import com.lafa.databinding.FragmentRegisterBinding
import com.lafa.model.RegisterModel
import com.lafa.common.Validation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RegisterFragment : Fragment() {


    private lateinit var binding: FragmentRegisterBinding

    @Inject
    lateinit var viewModel: RegisterViewModel

    @Inject
    lateinit var validation: Validation


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        clickedView()

    }


    private fun clickedView() {
        binding
            .btnRegister
            .setOnClickListener {

                val email = binding.editEmail.text.toString()
                val name = binding.editName.text.toString()
                val phone = binding.editPhone.text.toString()
                val password = binding.editPassword.text.toString()

                if (validation.checkEmail(requireContext(), email).none()) {
                    binding.editEmail.requestFocus()
                    return@setOnClickListener
                }

                if (validation.checkName(requireContext(), name).none()) {
                    binding.editName.requestFocus()
                    return@setOnClickListener
                }

                if (validation.checkPhone(requireContext(), phone).none()) {
                    binding.editPhone.requestFocus()
                    return@setOnClickListener
                }

                if (validation.checkPassword(requireContext(), password).none()) {
                    binding.editPassword.requestFocus()
                    return@setOnClickListener
                } else {
                    val model = RegisterModel(name, phone, email, password)
                    retrieveViewModel(model)
                }
            }
    }

    private fun retrieveViewModel(model: RegisterModel) {
        binding.loadingCreateNewAccount.visibility = View.VISIBLE
        binding.btnRegister.visibility = View.GONE
        viewModel
            .createAccountUser(model)
            .observe(viewLifecycleOwner) {
                if (it != null) {
                    binding.loadingCreateNewAccount.visibility = View.GONE
                    binding.btnRegister.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    if (!it.status) {
                        binding.loadingCreateNewAccount.visibility = View.GONE
                        binding.btnRegister.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    } else {
                        binding.loadingCreateNewAccount.visibility = View.GONE
                        binding.btnRegister.visibility = View.VISIBLE
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                } else {
                    binding.loadingCreateNewAccount.visibility = View.GONE
                    binding.btnRegister.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "Field", Toast.LENGTH_SHORT).show()
                }
            }
    }
}