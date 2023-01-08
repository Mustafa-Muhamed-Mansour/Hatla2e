package com.lafa.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lafa.R
import com.lafa.adapter.BannerAdapter
import com.lafa.adapter.CategoryAdapter
import com.lafa.adapter.ProductAdapter
import com.lafa.check_internet.CheckInternetService
import com.lafa.check_internet.NetworkUtil
import com.lafa.common.Constant
import com.lafa.databinding.FragmentHomeBinding
import com.lafa.interfaces.FavoriteProduct
import com.lafa.interfaces.ProductDetail
import com.lafa.model.ProductModel
import com.smarteist.autoimageslider.IndicatorAnimations
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment(), ProductDetail, FavoriteProduct {

    private lateinit var binding: FragmentHomeBinding
    @Inject
    lateinit var viewModel: HomeViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var authToken: String
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var broadcastReceiver: BroadcastReceiver


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initBroadCast()
        initView()
        initAdapter()
        retrieveViewModel()

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
        authToken = sharedPreferences.getString("Auth Token", null).toString()
        binding.loadingBanner.visibility = View.VISIBLE
        binding.loadingBanner.playAnimation()
        binding.loadingProduct.visibility = View.VISIBLE
        binding.loadingProduct.playAnimation()
    }

    private fun initAdapter() {
        productAdapter = ProductAdapter(this@HomeFragment, this@HomeFragment)
        categoryAdapter = CategoryAdapter()
    }

    private fun retrieveViewModel() {
        val status = NetworkUtil.networkState(requireContext())
        if (status.not()) {
            viewModel.getBanner().removeObservers(viewLifecycleOwner)
            viewModel.getCategory().removeObservers(viewLifecycleOwner)
            viewModel.getProduct("").removeObservers(viewLifecycleOwner)
        } else {
            retrieveBannerFromViewModel()
            retrieveCategoryFromViewModel()
            retrieveProductFromViewModel()
        }
    }

    // TODO("retrieve banner of image")
    private fun retrieveBannerFromViewModel() {
        viewModel.getBanner().observe(viewLifecycleOwner) {
            binding.loadingBanner.pauseAnimation()
            binding.loadingBanner.visibility = View.GONE
            binding.imgBanner.sliderAdapter = BannerAdapter(it.bannerModel, requireView())
            binding.imgBanner.setIndicatorAnimation(IndicatorAnimations.WORM)
        }
    }

    // TODO("retrieve category")
    private fun retrieveCategoryFromViewModel() {
        viewModel
            .getCategory()
            .observe(viewLifecycleOwner)
            {
                categoryAdapter.differ.submitList(it.dataCategoryResponse.categoryModel)
                binding.rVCategory.apply {
                    this.adapter = categoryAdapter
                    this.layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    this.addItemDecoration(
                        DividerItemDecoration(
                            requireContext(),
                            DividerItemDecoration.HORIZONTAL
                        )
                    )
                }
            }
    }

    // TODO("retrieve product")
    private fun retrieveProductFromViewModel() {
        viewModel.getProduct(authToken).observe(viewLifecycleOwner)
        {
            binding.loadingProduct.pauseAnimation()
            binding.loadingProduct.visibility = View.GONE
            productAdapter.differ.submitList(it.dataProductResponse.productModel)
            binding.rVProduct.apply {
                this.adapter = productAdapter
                this.layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )

                this.addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
        }
    }


    override fun clickProductOfDetail(productModel: ProductModel) {
        val bundle = Bundle().apply {
            this.putSerializable("product", productModel)
        }
        findNavController().navigate(R.id.action_homeFragment_to_productDetailFragment, bundle)
    }

    override fun clickFavoriteOfProduct(productModel: ProductModel) {
        viewModel.insertProductToFavorite(productModel)
        Snackbar.make(requireView(), "Product saved successfully", Snackbar.LENGTH_SHORT).show()
    }

}