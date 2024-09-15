package com.souravmodak.mdanalysis.misc;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("get-all-products")
    Call<JsonObject> getDynamicTitle();
}
