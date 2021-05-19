package com.target.targetcasestudy.ui.deals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.target.targetcasestudy.common.ObservableData
import com.target.targetcasestudy.data.repository.network.DealsResponse
import com.target.targetcasestudy.databinding.FragmentDealListBinding
import com.target.targetcasestudy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DealListFragment : BaseFragment() {

    private var _binding: FragmentDealListBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: DealsViewModel by viewModels()

    @Inject
    lateinit var dealItemAdapter: DealItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDealListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun configureView() {
        binding?.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView.adapter = dealItemAdapter

        viewModel
            .deals()
            .observeWithFragment(this, dealsObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val dealsObserver = Observer<ObservableData<DealsResponse, Throwable>> {
        if (it.hasError()) {
            handleApiError(it.error)
        } else if (it.hasData()) {
            it.data?.products?.let { items -> dealItemAdapter.setItems(items) }
        }
    }
}
