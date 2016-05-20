package com.skettios.kexdroid;

import java.lang.reflect.Field;

public enum SystemVersions
{
    US_5_5_1("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.30 (KHTML, like Gecko) NX/3.0.4.2.12 NintendoBrowser/4.3.1.11264.US", Constants550.class, "stagefright.bin"),
    US_5_5_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.30 (KHTML, like Gecko) NX/3.0.4.2.11 NintendoBrowser/4.3.0.11224.US", Constants550.class, "stagefright.bin"),
    US_5_4_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.30 (KHTML, like Gecko) NX/3.0.4.2.9 NintendoBrowser/4.2.0.11146.US", Constants532.class, "stagefright.bin"),
    US_5_3_2("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.15 NintendoBrowser/4.1.1.9601.US", Constants532.class, "stagefright.bin"),
    US_5_3_1("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.15 NintendoBrowser/4.1.1.9601.US", null, null),
    US_5_3_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.14 NintendoBrowser/4.1.0.9584.US", null, null),
    US_5_2_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.14 NintendoBrowser/3.1.1.9577.US", null, null),
    US_5_1_2("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.14 NintendoBrowser/3.1.1.9577.US", null, null),
    US_5_1_1("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.14 NintendoBrowser/3.1.1.9577.US", null, null),
    US_5_1_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.12 NintendoBrowser/3.0.0.9561.US", null, null),
    US_5_0_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.12 NintendoBrowser/3.0.0.9561.US", null, null),
    US_4_0_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.6 NintendoBrowser/2.0.0.9362.US", null, null),
    US_2_1_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/534.52 (KHTML, like Gecko) NX/2.1.0.8.23 NintendoBrowser/1.1.0.7579.US", null, null),
    US_2_0_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/534.52 (KHTML, like Gecko) NX/2.1.0.8.21 NintendoBrowser/1.0.0.7494.US", null, null),

    EU_5_5_1("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.30 (KHTML, like Gecko) NX/3.0.4.2.12 NintendoBrowser/4.3.1.11264.EU", Constants550.class, "stagefright.bin"),
    EU_5_5_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.30 (KHTML, like Gecko) NX/3.0.4.2.11 NintendoBrowser/4.3.0.11224.EU", Constants550.class, "stagefright.bin"),
    EU_5_4_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.30 (KHTML, like Gecko) NX/3.0.4.2.9 NintendoBrowser/4.2.0.11146.EU", Constants532.class, "stagefright.bin"),
    EU_5_3_2("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.15 NintendoBrowser/4.1.1.9601.EU", Constants532.class, "stagefright.bin"),
    EU_5_3_1("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.15 NintendoBrowser/4.1.1.9601.EU", null, null),
    EU_5_3_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.14 NintendoBrowser/4.1.0.9584.EU", null, null),
    EU_5_2_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.14 NintendoBrowser/3.1.1.9577.EU", null, null),
    EU_5_1_2("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.14 NintendoBrowser/3.1.1.9577.EU", null, null),
    EU_5_1_1("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.14 NintendoBrowser/3.1.1.9577.EU", null, null),
    EU_5_1_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.12 NintendoBrowser/3.0.0.9561.EU", null, null),
    EU_5_0_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.12 NintendoBrowser/3.0.0.9561.EU", null, null),
    EU_4_0_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.6 NintendoBrowser/2.0.0.9362.EU", null, null),
    EU_2_1_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/534.52 (KHTML, like Gecko) NX/2.1.0.8.23 NintendoBrowser/1.1.0.7579.EU", null, null),
    EU_2_0_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/534.52 (KHTML, like Gecko) NX/2.1.0.8.21 NintendoBrowser/1.0.0.7494.EU", null, null),

    JP_5_4_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.30 (KHTML, like Gecko) NX/3.0.4.2.9 NintendoBrowser/4.2.0.11146.JP", Constants532.class, "stagefright.bin"),
    JP_5_3_2("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.15 NintendoBrowser/4.1.1.9601.JP", Constants532.class, "stagefright.bin"),
    JP_5_3_1("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.15 NintendoBrowser/4.1.1.9601.JP", null, null),
    JP_5_3_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.14 NintendoBrowser/4.1.0.9584.JP", null, null),
    JP_5_2_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.14 NintendoBrowser/3.1.1.9577.JP", null, null),
    JP_5_1_2("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.14 NintendoBrowser/3.1.1.9577.JP", null, null),
    JP_5_1_1("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.14 NintendoBrowser/3.1.1.9577.JP", null, null),
    JP_5_1_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.12 NintendoBrowser/3.0.0.9561.JP", null, null),
    JP_5_0_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.12 NintendoBrowser/3.0.0.9561.JP", null, null),
    JP_4_0_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.28 (KHTML, like Gecko) NX/3.0.3.12.6 NintendoBrowser/2.0.0.9362.JP", null, null),
    JP_2_1_0("Mozilla/5.0 (Nintendo WiiU) AppleWebKit/534.52 (KHTML, like Gecko) NX/2.1.0.8.23 NintendoBrowser/1.1.0.7579.JP", null, null);

    public final String value;
    private final Class<?> constants;
    public final String loaderName;

    private SystemVersions(String browserVersion, Class<?> constants, String loaderName)
    {
        this.value = browserVersion;
        this.constants = constants;
        this.loaderName = loaderName;
    }

    public static SystemVersions getSystemVersion(HTTPPropriety propriety)
    {
        if (propriety == null)
        {
            return null;
        }
        SystemVersions[] arrayOfSystemVersions;
        int j = (arrayOfSystemVersions = values()).length;
        for (int i = 0; i < j; i++)
        {
            SystemVersions version = arrayOfSystemVersions[i];
            if (version.value.equals(propriety.getValue()))
            {
                return version;
            }
        }

        System.out.println("Warning, unknown system version: " + propriety.getValue());

        return null;
    }

    public int getConstantInt(String name) throws Exception
    {
        Field f = this.constants.getDeclaredField(name);
        f.setAccessible(true);
        return f.getInt(null);
    }
}
