package com.example.ahmtonur.projedeneme4;

/**
 * Created by on 10.05.2019.
 */

public class MyData {

    private String name;
    private byte[] image;

    public MyData(String name, byte[] image)
    {
        this.name = name;
        this.image = image;

    }
    public String GetName()
    {
        return name;
    }
    public byte[] GetImage()
    {
        return image;
    }
    public void SetName(String name)
    {
        this.name = name;
    }
    public void SetImage(byte[] image)
    {
        this.image = image;
    }
}
