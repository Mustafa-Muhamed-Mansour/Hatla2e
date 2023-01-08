package com.lafa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.lafa.R
import com.lafa.databinding.ItemProductBannerBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class ProductDetailAdapter(private val productBannerModel: List<String>, private val view: View) :
    SliderViewAdapter<ProductDetailAdapter.ProductDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?): ProductDetailViewHolder {
        return ProductDetailViewHolder(
            ItemProductBannerBinding.inflate(
                LayoutInflater.from(parent!!.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(viewHolder: ProductDetailViewHolder?, position: Int) {
        productBannerModel[position].apply {
            Glide
                .with(view.context)
                .load(this)
                .placeholder(R.drawable.ic_shopping)
                .error(R.drawable.ic_shopping)
                .fitCenter()
                .into(viewHolder!!.bind.imgItemProductBanner)
        }
    }

    override fun getCount(): Int {
        return productBannerModel.size
    }


    class ProductDetailViewHolder(binding: ItemProductBannerBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {
        val bind = binding
    }

}