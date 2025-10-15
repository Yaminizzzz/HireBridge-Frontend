package com.simats.hirebridge;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> registerUser(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );
    @GET("get_jobs.php")
    Call<List<Job>> getJobs();
    @GET("get_profile.php")
    Call<ProfileResponse> getProfile(@Query("user_id") Integer userId);
    @Multipart
    @POST("upload_personal_details.php")
    Call<ApiResponse> uploadPersonalDetails(
            @Part("username") RequestBody username,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part("location") RequestBody location,
            @Part("gender") RequestBody gender,
            @Part("languages") RequestBody languages,
            @Part("college") RequestBody college,
            @Part("cgpa") RequestBody cgpa,
            @Part("domain") RequestBody domain,
            @Part MultipartBody.Part resume
    );

    @Multipart
    @POST("set_details_filled.php") // Change to your actual endpoint
    Call<ApiResponse> setDetailsFilled(
            @Part("details_filled") int detailsFilled
    );
    @FormUrlEncoded
    @POST("change_password.php")
    Call<PasswordResponse> changePassword(
            @Field("user_id") int user_id,
            @Field("password") String password
    );
}
