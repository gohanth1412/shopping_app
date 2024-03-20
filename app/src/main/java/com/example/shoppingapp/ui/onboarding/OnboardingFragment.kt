package com.example.shoppingapp.ui.onboarding

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shoppingapp.MainActivity
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentOnboardingBinding

class OnboardingFragment: Fragment() {
    private lateinit var binding: FragmentOnboardingBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).hideBotNav()
        bindView()
        onClickListener()
    }

    private fun bindView() {
        sharedPreferences = activity?.getSharedPreferences("Login", Context.MODE_PRIVATE)!!
        val isFistLogin = sharedPreferences.getBoolean("firstLogin", false)

        if (isFistLogin) {
            findNavController().navigate(R.id.action_onboardingFragment_to_homeFragment)
        }
    }

    private fun onClickListener() {
        binding.tvGetStarted.setOnClickListener {
            findNavController().navigate(R.id.action_onboardingFragment_to_loginFragment)
        }
    }
}