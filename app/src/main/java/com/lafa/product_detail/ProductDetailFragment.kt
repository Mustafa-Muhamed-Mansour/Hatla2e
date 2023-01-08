package com.lafa.product_detail

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.lafa.R
import com.lafa.adapter.ProductDetailAdapter
import com.lafa.databinding.FragmentProductDetailBinding
import com.lafa.home.HomeViewModel
//import com.lafa.home.HomeViewModelFactory
import com.lafa.model.ProductModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailBinding
    @Inject
    lateinit var viewModel: HomeViewModel
    private lateinit var productDetail: ProductModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initViews()
        clickedViews()
        clickedOnProduct()

    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        productDetail = requireArguments().getSerializable("product") as ProductModel

        Glide
            .with(requireContext())
            .load(productDetail.image)
            .placeholder(R.drawable.ic_shopping)
            .error(R.drawable.ic_shopping)
            .into(binding.imgProductDetail)
        binding.txtNameProductDetail.text = productDetail.name
        binding.txtPriceProductDetail.text = "New Price: ${productDetail.price}  EGP"
        binding.txtOldPriceProductDetail.text = "Old Price: ${productDetail.oldPrice}  EGP"
        binding.txtOldPriceProductDetail.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        binding.txtDescriptionProductDetail.text = productDetail.description
        val productDetailAdapter =
            ProductDetailAdapter(productDetail.images.toList(), requireView())
        binding.imgBannerProductDetail.sliderAdapter = productDetailAdapter

        binding.numberPicker.minValue = 0
        binding.numberPicker.maxValue = 10
    }


    @SuppressLint("SetTextI18n")
    private fun clickedViews() {
        binding.txtReadMore.setOnClickListener {
            if (binding.txtReadMore.text.toString() == "Read More") {
                binding.txtDescriptionProductDetail.maxLines = Integer.MAX_VALUE
                binding.txtReadMore.text = "Read Less";
            } else {
                binding.txtDescriptionProductDetail.maxLines = 5;
                binding.txtReadMore.text = "Read More";
            }
        }

        binding.fabCartProduct.setOnClickListener {
            retrieveViewModel()
        }
    }

    private fun retrieveViewModel() {
        viewModel.cartOfProduct(binding.numberPicker.progress , productDetail)
        Toast.makeText(requireContext(), "Done successfully", Toast.LENGTH_SHORT).show()
    }

    private fun clickedOnProduct() {
        binding.imgFavorite.setOnClickListener {
            binding.imgFavorite.setImageResource(R.drawable.ic_favorite_red)
            viewModel.insertProductToFavorite(productDetail)
            Snackbar.make(it, "Product saved successfully", Snackbar.LENGTH_SHORT).show()
        }
    }

}