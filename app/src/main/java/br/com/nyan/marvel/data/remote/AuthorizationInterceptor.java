package br.com.nyan.marvel.data.remote;

import android.util.Log;

import java.io.IOException;

import br.com.nyan.marvel.Config;
import br.com.nyan.marvel.utils.Utils;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor
{
    @Override
    public Response intercept(Chain chain) throws IOException
    {
        String ts = String.valueOf(System.currentTimeMillis());
        String apikey = Config.PUBLIC_KEY;
        String hash = Utils.md5(ts + Config.PRIVATE_KEY + Config.PUBLIC_KEY);

        Request request = chain.request();

        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("ts", ts)
                .addQueryParameter("apikey", apikey)
                .addQueryParameter("hash", hash)
                .build();

        Log.e("URL", url.toString());

        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}