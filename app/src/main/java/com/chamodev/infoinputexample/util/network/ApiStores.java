package com.chamodev.infoinputexample.util.network;

import com.chamodev.infoinputexample.BuildConfig;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Koo on 2016. 10. 19..
 */

public interface ApiStores {
    String BASE_URL = BuildConfig.BASE_URL;

    @GET("api/v11/mission/infoinput/")
    Call<ResponseBody> getInfoInputSurvey(@Query("sid") String sid, @Query("tid") int tid);

    @FormUrlEncoded
    @POST("api/v11/mission/infoinput/")
    Call<ResponseBody> postInfoInputSurvey(@FieldMap Map<String, String> params);

}
