package com.example.farmersportal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.farmersportal.R;
import com.example.farmersportal.ViewProduceActivity;
import com.google.android.material.card.MaterialCardView;

public class BuyerCardsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_buyer_cards, container, false);

        MaterialCardView cardViewProduce = layout.findViewById(R.id.cardViewProduce);
        MaterialCardView cardHistory = layout.findViewById(R.id.cardHistory);

        cardViewProduce.setOnClickListener(v -> startActivity(new Intent(requireContext(), ViewProduceActivity.class)));

        cardHistory.setOnClickListener(v -> Toast.makeText(requireContext(), "History", Toast.LENGTH_SHORT).show());

        return layout;
    }
}
