package com.example.shoppingapp.ui.details

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ViewItemDetailBinding

class DetailAdapter(private val list: List<String?>, private val context: Context) :
    RecyclerView.Adapter<DetailAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ViewItemDetailBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ViewItemDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]
        Glide.with(context).load(currentItem).placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground).into(holder.binding.imgProduct)
    }
}