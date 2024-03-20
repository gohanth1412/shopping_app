package com.example.shoppingapp.ui.favourite

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ViewItemFavouriteBinding
import com.example.shoppingapp.model.ProductModel

class FavouriteAdapter(
    private val context: Context,
    private val list: List<ProductModel>,
    private val onItemClick: (ProductModel) -> Unit
) :
    RecyclerView.Adapter<FavouriteAdapter.MyViewHolder>() {
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
        holder.binding.tvProductName.text = currentItem.productName

        Glide.with(context).load(currentItem.thumbnail)
            .placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground)
            .into(holder.binding.imgProduct)

        holder.binding.layoutItem.setOnClickListener {
            onItemClick(currentItem)
        }
    }
}