package com.example.farketmez.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.farketmez.databinding.ActivityRegisterBinding
import com.example.farketmez.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        binding.registerBTN.setOnClickListener {
            viewModel.signUpFirebase(
                binding.nameET.text.toString(),
                binding.surnameET.text.toString(),
                binding.emailET.text.toString(),
                binding.passwordET.text.toString(),
                binding.passwordConfirmET.text.toString(),
                this
            )
        }

    }

}