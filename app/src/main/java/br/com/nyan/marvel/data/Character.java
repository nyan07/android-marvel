package br.com.nyan.marvel.data;

public class Character
{
    int id;

    String name;

    String description;

    Thumbnail thumbnail;

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public Thumbnail getThumbnail()
    {
        return thumbnail;
    }
}
