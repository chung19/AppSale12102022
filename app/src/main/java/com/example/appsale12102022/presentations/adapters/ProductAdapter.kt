package com.example.appsale12102022.presentations.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appsale12102022.R
import com.example.appsale12102022.common.AppConstant
import com.example.appsale12102022.data.model.Product
import com.example.appsale12102022.databinding.LayoutProductItemBinding
import com.example.appsale12102022.presentations.adapters.ProductAdapter.ProductViewHolder
import com.example.appsale12102022.utils.StringUtil

class ProductAdapter : RecyclerView.Adapter<ProductViewHolder>() {
    var getListProducts: MutableList<Product>? = ArrayList()
    var context: Context? = null
    private var onItemClickProduct: OnItemClickProduct? = null

    fun updateListProduct(data: List<Product>?) {
        if (getListProducts != null && getListProducts!!.size > 0) {
            getListProducts!!.clear()
        }
        getListProducts!!.addAll(data!!)
        notifyDataSetChanged()
    }

    fun getGetListProductsMutable(): List<Product>? {
        return getListProducts
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        context = parent.context
        return ProductViewHolder(
            LayoutProductItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(context, getListProducts!![position])
    }

    override fun getItemCount(): Int {
        return getListProducts!!.size
    }

    inner class ProductViewHolder(var binding: LayoutProductItemBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        init {
            binding.buttonAdd.setOnClickListener {
                if (onItemClickProduct != null) {
                    onItemClickProduct!!.onClick(adapterPosition)
                }
            }
        }

        fun bind(context: Context?, product: Product) {
            binding.textViewName.text = product.name
            binding.textViewAddress.text = product.address
            binding.textViewPrice.text =
                String.format("%s VND", StringUtil.formatCurrency(product.price!!))
            Glide.with(context!!)
                .load(AppConstant.BASE_URL + product.img)
                .placeholder(R.drawable.ic_logo)
                .into(binding.imageView)
        }
    }

    fun setOnItemClickFood(onItemClickProduct: OnItemClickProduct?) {
        this.onItemClickProduct = onItemClickProduct
    }

    interface OnItemClickProduct {
        fun onClick(position: Int)
    }
}