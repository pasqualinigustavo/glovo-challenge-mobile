package com.glovomap.rest;

import com.glovomap.BuildConfig;
import com.glovomap.sia.rest.endpoint.SearchEndPoint;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ApiComm {

    private static final String TAG = ApiComm.class.getSimpleName();
    private static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static Retrofit mRetrofit;
    private static OkHttpClient mHttpClient;
    private static Map<String, Object> mServicesPool = new HashMap<>();
    private static String URL_BASE = BuildConfig.DEBUG ? "http://192.168.1.13:3000/" : "http://192.168.1.13:3000/";
    private static ApiComm instance = null;
    private static Gson gson;

    @Inject
    public ApiComm() {
        // Define the interceptor, add authentication headers
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                String callUrl = chain.request().url().toString();
                builder.header("Content-Type", "application/json");
                builder.header("Connection", "close");
                // Get JSON response
                return chain.proceed(builder.build());
            }
        };

        // Logging interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        // Add the interceptor to OkHttpClient
        mHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor).build();


        // GSON Parser
        gson = new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .create();


        // Build the retrofit object
        mRetrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mHttpClient)
                .build();
    }

    public static synchronized ApiComm getInstance() {
        if (instance == null) {
            instance = new ApiComm();
        }
        return instance;
    }

    /**
     * Creates a service
     */
    public <T> T create(final Class<T> service) {
        String key = service.getSimpleName();
        if (!mServicesPool.containsKey(key)) {
            mServicesPool.put(key, mRetrofit.create(service));
        }
        return (T) mServicesPool.get(key);
    }

    public String getBaseUrl() {
        return mRetrofit.baseUrl().url().toString();
    }

    /**
     * Set base URL via reflection.
     */
    public void setBaseUrl(String newUrl) throws NoSuchFieldException, IllegalAccessException {
        Field field = Retrofit.class.getDeclaredField("baseUrl");
        field.setAccessible(true);
        HttpUrl newHttpUrl = HttpUrl.parse(newUrl);
        field.set(mRetrofit, newHttpUrl);
    }

    public SearchEndPoint searchEndPoint() {
        return create(SearchEndPoint.class);
    }

    public Gson gson() {
        return gson;
    }
}
