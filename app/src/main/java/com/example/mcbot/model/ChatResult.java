package com.example.mcbot.model;

/**
 * Created by yebonkim on 2018. 8. 8..
 */

public class ChatResult {
    private boolean result;

    public ChatResult(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
