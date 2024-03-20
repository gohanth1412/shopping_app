package com.example.shoppingapp.ui.account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.databinding.ViewItemMenuBinding
import com.example.shoppingapp.model.MenuModel

class MenuAdapter(private val list: List<MenuModel>) :
    RecyclerView.Adapter<MenuAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ViewItemMenuBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ViewItemMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.imgIcon.setImageResource(currentItem.imgIcon)
        holder.binding.tvMenuName.text = currentItem.menuName
    }
}