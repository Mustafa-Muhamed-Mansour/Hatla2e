package com.lafa.question

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lafa.R
import com.lafa.adapter.QuestionAdapter
import com.lafa.databinding.FragmentQuestionBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class QuestionFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentQuestionBinding

    @Inject
    lateinit var viewModel: QuestionViewModel
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var questionAdapter: QuestionAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
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
        bottomSheetDialog = BottomSheetDialog(requireContext())
    }

    private fun initAdapter() {
        questionAdapter = QuestionAdapter()
    }

    private fun clickedViews() {
        binding
            .fabClose
            .setOnClickListener {
                bottomSheetDialog.dismiss()
                NavHostFragment.findNavController(requireParentFragment())
                    .navigate(R.id.action_questionFragment_to_settingFragment)
            }


        binding
            .btnAbout
            .setOnClickListener {

                binding.rVQuestion.visibility = View.GONE
                binding.txtSetting.visibility = View.VISIBLE
                binding.loadingSetting.visibility = View.VISIBLE
                viewModel
                    .getSetting()
                    .observe(viewLifecycleOwner)
                    {
                        binding.loadingSetting.visibility = View.GONE
                        binding.txtSetting.text = it.settingResponse.about
                    }
            }

        binding
            .btnTerm
            .setOnClickListener {

                binding.rVQuestion.visibility = View.GONE
                binding.txtSetting.visibility = View.VISIBLE
                binding.loadingSetting.visibility = View.VISIBLE
                viewModel
                    .getSetting()
                    .observe(viewLifecycleOwner)
                    {
                        binding.loadingSetting.visibility = View.GONE
                        binding.txtSetting.text = it.settingResponse.term
                    }
            }
    }

    private fun retrieveViewModel() {
        viewModel
            .getQuestions()
            .observe(viewLifecycleOwner)
            {
                questionAdapter.differ.submitList(it.dataQuestionResponse.questionModel)
                binding.rVQuestion.apply {
                    this.adapter = questionAdapter
                    this.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    this.addItemDecoration(
                        DividerItemDecoration(
                            requireContext(),
                            DividerItemDecoration.VERTICAL
                        )
                    )
                }
            }
    }
}