package com.skettios.kexdroid.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;

public class Util
{
    public static void writeu32(int value, OutputStream out) throws Exception
    {
        out.write(value >> 24 & 0xFF);
        out.write(value >> 16 & 0xFF);
        out.write(value >> 8 & 0xFF);
        out.write(value >> 0 & 0xFF);
    }

    public static byte[] toArray(String string)
    {
        byte[] data = new byte[string.length() / 2];

        for (int i = 0; i < data.length; i++)
        {
            String value = string.substring(i * 2, i * 2 + 2);
            data[i] = ((byte) Integer.parseInt(value, 16));
        }

        return data;
    }

    public static byte[] readFile(java.io.File file) throws java.io.IOException
    {
        FileInputStream in = new FileInputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] buffer = new byte['Ѐ'];

        int read;
        while ((read = in.read(buffer, 0, buffer.length)) > 0)
            out.write(buffer, 0, read);

        in.close();

        return out.toByteArray();
    }
}
