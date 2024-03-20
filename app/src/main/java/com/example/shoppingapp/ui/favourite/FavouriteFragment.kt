package com.example.shoppingapp.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.MainActivity
import com.example.shoppingapp.MainViewModel
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentFavouriteBinding
import com.example.shoppingapp.model.ProductModel

class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        observeData()
    }

    private fun observeData() {
        mainViewModel.favouriteProducts.observe(viewLifecycleOwner) {
            binding.rcvFavourite.apply {
                adapter = FavouriteAdapter(requireContext(), it, onItemClick)
                layoutManager = GridLayoutManager(requireContext(), 2)
            }
        }
    }

    private fun initData() {
        (requireActivity() as MainActivity).showBotNav()
    }

    private val onItemClick:(product: ProductModel) -> Unit = {
        mainViewModel.itemProductChoose = it
        findNavController().navigate(R.id.action_favouriteFragment_to_detailFragment)
    }
}