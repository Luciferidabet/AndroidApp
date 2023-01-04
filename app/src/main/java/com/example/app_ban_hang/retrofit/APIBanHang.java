package com.example.app_ban_hang.retrofit;

import com.example.app_ban_hang.model.NewProductModel;
import com.example.app_ban_hang.model.ProductTypeModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIBanHang {
    @GET("ProductType.php")
    Observable<ProductTypeModel> getProductType();


    @GET("GetNewProduct.php")
    Observable<NewProductModel> getNewProduct();

    @POST("GetProduct.php")
    @FormUrlEncoded
    Observable<NewProductModel> getProduct(
            @Field("page") int page,
            @Field("Type") int Type
    );

}
