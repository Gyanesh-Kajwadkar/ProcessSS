package com.andoidproject.culluze_app.Activity.network;

import com.andoidproject.culluze_app.Activity.custumer_area.activity.AddUser_Activity;
import com.andoidproject.culluze_app.Activity.model.AddCustomer_Model;
import com.andoidproject.culluze_app.Activity.model.BranchUser_Model;
import com.andoidproject.culluze_app.Activity.model.BrandList_Model;
import com.andoidproject.culluze_app.Activity.model.Brand_SubBrand_Model;
import com.andoidproject.culluze_app.Activity.model.CompanyName_Model;
import com.andoidproject.culluze_app.Activity.model.CustomerBill_Model;
import com.andoidproject.culluze_app.Activity.model.FrequentlyModel;
import com.andoidproject.culluze_app.Activity.model.Get_CustomerModel;
import com.andoidproject.culluze_app.Activity.model.ItemList_Model;
import com.andoidproject.culluze_app.Activity.model.Orderlist_Model;
import com.andoidproject.culluze_app.Activity.model.PaymentMode_Model;
import com.andoidproject.culluze_app.Activity.model.Salesorderlist_Model;
import com.andoidproject.culluze_app.Activity.model.SubmitCheckInModel;
import com.andoidproject.culluze_app.Activity.model.SubmitOrder_Model;
import com.andoidproject.culluze_app.Activity.model.SubmitSales_Model;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    /*@GET("common/usrCd={var1}/pass={var2}")
    Call<String> loginData(@Path("var1") String userId, @Path("var2")String password);*/

    @GET("common")
    Call<String> loginData(@Query("usrCd") String userId, @Query("pass")String password);

    @GET("getCustList")
    Call<ArrayList<Get_CustomerModel>>getCustomers(@Query("usrCd")String userCd);

    @GET("brandList")
    Call<ArrayList<BrandList_Model>>brandlist();

    @GET("accountDtls")
    Call<ArrayList<PaymentMode_Model>>paymentMode(@Query("mode")String mode);

    @GET("itemList")
    Call<ArrayList<ItemList_Model>>itemList(@Query("brandId")int brandId);

    @GET("custmerBill")
    Call<CustomerBill_Model>customerBill(@Query("acctId")int cusId);

    @POST("submitCheckIn")
    Call<String>submitCheck(@Body SubmitCheckInModel submitCheckInModel);

    @POST("submitCustLoc")
    Call<String>latlong(@Body JsonObject object);

    @GET("brandItem/usrId={var}")
    Call<ArrayList<Brand_SubBrand_Model>>brandItem(@Path("var") String userId);

    /*@GET("itemListCust")
    Call<ArrayList<FrequentlyModel>>frequentlyBought(@Query("freqnt")String free,@Query("custId")int userId);*/

    @GET("itemListCust")
    Call<String>frequentlyBought(@Query("freqnt")String free,@Query("custId")int userId);

    @POST("submitOrder")
    Call<SubmitOrder_Model>submitOrder(@Body SubmitOrder_Model submitOrder_model);

    @POST("submitSales")
    Call<SubmitSales_Model>submitsales(@Body SubmitSales_Model submitSales_model);

    @POST("submitCust")
    Call<String>addCustomer(@Body AddCustomer_Model addusermodel);

    @GET("salesList")
    Call<ArrayList<Salesorderlist_Model>>salesOrderList(@Query("custId")int custId,@Query("usrId")int userId);

    @GET("orderList")
    Call<ArrayList<Orderlist_Model>>orderList(@Query("custId")int custId, @Query("usrId")int userId);

    @GET("company")
    Call<CompanyName_Model>comanyName();

    @GET("branchUsr/usrId={var}")
    Call<ArrayList<BranchUser_Model>>branchUser(@Path("var") String userId);

}
