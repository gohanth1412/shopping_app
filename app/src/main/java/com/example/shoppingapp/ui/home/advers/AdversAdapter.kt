package com.example.shoppingapp.ui.home.advers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.databinding.ViewItemAdViewPagerBinding
import com.example.shoppingapp.model.AdversModel

class AdversAdapter(private val list: List<AdversModel>) :
    RecyclerView.Adapter<AdversAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ViewItemAdViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ViewItemAdViewPagerBinding.inflate(
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
            currentItem.imgProduct?.let { imgProduct.setImageResource(it) }
            tvProductName.text = currentItem.productName
            tvDiscount.text = currentItem.discount
        }
    }
}