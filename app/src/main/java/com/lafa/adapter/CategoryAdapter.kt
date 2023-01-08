package com.lafa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lafa.R
import com.lafa.databinding.ItemCategoryBinding
import com.lafa.model.CategoryModel


class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>()
{

    private val differCallback = object : DiffUtil.ItemCallback<CategoryModel>() {
        override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem.image == newItem.image && oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder
    {
        return CategoryViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int)
    {
        differ.currentList[position].apply {
            Glide
                .with(holder.itemView.context)
                .load(this.image)
                .placeholder(R.drawable.ic_shopping)
                .error(R.drawable.ic_shopping)
                .fitCenter()
                .into(holder.bind.imgItemCategory)
            holder.bind.txtNameItemCategory.text = this.name
        }

//        Glide
//            .with(holder.itemView.context)
//            .load(model.image)
//            .placeholder(R.drawable.ic_shopping)
//            .error(R.drawable.ic_shopping)
//            .fitCenter()
//            .into(holder.bind.imgItemCategory)
//        holder.bind.txtNameItemCategory.text = model.name

    }

    override fun getItemCount(): Int
    {
//        return categoryModel.size
        return differ.currentList.size
    }

    class CategoryViewHolder(binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)
    {
        val bind = binding
    }
}