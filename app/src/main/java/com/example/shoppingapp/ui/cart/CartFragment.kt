package com.example.shoppingapp.ui.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.MainActivity
import com.example.shoppingapp.MainViewModel
import com.example.shoppingapp.databinding.FragmentCartBinding
import com.example.shoppingapp.model.CartModel
import com.example.shoppingapp.model.ProductModel

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        bindView()
        onClickListener()
        observeData()
    }

    @SuppressLint("SetTextI18n")
    private fun bindView() {
        mainViewModel.allPrice.observe(viewLifecycleOwner) {
            binding.tvTotalPrice.text = "₹$it"
        }
    }

    private fun observeData() {
        mainViewModel.dataCart.observe(viewLifecycleOwner) {
            binding.rcvCart.apply {
                adapter = CartAdapter(requireContext(), it, onItemDelete, onItemPlus, onItemMinus)
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    private fun onClickListener() {

    }

    private fun initData() {
        (requireActivity() as MainActivity).showBotNav()
    }

    private val onItemDelete:(cart: CartModel) -> Unit = {
        it.idProduct?.let { it1 -> mainViewModel.deleteCart(it1) }
    }

    private val onItemPlus:(cart: CartModel) -> Unit = {
        var count = it.countProduct!!
        count++
        val totalPrice = String.format("%.2f", count * it.singlePrice!!.toFloat())
        mainViewModel.upDateCart(it.idCart!!, count, totalPrice)
    }

    private val onItemMinus:(cart: CartModel) -> Unit = {
        var count = it.countProduct!!
        if (count > 1) {
            count--
            val totalPrice = String.format("%.2f", count * it.singlePrice!!.toFloat())
            mainViewModel.upDateCart(it.idCart!!, count, totalPrice)
        }
    }
}