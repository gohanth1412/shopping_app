package com.example.shoppingapp.ui.cart

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ViewItemCartBinding
import com.example.shoppingapp.model.CartModel
import com.example.shoppingapp.model.ProductModel

class CartAdapter(
    private val context: Context,
    private val list: List<CartModel>,
    private val onDelete: (CartModel) -> Unit,
    private val onPlus: (CartModel) -> Unit,
    private val onMinus: (CartModel) -> Unit,
) :
    RecyclerView.Adapter<CartAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ViewItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ViewItemCartBinding.inflate(
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
        holder.binding.tvPrice.text = "₹${currentItem.totalPrice}"
        holder.binding.tvProductName.text = currentItem.productName
        holder.binding.tvCount.text = currentItem.countProduct.toString()

        Glide.with(context).load(currentItem.thumbnail)
            .placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground)
            .into(holder.binding.imgProduct)

        holder.binding.imgDelete.setOnClickListener {
            onDelete(currentItem)
        }

        holder.binding.imgPlus.setOnClickListener {
            onPlus(currentItem)
        }

        holder.binding.imgMinus.setOnClickListener {
            onMinus(currentItem)
        }
    }

}