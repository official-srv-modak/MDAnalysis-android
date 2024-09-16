package com.souravmodak.mdanalysis.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.souravmodak.mdanalysis.MainActivity;
import com.souravmodak.mdanalysis.R;
import com.souravmodak.mdanalysis.databinding.FragmentHomeBinding;
import com.souravmodak.mdanalysis.misc.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private View productListCard, productCard;
    private LinearLayout productListLinearLayout, homeLinearLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot(); // This is the root view that will be returned

        // Access the LinearLayout using binding instead of getView()
        homeLinearLayout = binding.homeLinearLayout;

        // Inflate a product card and add it to the LinearLayout
        productListCard = LayoutInflater.from(getContext()).inflate(R.layout.product_list_home, homeLinearLayout,  false);
        productListLinearLayout = productListCard.findViewById(R.id.product_list_linear_layout);

        fetchProducts();

        homeLinearLayout.addView(productListCard);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void fetchProducts(){

        ApiService apiService;

        // Set up Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getContext().getString(R.string.url_product)) // replace with your API's base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        Call<JsonObject> call = apiService.getAllProducts();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    // Assuming your response has a title field and a products array
                    JsonObject responseObject = response.body();
                    JsonArray products = responseObject.getAsJsonArray("products");

                    for(JsonElement val : products)
                    {
                        JsonObject obj = val.getAsJsonObject();
                        fetchProduct(obj.get("id").getAsInt());
                    }

                } else {
                    Toast.makeText(getContext(), "Failed to get response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchProduct(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getContext().getString(R.string.url_product)) // Replace with your actual base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<JsonObject> call = apiService.getProduct(
                "edbeb8ed-cef9-4a21-b2fb-abe1df24b3e7", // x-request-id
                "Basic YWRtaW46YWRtaW4=",               // Authorization
                id                                       // Product ID
        );

        // Asynchronously call the API
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    // Handle the product data
                    JsonObject productRes = response.body();
                    JsonObject product = productRes.getAsJsonObject("product");

                    productCard = LayoutInflater.from(getContext()).inflate(R.layout.product_card, productListLinearLayout,  false);

                    TextView productCardTitle = productCard.findViewById(R.id.product_title);
                    TextView productCardAccuracyValue = productCard.findViewById(R.id.product_card_accuracy_value);
                    TextView productCardDesModelValue = productCard.findViewById(R.id.product_card_description_value);
                    TextView productCardTrainModel = productCard.findViewById(R.id.product_card_train_model_file_value);
                    TextView productCardTestModel = productCard.findViewById(R.id.product_card_test_model_file_value);

                    productCardTitle.setText(product.get("name").getAsString());
                    productCardAccuracyValue.setText(product.get("accuracy").getAsString());
                    productCardDesModelValue.setText(product.get("description").getAsString());
                    productCardTrainModel.setText(product.get("trainModelPath").getAsString());
                    productCardTestModel.setText(product.get("testModelPath").getAsString());


                    productListLinearLayout.addView(productCard);
                } else {
                    Toast.makeText(getContext(), "Failed to get response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to get response", Toast.LENGTH_SHORT).show();
            }
        });
    }
}