package com.example.vrgsofttestnechet.retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyInterface {
    @GET("/svc/mostpopular/v2/emailed/30.json?")
    Call<JsonObject> getEmailed(@Query("api-key") String api_key);

    @GET("/svc/mostpopular/v2/shared/1/facebook.json?")
    Call<JsonObject> getShared(@Query("api-key") String api_key);

    @GET("/svc/mostpopular/v2/viewed/30.json?")
    Call<JsonObject> getViewed(@Query("api-key") String api_key);
}
