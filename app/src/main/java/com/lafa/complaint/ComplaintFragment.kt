package com.lafa.complaint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lafa.common.Validation
import com.lafa.databinding.FragmentComplaintBinding
import com.lafa.response.DataComplaint
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ComplaintFragment : Fragment() {

    private lateinit var binding: FragmentComplaintBinding
    @Inject
    lateinit var viewModel: ComplaintViewModel
    @Inject
    lateinit var validation: Validation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComplaintBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickedViews()

    }



    private fun clickedViews() {
        binding.btnComplaint.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val name = binding.editName.text.toString()
            val phone = binding.editPhone.text.toString()
            val message = binding.editMessage.text.toString()

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
            if (validation.checkMessage(requireContext(), message).none()) {
                binding.editMessage.requestFocus()
                return@setOnClickListener
            } else {
                val dataComplaint = DataComplaint(name, phone, email, message)
                retrieveViewModel(dataComplaint)
            }
        }
    }

    private fun retrieveViewModel(dataComplaint: DataComplaint) {
        viewModel.addNewComplaint(dataComplaint).observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

}