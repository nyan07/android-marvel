package br.com.nyan.marvel.data.remote;

import br.com.nyan.marvel.data.Page;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CharacterRemoteService
{
    @GET("characters")
    Call<Page> getCharacters(@Query("limit") int limit, @Query("offset") int offset);
}
