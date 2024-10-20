package com.example.dwhats.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dwhats.data.NumberW
import com.example.dwhats.databinding.RecentListItemBinding

class NumbersAdapter : RecyclerView.Adapter<NumbersAdapter.ViewHolder>() {

    var onItemClick: ((NumberW) -> Unit)? = null

    private val diffUtil = object : DiffUtil.ItemCallback<NumberW>() {
        override fun areItemsTheSame(oldItem: NumberW, newItem: NumberW): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NumberW, newItem: NumberW): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtil)

    class ViewHolder(val binding: RecentListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecentListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val number = differ.currentList[position]
        holder.binding.txtNumber.text = number.num

        onItemClick?.let {
            holder.itemView.setOnClickListener {
                it(number)
            }
        }

    }
}