package com.example.shoppingapp.ui.explore

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
import com.example.shoppingapp.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {
    private lateinit var binding: FragmentExploreBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        bindView()
    }

    private fun bindView() {
        binding.rcvCategory.apply {
            adapter = CategoryAdapter(mainViewModel.dataCategory, onItemClick)
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun initData() {
        (requireActivity() as MainActivity).showBotNav()
    }

    private val onItemClick: (category: String) -> Unit = {
        mainViewModel.categoryName = it
        mainViewModel.listFilterCategory = mainViewModel.filterProductByCategory(it)
        findNavController().navigate(R.id.action_exploreFragment_to_categoryDetailFragment)
    }
}