package com.skettios.kexdroid.server;

import java.util.List;

public class HTTPRequest
{
    private final String method;
    private final String protocol;
    private final String path;
    private final List<HTTPPropriety> proprieties;

    public HTTPRequest(String method, String protocol, String path, List<HTTPPropriety> proprieties)
    {
        this.method = method;
        this.protocol = protocol;
        this.path = path;
        this.proprieties = java.util.Collections.unmodifiableList(proprieties);
    }

    public String getMethod()
    {
        return this.method;
    }

    public String getProtocol()
    {
        return this.protocol;
    }

    public String getPath()
    {
        return this.path;
    }

    public List<HTTPPropriety> getProprieties()
    {
        return this.proprieties;
    }

    public HTTPPropriety getPropriety(String key)
    {
        for (HTTPPropriety prop : this.proprieties)
        {
            if (prop.getKey().equals(key))
            {
                return prop;
            }
        }

        return null;
    }
}
