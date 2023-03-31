package com.example.appsale12102022.presentations.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appsale12102022.R;
import com.example.appsale12102022.data.model.Cart;
import com.example.appsale12102022.data.model.Product;
import com.example.appsale12102022.data.remote.AppResource;
import com.example.appsale12102022.databinding.ActivityProductBinding;
import com.example.appsale12102022.presentations.adapters.ProductAdapter;
import com.example.appsale12102022.presentations.viewmodels.ProductViewModel;

import java.util.List;

public class ProductActivity extends AppCompatActivity {

    ActivityProductBinding binding;
    ProductViewModel productViewModel;
    ProductAdapter productAdapter;
    TextView tvCountCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initial();
        observerData();

        productViewModel.fetchListProducts();
        productViewModel.fetchCart();
    }

    private void observerData() {
        productViewModel.getProductResource().observe(this, new Observer<AppResource<List<Product>>>() {
            @Override
            public void onChanged(AppResource<List<Product>> resource) {
                switch (resource.status) {
                    case SUCCESS:
                        binding.layoutLoading.layoutLoading.setVisibility(View.GONE);
                        productAdapter.updateListProduct(resource.data);
                        break;
                    case LOADING:
                        binding.layoutLoading.layoutLoading.setVisibility(View.VISIBLE);
                        break;
                    case ERROR:
                        binding.layoutLoading.layoutLoading.setVisibility(View.GONE);
                        Toast.makeText(ProductActivity.this, resource.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        productViewModel.getCartResource().observe(this, new Observer<AppResource<Cart>>() {
            @Override
            public void onChanged(AppResource<Cart> resource) {
                if (resource == null) return;
                switch (resource.status) {
                    case SUCCESS:
                        binding.layoutLoading.layoutLoading.setVisibility(View.GONE);
                        int quantity = 0;
                        if (resource.data.getProductList() == null || resource.data.getProductList().isEmpty()) return;
                        for (Product product: resource.data.getProductList()) {
                            quantity += product.getQuantity();
                        }
                        setupBadge(quantity);
                        break;
                    case LOADING:
                        binding.layoutLoading.layoutLoading.setVisibility(View.VISIBLE);
                        break;
                    case ERROR:
                        binding.layoutLoading.layoutLoading.setVisibility(View.GONE);
                        setupBadge(0);
                        Toast.makeText(ProductActivity.this, resource.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void initial() {
        // toolbar
        setSupportActionBar(binding.toolbarHome);
        getSupportActionBar().setTitle("Food");

        productViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new ProductViewModel(ProductActivity.this);
            }
        }).get(ProductViewModel.class);

        productAdapter = new ProductAdapter();
        binding.recyclerViewProduct.setAdapter(productAdapter);
        binding.recyclerViewProduct.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product, menu);
        final MenuItem menuItem = menu.findItem(R.id.item_menu_cart);
        View actionView = menuItem.getActionView();
        tvCountCart = actionView.findViewById(R.id.text_cart_badge);
        setupBadge(0);
        actionView.setOnClickListener(v -> onOptionsItemSelected(menuItem));
        return true;
    }

    private void setupBadge(int quantities) {
        if (quantities == 0) {
            tvCountCart.setVisibility(View.GONE);
        } else {
            tvCountCart.setVisibility(View.VISIBLE);
            tvCountCart.setText(String.valueOf(Math.min(quantities, 99)));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_cart:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
