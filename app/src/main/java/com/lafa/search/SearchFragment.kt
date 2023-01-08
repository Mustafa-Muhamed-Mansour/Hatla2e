package com.lafa.search

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lafa.adapter.SearchAdapter
import com.lafa.common.Constant
import com.lafa.databinding.FragmentShoppingBinding
import com.lafa.response.SearchProductResponse
import com.lafa.common.Validation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentShoppingBinding
    @Inject
    lateinit var viewModel: SearchViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var authToken: String
    @Inject
    lateinit var validation: Validation
    private lateinit var searchAdapter: SearchAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initViews()
        initAdapter()
        clickedViews()

    }

    private fun initViews() {
        sharedPreferences = requireActivity().getSharedPreferences(Constant.USER_PREF, Context.MODE_PRIVATE)
        authToken = sharedPreferences.getString("Auth Token", null).toString()
        binding.loadingSearchProduct.visibility = View.VISIBLE
        binding.loadingSearchProduct.playAnimation()
    }

    private fun initAdapter() {
        searchAdapter = SearchAdapter()
    }

    private fun clickedViews() {
        binding.imgSearchProduct.setOnClickListener {
            val search = binding.editSearch.text.toString()
            if (validation.checkSearchProduct(requireContext(), search).none()) {
                binding.editSearch.requestFocus()
                binding.loadingSearchProduct.visibility = View.VISIBLE
                binding.loadingSearchProduct.playAnimation()
                searchAdapter.differ.submitList(null)
                binding.rVSearchProduct.apply {
                    this.adapter = searchAdapter
                    this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    this.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                }
                return@setOnClickListener
            } else {
                val res = SearchProductResponse(search)
                retrieveViewModel(res)
            }
        }
    }

    private fun retrieveViewModel(res: SearchProductResponse) {
        viewModel.searchProducts(authToken, res).observe(viewLifecycleOwner) {
            binding.loadingSearchProduct.pauseAnimation()
            binding.loadingSearchProduct.visibility = View.GONE
            searchAdapter.differ.submitList(it.dataSearchResponse.searchModel)
            binding.rVSearchProduct.apply {
                this.adapter = searchAdapter
                this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                this.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            }
        }
    }
}