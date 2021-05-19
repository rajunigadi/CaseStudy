package com.target.targetcasestudy.ui.deals

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.target.targetcasestudy.R
import com.target.targetcasestudy.common.RecycleViewItemClickListener
import com.target.targetcasestudy.data.repository.network.Product
import com.target.targetcasestudy.databinding.DealListItemBinding
import com.target.targetcasestudy.ui.base.BaseRecyclerViewAdapter
import javax.inject.Inject

class DealItemAdapter @Inject constructor(val requestManager: RequestManager): BaseRecyclerViewAdapter<RecyclerView.ViewHolder>() {

    private var itemClickListener: RecycleViewItemClickListener<Product>? = null
    private var items: MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductViewHolder(parent.getViewBinding(R.layout.deal_list_item))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductViewHolder).bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(moreItems: List<Product>) {
        items.clear()
        items.addAll(moreItems)
        if (attachedToRecyclerView) {
            notifyDataSetChanged()
        }
    }

    fun getItems(): List<Product> {
        return items
    }

    fun setOnClickListener(itemClickListener: RecycleViewItemClickListener<Product>?) {
        itemClickListener?.let {
            this.itemClickListener = it
        }
    }

    internal inner class ProductViewHolder(itemRowBinding: DealListItemBinding) :
        RecyclerView.ViewHolder(itemRowBinding.root) {

        var itemRowBinding: DealListItemBinding = itemRowBinding

        fun bind(item: Product) {
            itemRowBinding.item = item
            item.imageUrl?.let { imageUrl ->
                requestManager
                    .load(imageUrl)
                    .override(144, 144)
                    .into(itemRowBinding.ivDealImage)
            }
        }
    }
}