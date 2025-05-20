package com.example.cinemaapp.api;

import com.example.cinemaapp.dto.FoodDetailDTO;
import com.example.cinemaapp.dto.InvoiceCommonDTO;
import com.example.cinemaapp.dto.response.InvoiceDetailDTO;
import com.example.cinemaapp.dto.response.InvoiceResponse;
import com.example.cinemaapp.factory.GeneralResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InvoiceAPI {
    @POST("/api/invoice/create")
    Call<GeneralResponse<InvoiceResponse>> createInvoice(@Body List<FoodDetailDTO> foodDetailDTOList);

    @GET("/api/invoice/save")
    Call<GeneralResponse<String>> saveInvoice();

    @GET("/api/invoice/getAll")
    Call<GeneralResponse<List<InvoiceCommonDTO>>> getInvoice();

    @GET("/api/invoice/getDetail")
    Call<GeneralResponse<InvoiceDetailDTO>> getInvoiceDetail(@Query("id") Integer id);
}
