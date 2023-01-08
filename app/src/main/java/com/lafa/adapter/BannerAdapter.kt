package com.lafa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.lafa.R
import com.lafa.databinding.ItemBannerBinding
import com.lafa.model.BannerModel
import com.smarteist.autoimageslider.SliderViewAdapter

class BannerAdapter(private val bannerModel: ArrayList<BannerModel>, private val view: View) :
    SliderViewAdapter<BannerAdapter.BannerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?): BannerViewHolder {
        return BannerViewHolder(
            ItemBannerBinding.inflate(
                LayoutInflater.from(parent!!.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: BannerViewHolder?, position: Int) {
        bannerModel[position].apply {
            Glide
                .with(view.context)
                .load(this.image)
                .placeholder(R.drawable.ic_shopping)
                .error(R.drawable.ic_shopping)
                .fitCenter()
                .into(viewHolder!!.bind.imgItemBanner)
        }
    }

    override fun getCount(): Int {
        return bannerModel.size
    }

    class BannerViewHolder(binding: ItemBannerBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {
        val bind = binding
    }
}