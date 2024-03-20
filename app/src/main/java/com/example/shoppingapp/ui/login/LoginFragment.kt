package com.example.shoppingapp.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shoppingapp.MainActivity
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    @Inject lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        onClickListener()
    }

    private fun initData() {
        (requireActivity() as MainActivity).hideBotNav()
        sharedPreferences = activity?.getSharedPreferences("Login", Context.MODE_PRIVATE)!!
        checkIsLogout()
    }

    private fun checkIsLogout() {
        val isLogout = sharedPreferences.getBoolean("logout", false)
        if (isLogout) {
            val editor = sharedPreferences.edit()
            editor.putBoolean("logout", false)
            editor.apply()
            (requireActivity() as MainActivity).recreate()
        }
    }

    private fun onClickListener() {
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.tvLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                binding.progressBar.visibility = View.VISIBLE
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            flagFistLogin()
                            Toast.makeText(requireContext(), "Login success", Toast.LENGTH_SHORT)
                                .show()
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        } else {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "Email or password is wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Enter email and password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun flagFistLogin() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("firstLogin", true)
        editor.apply()
    }
}