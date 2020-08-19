package com.iot.timetomeet.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.iot.timetomeet.api.ApiContext.BASE_URL_API;

public class RetrofitApiFactory {

    private static Retrofit instance;

    public static Retrofit create() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build();
        }

        return instance;
    }
}



