package br.com.nyan.marvel.data;

public class Thumbnail
{
    String path;
    String extension;

    public String getSmall()
    {
        return path + "/portrait_xlarge." + extension;
    }

    public String getLarge()
    {
        return path + "/landscape_incredible." + extension;
    }
}