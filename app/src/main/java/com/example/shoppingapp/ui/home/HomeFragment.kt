package com.example.shoppingapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.MainActivity
import com.example.shoppingapp.MainViewModel
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentHomeBinding
import com.example.shoppingapp.model.ProductModel
import com.example.shoppingapp.ui.home.advers.AdversAdapter
import com.example.shoppingapp.ui.home.exclusive.ExclusiveAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var job: Job
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        bindView()
        observeData()
    }

    private fun observeData() {
        mainViewModel.offerProduct.observe(viewLifecycleOwner) {
            binding.rcvExclusiveOffer.apply {
                adapter = ExclusiveAdapter(requireContext(), it, onItemCLick)
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }

        mainViewModel.sellingProduct.observe(viewLifecycleOwner) {
            binding.rcvBestSelling.apply {
                adapter = ExclusiveAdapter(requireContext(), it, onItemCLick)
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }

        mainViewModel.woolenProduct.observe(viewLifecycleOwner) {
            binding.rcvWoolenProducts.apply {
                adapter = ExclusiveAdapter(requireContext(), it, onItemCLick)
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    private fun bindView() {
        binding.viewPagerAd.adapter = AdversAdapter(mainViewModel.dataAdvers)
        binding.indicator.setViewPager(binding.viewPagerAd)
        autoChangeAd()
    }

    private fun autoChangeAd() {
        job = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                val currentItem = binding.viewPagerAd.currentItem
                delay(2000L)
                if (currentItem == 2) {
                    binding.viewPagerAd.currentItem = 0
                } else {
                    binding.viewPagerAd.currentItem++
                }
            }
        }
    }

    private fun initData() {
        (requireActivity() as MainActivity).showBotNav()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
    }

    private val onItemCLick: (product: ProductModel) -> Unit = {
        mainViewModel.itemProductChoose = it
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
    }
}