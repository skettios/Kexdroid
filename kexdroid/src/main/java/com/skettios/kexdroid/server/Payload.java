package com.skettios.kexdroid.server;

import com.skettios.kexdroid.MainActivity;
import com.skettios.kexdroid.util.SystemVersions;
import com.skettios.kexdroid.util.Util;

import java.io.ByteArrayOutputStream;
import java.io.File;


public class Payload
{
    private static final File payloadDir = new File(MainActivity.external, "/payloads");
    private static final File loaderDir = new File(MainActivity.external, "/loaders");

    static
    {
        if (!payloadDir.exists())
            payloadDir.mkdir();

        if (!loaderDir.exists())
            payloadDir.mkdir();
    }

    public static byte[] generatePayload(SystemVersions systemVersion, String payloadName) throws Exception
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] payload = Util.readFile(new File(payloadDir, payloadName));
        byte[] loader = Util.readFile(new File(loaderDir, systemVersion.loaderName));

        int padding = 0;
        while ((payload.length + padding & 0x3) != 0)
        {
            padding++;
        }

        out.write(loader);
        Util.writeu32(payload.length + padding, out);
        out.write(payload);
        out.write(new byte[padding]);

        return out.toByteArray();
    }
}
