package com.skettios.kexdroid;

import android.widget.TextView;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class HaxServer implements Runnable
{
    public volatile boolean isRunning = false;

    @Override
    public void run()
    {
        try
        {
            ServerSocket server = new ServerSocket(1337);

            long clientCount = 0L;

            while(isRunning)
            {
                Socket socket = server.accept();

                HaxClient client = new HaxClient(socket);
                Thread thread = new Thread(client);
                thread.setDaemon(true);

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("clientThread-");
                stringBuilder.append(clientCount++);
                thread.setName(stringBuilder.toString());

                System.out.println("YEA");

                thread.start();
            }

            server.close();
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
