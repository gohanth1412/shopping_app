package com.example.shoppingapp.ui.home.exclusive

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ViewItemProductHomeBinding
import com.example.shoppingapp.model.ProductModel

class ExclusiveAdapter(
    private val context: Context,
    private val list: List<ProductModel>,
    private val onClick: (ProductModel) -> Unit
) :
    RecyclerView.Adapter<ExclusiveAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ViewItemProductHomeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ViewItemProductHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.apply {
            tvProductName.text = currentItem.productName
            tvPrice.text = "₹${currentItem.productPrice}"

            Glide.with(context).load(currentItem.thumbnail)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground).into(imgProduct)
        }

        holder.binding.layoutItem.setOnClickListener {
            onClick(currentItem)
        }
    }
}