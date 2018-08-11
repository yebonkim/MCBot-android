package com.example.mcbot.util.retro;

/**
 * Created by lipnus on 2018. 8. 11.
 */

public interface RetroCallback<T> {
    void onError(Throwable t);

    void onSuccess(int code, T receivedData);

    void onFailure(int code);
}
