package com.example.shoppingapp.ui.explore.categoryDetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppingapp.MainActivity
import com.example.shoppingapp.MainViewModel
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentCategoryDetailBinding
import com.example.shoppingapp.model.ProductModel
import com.example.shoppingapp.ui.favourite.FavouriteAdapter

class CategoryDetailFragment : Fragment() {
    private lateinit var binding: FragmentCategoryDetailBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        bindView()
        onClickListener()
    }

    private fun onClickListener() {
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindView() {
        binding.tvCategory.text = "${mainViewModel.categoryName} Product"

        binding.rcv.apply {
            adapter =
                FavouriteAdapter(requireContext(), mainViewModel.listFilterCategory, onItemClick)
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun initData() {
        (requireActivity() as MainActivity).hideBotNav()
    }

    private val onItemClick: (product: ProductModel) -> Unit = {
        mainViewModel.itemProductChoose = it
        findNavController().navigate(R.id.action_categoryDetailFragment_to_detailFragment)
    }
}