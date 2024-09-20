package com.souravmodak.mdanalysis.ui.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.souravmodak.mdanalysis.databinding.FragmentNewProductBinding;
import com.souravmodak.mdanalysis.misc.LibraryFuctions;
import com.souravmodak.mdanalysis.ui.polar_page_models.NewProductPageModel;

public class NewProductFragment extends Fragment {

    private FragmentNewProductBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewProductViewModel galleryViewModel =
                new ViewModelProvider(this).get(NewProductViewModel.class);

        binding = FragmentNewProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ConstraintLayout constraintLayout = binding.fragmentNewProductRoot;
        NewProductPageModel.renderUI(constraintLayout, getContext());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}