package com.target.targetcasestudy.ui.dealsitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.target.targetcasestudy.databinding.FragmentDealItemBinding
import com.target.targetcasestudy.ui.base.BaseFragment

class DealItemFragment : BaseFragment() {

    private var _binding: FragmentDealItemBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDealItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}