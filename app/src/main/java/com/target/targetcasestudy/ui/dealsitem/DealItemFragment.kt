package com.target.targetcasestudy.ui.dealsitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.target.targetcasestudy.R
import com.target.targetcasestudy.common.ObservableData
import com.target.targetcasestudy.common.applyStrikeSpannableString
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
        binding.tvAddToList.setOnClickListener {
            showSnackBar(binding.clContent, getString(R.string.feature_under_development))
        }
        binding.tvAddToCart.setOnClickListener {
            showSnackBar(binding.clContent, getString(R.string.feature_under_development))
        }
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
            binding.tvTittle.text = it.data?.title
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

            if(it.data.salePrice != null) {
                binding.tvPrice.text = it.data.salePrice.displayString
                binding.tvReg.visibility = View.VISIBLE
                binding.tvRegPrice.applyStrikeSpannableString(it.data.regularPrice.displayString)
            } else {
                binding.tvPrice.text = it.data.regularPrice.displayString
                binding.tvReg.visibility = View.GONE
                binding.tvRegPrice.visibility = View.GONE
            }
        }
    }
}