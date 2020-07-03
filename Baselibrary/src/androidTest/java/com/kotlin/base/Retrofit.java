package com.kotlin.base;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class Retrofit {

    public void inti() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request()
                        .newBuilder()
                        .header("Content-Type", "application/json")
                        .header("charset", "utf-8")
                        .build();
                return chain.proceed(request);
            }
        };
    }
    public Interceptor te(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .header("Content-Type", "application/json")
                        .header("charset", "utf-8")
                        .build();
                return chain.proceed(request);
            }
        };
    }
}
