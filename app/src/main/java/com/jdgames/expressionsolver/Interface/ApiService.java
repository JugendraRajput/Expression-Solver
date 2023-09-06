package com.jdgames.expressionsolver.Interface;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("?")
    Call<String> getResult(@Query("expr") String expression);
}