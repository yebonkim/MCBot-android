package com.example.mcbot.util.retro;


import com.example.mcbot.model.PostResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by lipnus on 2018. 8. 11.
 */

public interface RetroBaseApiService {


    String Base_URL= "http://5fa840f6.ngrok.io";

    @POST("/api/chats")
    Call<PostResult> postChat(@Body HashMap<String, Object> parameters);

    @POST("/api/faces")
    Call<PostResult> postFace(@Body HashMap<String, Object> parameters);
}
