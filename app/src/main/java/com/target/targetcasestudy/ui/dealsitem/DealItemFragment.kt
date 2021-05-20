package com.target.targetcasestudy.ui.dealsitem

import android.os.Bundle
import android.text.Html
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.target.targetcasestudy.common.ObservableData
import com.target.targetcasestudy.data.repository.network.Product
import com.target.targetcasestudy.databinding.FragmentDealItemBinding
import com.target.targetcasestudy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DealItemFragment: BaseFragment() {

    private var _binding: FragmentDealItemBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: DealsItemViewModel by viewModels()

    private val args: DealItemFragmentArgs by navArgs()

    @Inject
    lateinit var requestManager: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDealItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun configureView() {
        viewModel
            .dealItem(args.id)
            .observeWithFragment(this, dealsItemObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val dealsItemObserver = Observer<ObservableData<Product, Throwable>> {
        if (it.hasError()) {
            handleApiError(it.error)
        } else if (it.hasData()) {
            Timber.d("length: ${it.data?.description?.length}")
            binding?.item = it.data
            val description = it.data?.description

            if(description?.length!! > 5000) {
                val newDescription = description.substring(0, 5000)
                binding.tvDescription.text = newDescription
            } else {
                binding.tvDescription.text = description
            }
            it.data?.imageUrl?.let { imageUrl ->
                requestManager
                    .load(imageUrl)
                    .override(200, 200)
                    .into(binding?.imageView)
            }
        }
    }
}