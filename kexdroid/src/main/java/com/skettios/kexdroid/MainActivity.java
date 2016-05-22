package com.skettios.kexdroid;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.skettios.kexdroid.server.HaxServer;
import com.skettios.kexdroid.tasks.DownloadFileTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity
{
    private HaxServer server;
    private Thread serverThread;

    public static final File external = Environment.getExternalStoragePublicDirectory("kexdroid");

    static
    {
        external.mkdir();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.RED));

        String ipAddress = "";

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (permission != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);

        try
        {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); )
            {
                NetworkInterface networkInterface = (NetworkInterface) en.nextElement();
                for (Enumeration ip = networkInterface.getInetAddresses(); ip.hasMoreElements(); )
                {
                    InetAddress address = (InetAddress) ip.nextElement();
                    if (!address.isLoopbackAddress() && address instanceof Inet4Address)
                        ipAddress = address.getHostAddress();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        ((TextView) findViewById(R.id.ip_address)).setText("IP Address\nhttp://" + ipAddress + ":1337/hax");
        server = new HaxServer();
    }

    private void copyLoaders()
    {
        try
        {
            AssetManager am = getAssets();
            String[] rootAssetList = am.list("");
            for (String s : rootAssetList)
            {
                if (s.equals("loaders"))
                {
                    File folder = new File(external + "/" + s);
                    folder.mkdirs();

                    String[] innerAssetList = am.list(s);
                    for (String name : innerAssetList)
                    {
                        InputStream in = am.open(s + "/" + name);
                        int size = in.available();
                        byte[] buffer = new byte[size];
                        in.read(buffer);
                        in.close();
                        FileOutputStream out = new FileOutputStream(folder + "/" + name);
                        out.write(buffer);
                        out.close();
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void startServer(View view)
    {
        copyLoaders();

        File payloads = new File(external, "payloads");
        if (!payloads.exists() || payloads.listFiles().length == 0)
        {
            payloads.mkdir();
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.YELLOW));
            new DownloadFileTask(this).execute("https://github.com/dimok789/homebrew_launcher/releases/download/v1.2_RC3/homebrew_launcher.v1.2_webserver_files_RC3.zip");
        }
        else
        {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.GREEN));
        }

        serverThread = new Thread(server);
        serverThread.start();

        (view.getRootView().findViewById(R.id.button)).setEnabled(false);
    }
}
