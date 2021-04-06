package com.example.farmersportal.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.farmersportal.R;
import com.example.farmersportal.application.BaseApplication;
import com.example.farmersportal.database.Product;
import com.example.farmersportal.database.User;
import com.example.farmersportal.viewmodel.MainFactory;
import com.example.farmersportal.viewmodel.ProductViewModel;
import com.example.farmersportal.viewmodel.UserViewModel;

public class ProduceDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce_details);
        setSupportActionBar(findViewById(R.id.toolbar));

        Intent i = getIntent();
        int productId = i.getIntExtra("EXTRA_PRODUCT_ID", 0);
        int sellerId = i.getIntExtra("EXTRA_SELLER_ID", 0);

        MainFactory factory = new MainFactory(getApplication());
        UserViewModel userViewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);
        ProductViewModel productViewModel = new ViewModelProvider(this, factory).get(ProductViewModel.class);

        TextView textViewProduct = findViewById(R.id.textViewProduct);
        TextView textViewSeller = findViewById(R.id.textViewSeller);
        TextView textViewLocation = findViewById(R.id.textViewLocation);
        TextView textViewEmail = findViewById(R.id.textViewEmail);
        TextView textViewPhone = findViewById(R.id.textViewPhone);
        TextView textViewQuantity = findViewById(R.id.textViewQuantity);
        TextView textViewPrice = findViewById(R.id.textViewPrice);
        TextView textViewBuyer = findViewById(R.id.textViewBuyer);
        TextView textViewBuyerEmail = findViewById(R.id.textViewBuyerEmail);
        TextView textViewBuyerPhone = findViewById(R.id.textViewBuyerPhone);
        Button buttonSendEmail = findViewById(R.id.buttonSendEmail);
        Button buttonCall = findViewById(R.id.buttonCall);
        Button buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);

        if (productId == 0 || sellerId == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("There was an error displaying produce details. Please try again")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, which) -> finish())
                    .show();
        } else {
            try {
                String string;
                Spanned spanned;
                User seller = userViewModel.getUser(sellerId);
                Product product = productViewModel.getProduct(productId);
                string = getString(R.string.textview_product_name, product.getName());
                spanned = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY);
                textViewProduct.setText(spanned);

                string = getString(R.string.textview_seller_name, seller.getName());
                spanned = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY);
                textViewSeller.setText(spanned);

                string = getString(R.string.textview_location, seller.getLocation());
                spanned = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY);
                textViewLocation.setText(spanned);

                string = getString(R.string.textview_email, seller.getEmail());
                spanned = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY);
                textViewEmail.setText(spanned);

                string = getString(R.string.textview_phone, seller.getPhoneNumber());
                spanned = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY);
                textViewPhone.setText(spanned);

                string = getString(R.string.textview_quantity, product.getQuantity());
                spanned = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY);
                textViewQuantity.setText(spanned);

                string = getString(R.string.textview_price, product.getPrice());
                spanned = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY);
                textViewPrice.setText(spanned);

                buttonSendEmail.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:" + seller.getEmail()));
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(this, "No Email app found!!!", Toast.LENGTH_SHORT).show();
                    }
                });

                buttonCall.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + seller.getPhoneNumber()));
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(this, "No Dialer app found!!!", Toast.LENGTH_SHORT).show();
                    }
                });

                if (product.getBuyerId() != 0 && BaseApplication.getApplicationInstance().getSessionUser().getUserType() == 1) {
                    User buyer = userViewModel.getUser(product.getBuyerId());
                    string = getString(R.string.textview_buyer_name, buyer.getName());
                    spanned = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY);
                    textViewBuyer.setText(spanned);
                    textViewBuyer.setVisibility(View.VISIBLE);

                    string = getString(R.string.textview_buyer_email, buyer.getEmail());
                    spanned = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY);
                    textViewBuyerEmail.setText(spanned);
                    textViewBuyerEmail.setVisibility(View.VISIBLE);

                    string = getString(R.string.textview_buyer_phone, buyer.getPhoneNumber());
                    spanned = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY);
                    textViewBuyerPhone.setText(spanned);
                    textViewBuyerPhone.setVisibility(View.VISIBLE);

                    buttonSendEmail.setText("Mail Buyer");
                    buttonSendEmail.setOnClickListener(v -> {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:" + buyer.getEmail()));
                        try {
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(this, "No Email app found!!!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    buttonCall.setText("Call Buyer");
                    buttonCall.setOnClickListener(v -> {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + buyer.getPhoneNumber()));
                        try {
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(this, "No Dialer app found!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                if (BaseApplication.getApplicationInstance().getSessionUser().getUserType() == 0 && product.getBuyerId() == 0) {
                    buttonPlaceOrder.setVisibility(View.VISIBLE);
                    buttonPlaceOrder.setOnClickListener(v -> new AlertDialog.Builder(this)
                            .setTitle("Order Confirmation")
                            .setMessage("Are you sure you want to place this order?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                Product soldProduct = new Product(product.getName(), product.getPrice(), product.getQuantity(), product.getSellerId(), BaseApplication.getApplicationInstance().getSessionUser().getId());
                                soldProduct.setId(productId);
                                productViewModel.update(soldProduct);
                                Toast.makeText(this, "Order confirmed\nYou can find it in Order History", Toast.LENGTH_SHORT).show();
                                finish();
                            }).setNegativeButton("No", (dialog, which) -> {
                            })
                            .show());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}