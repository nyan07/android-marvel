package br.com.nyan.marvel.data;

import java.util.List;

public class Page
{
    int offset;

    int limit;

    int total;

    int count;

    List<Character> results;

    public boolean isLastPage()
    {
        return (limit + offset) >= total;
    }

    public List<Character> getCharacters()
    {
        return results;
    }
}
