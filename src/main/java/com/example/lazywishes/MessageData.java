package com.example.lazywishes;

public class MessageData {
    private static MessageData sInstance;
    private String mMessage;

    private MessageData() {}

    public static  MessageData getInstance() {
        if (sInstance == null) {
            sInstance = new MessageData();
        }
        return sInstance;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
