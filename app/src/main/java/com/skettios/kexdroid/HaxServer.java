package com.skettios.kexdroid;

import android.widget.TextView;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class HaxServer implements Runnable
{
    private final TextView exploits;
    private final TextView errors;
    private final TextView data;

    public HaxServer(TextView exploits, TextView errors, TextView data)
    {
        this.exploits = exploits;
        this.errors = errors;
        this.data = data;
    }

    @Override
    public void run()
    {
        try
        {
            ServerSocket server = new ServerSocket(25565);

            long clientCount = 0L;

            while(true)
            {
                Socket socket = server.accept();

                HaxClient client = new HaxClient(socket, exploits, errors, data);
                Thread thread = new Thread(client);
                thread.setDaemon(true);


                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("clientThread-");
                stringBuilder.append(clientCount++);
                thread.setName(stringBuilder.toString());


                thread.start();
            }
        }
        catch (BindException e)
        {
            System.err.println("Could not bind port 1337, are you using Skype?");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
