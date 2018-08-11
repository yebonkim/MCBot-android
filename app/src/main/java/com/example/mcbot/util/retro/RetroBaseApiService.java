package com.example.mcbot.util.retro;


import com.example.mcbot.model.Chat;
import com.example.mcbot.model.ChatResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by lipnus on 2018. 8. 11.
 */

public interface RetroBaseApiService {


    String Base_URL= "http://127.0.0.1:9000";

    @POST("/test")
    Call<ChatResult> postChat(@Body HashMap<String, Object> parameters);

}
