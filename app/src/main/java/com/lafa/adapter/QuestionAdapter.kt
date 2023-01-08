package com.lafa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lafa.databinding.ItemFeqBinding
import com.lafa.model.QuestionModel

class QuestionAdapter: RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>()
{
    private val differCallback = object : DiffUtil.ItemCallback<QuestionModel>() {
        override fun areItemsTheSame(oldItem: QuestionModel, newItem: QuestionModel): Boolean {
            return oldItem.question == newItem.question && oldItem.answer == newItem.answer
        }

        override fun areContentsTheSame(oldItem: QuestionModel, newItem: QuestionModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder
    {
        return QuestionViewHolder(ItemFeqBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int)
    {
        differ.currentList[position].apply {
            holder.bind.txtQuestionItemFeq.text = this.question
            holder.bind.txtAnswerItemFeq.text = this.answer
        }
    }

    override fun getItemCount(): Int
    {
//        return questionModel.size
        return differ.currentList.size
    }

    class QuestionViewHolder(binding: ItemFeqBinding) : RecyclerView.ViewHolder(binding.root)
    {
        val bind = binding
    }
}