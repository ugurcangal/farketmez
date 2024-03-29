package com.example.farketmez.view

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.farketmez.BaseFragment
import com.example.farketmez.adapter.DontShowAgainAdapter
import com.example.farketmez.databinding.FragmentDontShowAgainBinding
import com.example.farketmez.viewmodel.DontShowAgainViewModel


class DontShowAgainFragment : BaseFragment<FragmentDontShowAgainBinding,DontShowAgainViewModel>(FragmentDontShowAgainBinding::inflate) {

    private lateinit var dontShowAgainAdapter : DontShowAgainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllDontShowAgain(binding)
        observeDontShowAgainList()

        binding.closeBtn.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun observeDontShowAgainList(){
        viewModel.observeDontShowAgainList().observe(viewLifecycleOwner){
            dontShowAgainAdapter = DontShowAgainAdapter(it)
            binding.dontShowAgainRV.layoutManager = LinearLayoutManager(context)
            binding.dontShowAgainRV.adapter = dontShowAgainAdapter
            dontShowAgainAdapter.notifyDataSetChanged()
        }
    }


    override fun getViewModel(): Class<DontShowAgainViewModel> = DontShowAgainViewModel::class.java

}