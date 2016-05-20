package com.skettios.kexdroid;

public final class ResourceCounter
{
    private long payloadsServed = 0L;
    private long dataServed = 0L;
    private long errors = 0L;


    public static final ResourceCounter instance = new ResourceCounter();

    public synchronized long payloadServed()
    {
        return ++this.payloadsServed;
    }

    public synchronized long dataServed()
    {
        return ++this.dataServed;
    }

    public synchronized long errors()
    {
        return ++this.errors;
    }
}
