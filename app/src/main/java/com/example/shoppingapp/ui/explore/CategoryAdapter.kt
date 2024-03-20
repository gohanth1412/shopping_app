package com.example.shoppingapp.ui.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.databinding.ViewItemFavouriteBinding
import com.example.shoppingapp.model.CategoryModel

class CategoryAdapter(
    private val list: List<CategoryModel>,
    private val onClick: (String) -> Unit
) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ViewItemFavouriteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ViewItemFavouriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.apply {
            currentItem.imgCategory?.let { imgProduct.setImageResource(it) }
            tvProductName.text = currentItem.categoryName
            layoutItem.setOnClickListener {
                currentItem.categoryName?.let { it1 -> onClick(it1) }
            }
        }
    }
}