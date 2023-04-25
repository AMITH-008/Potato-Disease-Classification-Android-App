package com.company.potato_disease;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiCall {
    @POST("predict")
    Call<ServerResponse> predict(@Part MultipartBody.Part file);
}
