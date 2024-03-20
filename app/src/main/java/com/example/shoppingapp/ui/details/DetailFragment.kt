package com.example.shoppingapp.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shoppingapp.MainActivity
import com.example.shoppingapp.MainViewModel
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentDetailBinding
import com.example.shoppingapp.model.CartModel
import com.example.shoppingapp.model.ProductModel

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var itemProductChoose: ProductModel
    private var showDetail = false
    private var favourite = false
    private var countProduct = 1
    private var priceProduct = 1.0f
    private var totalPrice = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        bindView()
        onClickListener()
    }

    @SuppressLint("SetTextI18n")
    private fun onClickListener() {
        binding.imgArrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.imgShow.setOnClickListener {
            if (showDetail) {
                binding.tvDetails.visibility = View.GONE
                binding.imgShow.setImageResource(R.drawable.baseline_navigate_next_24)
                showDetail = false
            } else {
                binding.tvDetails.visibility = View.VISIBLE
                binding.imgShow.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                showDetail = true
            }
        }

        binding.imgHeart.setOnClickListener {
            if (favourite) {
                binding.imgHeart.setImageResource(R.drawable.heart_icon)
                favourite = false
            } else {
                binding.imgHeart.setImageResource(R.drawable.heart_fill_icon)
                favourite = true
            }
        }

        binding.imgPlus.setOnClickListener {
            countProduct++
            binding.tvCount.text = countProduct.toString()
            binding.tvPrice.text = "₹${String.format("%.2f", countProduct * priceProduct)}"
        }

        binding.imgMinus.setOnClickListener {
            if (countProduct > 1) {
                countProduct--
                binding.tvCount.text = countProduct.toString()
                binding.tvPrice.text = "₹${String.format("%.2f", countProduct * priceProduct)}"
            }
        }

        binding.tvAddToCart.setOnClickListener {
            totalPrice = String.format("%.2f", countProduct * priceProduct)
            mainViewModel.addCart(itemProductChoose, countProduct, totalPrice)
            Toast.makeText(requireContext(), "Add To Cart Success", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initData() {
        (requireActivity() as MainActivity).hideBotNav()
        itemProductChoose = mainViewModel.itemProductChoose
        favourite = mainViewModel.checkProductFavourite()
        priceProduct = itemProductChoose.productPrice!!.toFloat()
    }

    @SuppressLint("SetTextI18n")
    private fun bindView() {
        if (favourite) {
            binding.imgHeart.setImageResource(R.drawable.heart_fill_icon)
        } else {
            binding.imgHeart.setImageResource(R.drawable.heart_icon)
        }

        binding.viewPagerDetails.apply {
            adapter = DetailAdapter(
                listOf(
                    itemProductChoose.thumbnail,
                    itemProductChoose.thumbnail,
                    itemProductChoose.thumbnail
                ),
                requireContext()
            )
        }
        binding.indicator.setViewPager(binding.viewPagerDetails)
        binding.tvProductName.text = itemProductChoose.productName
        binding.tvPrice.text = "₹${itemProductChoose.productPrice}"
        binding.tvDetails.text = itemProductChoose.details
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (favourite) {
            mainViewModel.addFavouriteProduct(itemProductChoose)
        } else {
            mainViewModel.deleteFavouriteProduct(itemProductChoose)
        }
    }
}