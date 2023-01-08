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
import com.lafa.databinding.ItemContactBinding
import com.lafa.interfaces.ContactDetail
import com.lafa.model.ContactModel

class ContactAdapter(private val contactDetail: ContactDetail) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<ContactModel>() {
        override fun areItemsTheSame(oldItem: ContactModel, newItem: ContactModel): Boolean {
            return oldItem.value == newItem.value && oldItem.image == newItem.image
        }

        override fun areContentsTheSame(oldItem: ContactModel, newItem: ContactModel): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        differ.currentList[position].apply {
            Glide
                .with(holder.itemView.context)
                .load(this.image)
                .placeholder(R.drawable.ic_home)
                .error(R.drawable.ic_home)
                .fitCenter()
                .into(holder.bind.imgItemContact)
            holder.bind.imgItemContact.setBackgroundColor(R.color.black)
            holder.bind.txtItemContact.text = this.value

            holder.itemView.setOnClickListener {
                contactDetail.clickContactOfDetail(this)
            }
        }

    }

    override fun getItemCount(): Int {
//        return contactModel.size
        return differ.currentList.size
    }

    class ContactViewHolder(binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {
        val bind = binding
    }
}