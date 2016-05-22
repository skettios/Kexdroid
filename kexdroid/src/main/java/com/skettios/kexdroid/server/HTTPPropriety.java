package com.skettios.kexdroid.server;

public class HTTPPropriety
{
    private final String key;
    private final String value;

    public HTTPPropriety(String key, String value)
    {
        this.key = key;
        this.value = value;

        System.out.println(key);
        System.out.println(value);
    }

    public String getKey()
    {
        return this.key;
    }

    public String getValue()
    {
        return this.value;
    }
}
