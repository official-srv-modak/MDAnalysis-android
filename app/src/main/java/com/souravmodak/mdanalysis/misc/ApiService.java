package com.souravmodak.mdanalysis.misc;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ApiService {

    @GET("get-all-products")
    Call<JsonObject> getAllProducts();

    @GET("get-product/{id}")
    Call<JsonObject> getProduct(
            @Header("x-request-id") String requestId,
            @Header("Authorization") String authorization,
            @Path("id") int productId
    );
}
