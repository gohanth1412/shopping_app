package com.example.shoppingapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shoppingapp.MainActivity
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentRegisterBinding
import com.example.shoppingapp.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    @Inject lateinit var firebaseAuth: FirebaseAuth
    @Inject lateinit var firebaseDb: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        onClickListener()
    }

    private fun initData() {
        (requireActivity() as MainActivity).hideBotNav()
    }

    private fun onClickListener() {
        binding.tvLogin.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvRegister.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val confirmPass = binding.edtConfirmPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (password != confirmPass) {
                    Toast.makeText(
                        requireContext(),
                        "Pass is not equals to confirm pass",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    binding.progressBar.visibility = View.VISIBLE
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Register Success",
                                Toast.LENGTH_SHORT
                            ).show()
                            addUserToDb(email, password)
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        } else {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "Register Fail",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
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

    private fun addUserToDb(email: String, pass: String) {
        val currentUid = firebaseAuth.uid
        val myRef = firebaseDb.getReference("users")
        myRef.child(currentUid!!).setValue(UserModel(currentUid, "User${currentUid.take(5)}", email, pass))
    }
}