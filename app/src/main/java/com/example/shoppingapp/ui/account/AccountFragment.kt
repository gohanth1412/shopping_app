package com.example.shoppingapp.ui.account

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.MainActivity
import com.example.shoppingapp.MainViewModel
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentAccountBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        bindView()
        setOnClickListener()
        observeData()
    }

    private fun observeData() {
        mainViewModel.currentUser.observe(viewLifecycleOwner) {
            binding.tvUserName.text = it.userName
            binding.tvEmail.text = it.email
        }
    }

    private fun bindView() {
        binding.rcvMenu.apply {
            adapter = MenuAdapter(mainViewModel.dataMenu)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setOnClickListener() {
        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            val editor = sharedPreferences.edit()
            editor.putBoolean("firstLogin", false)
            editor.putBoolean("logout", true)
            editor.apply()
            findNavController().navigate(R.id.action_accountFragment_to_loginFragment)
        }
    }

    private fun initData() {
        (requireActivity() as MainActivity).showBotNav()
        sharedPreferences = activity?.getSharedPreferences("Login", Context.MODE_PRIVATE)!!
    }
}