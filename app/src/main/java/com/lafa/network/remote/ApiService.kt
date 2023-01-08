package com.lafa.network.remote

import com.lafa.model.LoginModel
import com.lafa.model.RegisterModel
import com.lafa.response.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService
{

//    call --------> Response

    @Headers("lang: ar")
    @POST("register")
    suspend fun createAccount(@Body registerModel: RegisterModel): Response<RegisterResponse>


    @Headers("lang: ar")
    @POST("login")
    suspend fun loginAccount(@Body loginModel: LoginModel): Response<LoginResponse>


    @GET("profile")
    suspend fun profileAccount(@Header("Authorization") authToken: String): Response<LoginResponse>


    @Headers("lang: ar")
    @POST("logout")
    suspend fun logoutAccount(@Header("Authorization") authTokenLogout: String): Response<LogoutResponse>


    @Headers("lang: ar")
    @PUT("update-profile")
    suspend fun updateProfile(@Header("Authorization") authTokenUpdate: String, @Body registerModel: RegisterModel): Response<UpdateProfileResponse>


    @Headers("lang: ar")
    @POST("change-password")
    suspend fun changePassword(@Header("Authorization") authTokenChange: String, @Body changePassword: ChangePassword): Response<ChangePasswordResponse>


    @GET("banners")
    suspend fun getImageBanners(): Response<BannerResponse>


//    @Headers("lang: ar")
//    @GET("home")
//    suspend fun getHomeImages(@Header("Authorization") authTokenProduct: String): Response<HomeResponse>


    @Headers("lang: ar")
    @POST("products/search")
    suspend fun getSearchProducts(@Header("Authorization") authTokenSearchProduct: String, @Body searchProductResponse: SearchProductResponse): Response<SearchResponse>


    @Headers("lang: ar")
    @GET("products")
    suspend fun getImageProducts(@Header("Authorization") authTokenProduct: String): Response<ProductResponse>


    @Headers("lang: ar")
    @GET("categories")
    suspend fun getCategories(): Response<CategoryResponse>


    @Headers("lang: ar")
    @GET("settings")
    suspend fun getSettings(): Response<DataSettingResponse>


    @Headers("lang: ar")
    @GET("faqs")
    suspend fun getQuestions(): Response<QuestionResponse>


    @Headers("lang: ar")
    @GET("contacts")
    suspend fun getContacts(): Response<ContactResponse>


    @Headers("lang: ar")
    @POST("complaints")
    suspend fun addNewComplaints(@Body dataComplaint: DataComplaint): Response<DataComplaintResponse>

}