package com.souravmodak.mdanalysis.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.souravmodak.mdanalysis.R;
import com.souravmodak.mdanalysis.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot(); // This is the root view that will be returned

        // Access the LinearLayout using binding instead of getView()
        LinearLayout homeLinearLayout = binding.homeLinearLayout;

        // Inflate a product card and add it to the LinearLayout
        View productListCard = LayoutInflater.from(getContext()).inflate(R.layout.product_list_home, homeLinearLayout,  false);
        LinearLayout productListLinearLayout = productListCard.findViewById(R.id.product_list_linear_layout);
        View productCard = LayoutInflater.from(getContext()).inflate(R.layout.product_card, productListLinearLayout,  false);
        productListLinearLayout.addView(productCard);
        homeLinearLayout.addView(productListCard);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}