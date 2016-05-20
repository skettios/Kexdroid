package com.skettios.kexdroid;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    public static Context context;
    private HaxServer server;
    private Thread serverThread;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.RED));

        String ipAddress = "";
        System.out.println(Environment.getExternalStorageDirectory());
        context = getApplicationContext();


        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        File external = Environment.getExternalStoragePublicDirectory("kexdroid");
        external.mkdirs();

        try
        {
            AssetManager am = getAssets();
            String[] list = am.list("");
            for (String s : list)
            {
                if (s.equals("data") || s.equals("loaders") || s.equals("payloads"))
                {
                    File folder = new File(external + "/" + s);
                    folder.mkdirs();

                    String[] inner = am.list(s);
                    for (String name : inner)
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

        try
        {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); )
            {
                NetworkInterface networkInterface = (NetworkInterface) en.nextElement();
                for (Enumeration ip = networkInterface.getInetAddresses(); ip.hasMoreElements(); )
                {
                    InetAddress address = (InetAddress) ip.nextElement();
                    if (!address.isLoopbackAddress() && address instanceof Inet4Address)
                    {
                        ipAddress = address.getHostAddress().toString();
                    }
                }
            }
        }
        catch (Exception e)
        {

        }

        ((TextView) findViewById(R.id.ip_address)).setText("IP Address: " + ipAddress + ":1337");
        server = new HaxServer();
        serverThread = new Thread(server);
    }


    public void startServer(View view)
    {
        if (!server.isRunning)
        {
            serverThread = new Thread(server);
            serverThread.start();

            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.GREEN));
            ((Button) view.getRootView().findViewById(R.id.button)).setEnabled(false);
            server.isRunning = true;
        }
    }
}
