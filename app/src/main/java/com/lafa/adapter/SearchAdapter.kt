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
import com.lafa.databinding.ItemSearchBinding
import com.lafa.interfaces.ProductDetail
import com.lafa.model.ProductModel
import com.lafa.model.SearchModel

class SearchAdapter :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<SearchModel>() {
        override fun areItemsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean {
            return oldItem.image == newItem.image && oldItem.name == newItem.name && oldItem.price.toString() == newItem.price.toString()
        }

        override fun areContentsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        differ.currentList[position].apply {
            Glide
                .with(holder.itemView.context)
                .load(this.image)
                .placeholder(R.drawable.ic_search)
                .error(R.drawable.ic_search)
                .fitCenter()
                .into(holder.bind.imgItemSearchProduct)
            holder.bind.txtNameItemSearchProduct.text = this.name
            holder.bind.txtPriceItemSearchProduct.text = "${this.price}  EGP"
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class SearchViewHolder(binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        val bind = binding
    }
}