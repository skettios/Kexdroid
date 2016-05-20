package com.skettios.kexdroid;

import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;


public class Payload
{
    private static final File payloadDir = new File(Environment.getExternalStoragePublicDirectory("kexdroid") + "/payloads");
    private static final File loaderDir = new File(Environment.getExternalStoragePublicDirectory("kexdroid") + "/loaders");

    public static byte[] generatePayload(SystemVersions systemVersion) throws Exception
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] payload = Util.readFile(new File(payloadDir, systemVersion.payloadName));
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
