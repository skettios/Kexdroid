package com.skettios.kexdroid;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity
{
    public static Context context;

    public boolean serverStarted = false;
    private Thread serverThread;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String ipAddress = "";
        System.out.println(Environment.getExternalStorageDirectory());
        context = getApplicationContext();

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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

        ((TextView) findViewById(R.id.textView4)).setText("IP Address: " + ipAddress);
        TextView exploits = (TextView) findViewById(R.id.textView);
        TextView errors = (TextView) findViewById(R.id.textView2);
        TextView data = (TextView) findViewById(R.id.textView3);
        serverThread = new Thread(new HaxServer(exploits, errors, data));
    }

    public void startServer(View view)
    {
        if (!serverStarted)
        {
            serverThread.start();
            serverStarted = true;

            ((Button) view.getRootView().findViewById(R.id.button)).setText("Stop Server");
        }
    }
}
