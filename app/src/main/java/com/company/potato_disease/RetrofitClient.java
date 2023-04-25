package com.company.potato_disease;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String BASE_URL = "https://us-central1-plated-entry-384509.cloudfunctions.net/";

    public static Retrofit retrofitInstance;

    public static Retrofit getInstance() {
        if(retrofitInstance == null){
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient()).build();
        }

        return retrofitInstance;
    }
}
