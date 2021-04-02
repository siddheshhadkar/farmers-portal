package com.example.farmersportal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersportal.R;
import com.example.farmersportal.database.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private List<Product> productList = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ProductAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductHolder(itemView, listener, productList);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductHolder holder, int position) {
        Product product = productList.get(position);
        holder.product.setText(product.getName());
        holder.seller.setText("SELLER");
        holder.location.setText("LOCATION");
        holder.price.setText(product.getPrice());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setProductList(List<Product> productList){
        this.productList = productList;
        notifyDataSetChanged();
    }

    public Product getProductAt(int position){
        return productList.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    static class ProductHolder extends RecyclerView.ViewHolder {

        private final TextView product;
        private final TextView seller;
        private final TextView location;
        private final TextView price;

        public ProductHolder(@NonNull View itemView, OnItemClickListener listener, List<Product> productList) {
            super(itemView);
            product = itemView.findViewById(R.id.textViewProduct);
            seller = itemView.findViewById(R.id.textViewSeller);
            location = itemView.findViewById(R.id.textViewLocation);
            price = itemView.findViewById(R.id.textViewPrice);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(productList.get(position));
                }
            });
        }
    }
}
