package com.example.appsale12102022.presentations.activities
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appsale12102022.R
import com.example.appsale12102022.data.model.Cart
import com.example.appsale12102022.data.model.Product
import com.example.appsale12102022.data.remote.AppResource
import com.example.appsale12102022.databinding.ActivityProductBinding
import com.example.appsale12102022.presentations.adapters.ProductAdapter
import com.example.appsale12102022.presentations.viewmodels.ProductViewModel
class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productAdapter: ProductAdapter
    private lateinit var tvCountCart: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initial()
        observerData()
        productViewModel.fetchListProducts()
        productViewModel.fetchCart()

    }
    private fun observerData() {
        productViewModel.getProductResource().observe(this, Observer { resource ->
            when (resource.status) {
                AppResource.Status.SUCCESS -> {
                    binding.layoutLoading.layoutLoading.visibility = View.GONE
                    resource.data?.let { productAdapter.updateListProduct(it) }
                }
                AppResource.Status.LOADING -> binding.layoutLoading.layoutLoading.visibility = View.VISIBLE
                AppResource.Status.ERROR -> {
                    binding.layoutLoading.layoutLoading.visibility = View.GONE
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        productViewModel.getCartResource().observe(this, Observer { resource ->
            resource?.let {
                when (it.status) {
                    AppResource.Status.SUCCESS -> {
                        binding.layoutLoading.layoutLoading.visibility = View.GONE
                        var quantity: Int = 4
                        it.data?.productList?.let { productList ->
                            if (productList.isEmpty()) return@let
                            for (product in productList) {
                                quantity += product.quantity!! // Update the value of quantity here
                            }
                        }
                        setupBadge(quantity) // Call setupBadge with the updated value of quantity
                    }
                    AppResource.Status.LOADING -> binding.layoutLoading.layoutLoading.visibility = View.VISIBLE
                    AppResource.Status.ERROR -> {
                        binding.layoutLoading.layoutLoading.visibility = View.GONE
                        setupBadge(4)
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
    private fun initial() {
        // toolbar
        setSupportActionBar(binding.toolbarHome)
        supportActionBar?.title = "Food"
        productViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            @NonNull
            override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
                return ProductViewModel(this@ProductActivity) as T
            }
        }).get(ProductViewModel::class.java)
        productAdapter = ProductAdapter()
        binding.recyclerViewProduct.adapter = productAdapter
        binding.recyclerViewProduct.setHasFixedSize(true)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_product, menu)
        val menuItem = menu.findItem(R.id.item_menu_cart)
        val actionView = menuItem.actionView
        tvCountCart = actionView.findViewById(R.id.text_cart_badge)
        setupBadge(0)
        actionView.setOnClickListener { onOptionsItemSelected(menuItem) }
        return true
    }
    private fun setupBadge(quantities: Int) {
        if (quantities == 0) {
            tvCountCart.visibility = View.GONE
        } else {
            tvCountCart.visibility = View.VISIBLE
            tvCountCart.text = quantities.coerceAtMost(99).toString()
        }
    }
    override fun onOptionsItemSelected(@NonNull item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_menu_cart -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }
}