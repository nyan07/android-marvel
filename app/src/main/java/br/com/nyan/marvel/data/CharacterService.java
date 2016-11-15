package br.com.nyan.marvel.data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.nyan.marvel.Config;
import br.com.nyan.marvel.data.remote.AuthorizationInterceptor;
import br.com.nyan.marvel.data.remote.CharacterRemoteService;
import br.com.nyan.marvel.data.remote.MarvelDeserializer;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CharacterService
{
    private static CharacterService INSTANCE;
    private final List<Character> items = new ArrayList<Character>();
    private final Map<String, Character> itemMap = new HashMap<String, Character>();
    private CharacterRemoteService mRemoteService;
    private OnDataSetChangedListener mListener;
    private Page mLastPage;

    private CharacterService()
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new AuthorizationInterceptor())
                .build();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Page.class, new MarvelDeserializer<Page>());
        Gson gson = gsonBuilder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Config.API_URL)
                .build();

        this.mRemoteService = retrofit.create(CharacterRemoteService.class);
    }

    public static CharacterService getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new CharacterService();
        }

        return INSTANCE;
    }

    public void setOnDataSetChangedListener(OnDataSetChangedListener listener)
    {
        mListener = listener;
    }

    private void addItem(Character character)
    {
        String key = String.valueOf(character.getId());
        if (!itemMap.containsKey(key))
        {
            items.add(character);
            itemMap.put(key, character);
        }
    }

    public Character getCharacter(String id)
    {
        return itemMap.get(id);
    }

    public void loadCharacters()
    {
        int limit = 20;
        int offset = 0;

        if (mLastPage != null && !mLastPage.isLastPage())
        {
            offset = mLastPage.offset + mLastPage.count;
        }

        Call<Page> call = mRemoteService.getCharacters(limit, offset);

        call.enqueue(new Callback<Page>()
        {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response)
            {
                if (response.isSuccessful())
                {
                    mLastPage = response.body();

                    List<Character> characters = mLastPage.getCharacters();

                    for (Character character : characters)
                    {
                        addItem(character);
                    }

                    if (mListener != null)
                    {
                        mListener.OnDataSetChanged();
                    }
                }
                else
                {
                    Log.d("Error", response.toString());
                }
            }

            @Override
            public void onFailure(Call<Page> call, Throwable t)
            {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
            }
        });
    }

    public List<Character> getCharacters()
    {
        return items;
    }

    public interface OnDataSetChangedListener
    {
        void OnDataSetChanged();
    }
}
