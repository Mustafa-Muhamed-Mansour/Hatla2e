package com.lafa.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lafa.R
import com.lafa.databinding.ItemProductBinding
import com.lafa.interfaces.ProductDetail
import com.lafa.model.ProductModel

class FavoriteAdapter(private val productDetail: ProductDetail) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<ProductModel>() {
        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.image == newItem.image && oldItem.name == newItem.name && oldItem.price.toString() == newItem.price.toString()
        }

        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        differ.currentList[position].apply {
            Glide
                .with(holder.itemView.context)
                .load(this.image)
                .placeholder(R.drawable.ic_shopping)
                .error(R.drawable.ic_shopping)
                .into(holder.bind.imgItemProduct)
            holder.bind.txtNameItemProduct.text = this.name
            holder.bind.txtPriceItemProduct.text = "${this.price}  EGP"

            holder.bind.imgFavoriteItemProduct.visibility = View.GONE

            holder.itemView.setOnClickListener {
                productDetail.clickProductOfDetail(this)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class FavoriteViewHolder(binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        val bind = binding
    }
}