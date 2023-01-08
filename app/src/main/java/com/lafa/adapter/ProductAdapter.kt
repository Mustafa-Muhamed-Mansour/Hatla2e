package com.lafa.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lafa.R
import com.lafa.databinding.ItemProductBinding
import com.lafa.interfaces.FavoriteProduct
import com.lafa.interfaces.ProductDetail
import com.lafa.model.ProductModel


class ProductAdapter(private val productDetail: ProductDetail, private val favoriteProduct: FavoriteProduct) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<ProductModel>() {
        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.description == newItem.description && oldItem.discount == newItem.discount && oldItem.image == newItem.image && oldItem.name == newItem.name && oldItem.oldPrice.toString() == newItem.oldPrice.toString() && oldItem.price.toString() == newItem.price.toString()
        }

        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        differ.currentList[position].apply {
            Glide
                .with(holder.itemView.context)
                .load(this.image)
                .placeholder(R.drawable.ic_shopping)
                .error(R.drawable.ic_shopping)
                .into(holder.bind.imgItemProduct)
            holder.bind.txtNameItemProduct.text = this.name
            holder.bind.txtPriceItemProduct.text = "${this.price}  EGP"

            holder.bind.imgFavoriteItemProduct.setOnClickListener {
                holder.bind.imgFavoriteItemProduct.setImageResource(R.drawable.ic_favorite_red)
                favoriteProduct.clickFavoriteOfProduct(this)
            }

            holder.itemView.setOnClickListener {
                productDetail.clickProductOfDetail(this)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class ProductViewHolder(binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        val bind = binding
    }
}