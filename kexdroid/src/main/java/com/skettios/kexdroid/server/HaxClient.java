package com.skettios.kexdroid.server;

import com.skettios.kexdroid.util.SystemVersions;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class HaxClient implements Runnable
{
    private final Socket socket;

    public HaxClient(Socket socket)
    {
        this.socket = socket;
    }

    public void run()
    {
        try
        {
            InputStream in = this.socket.getInputStream();
            OutputStream out = this.socket.getOutputStream();
            HTTPRequest header = getRequest(in);

            SystemVersions version = SystemVersions.getSystemVersion(header.getPropriety("User-Agent"));
            System.out.println(version);

            if (version != null)
            {
                serveHax(version, out, header);
            }

            in.close();
            out.close();
            this.socket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void serveError(OutputStream out) throws IOException
    {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

        writeHeader(writer, "text/html");
        writer.write("<html><head><title>Error</title></head><body><p>There was an error</p></body></html>");
        writer.flush();
    }


    private void writeHeader(BufferedWriter writer, String contentType) throws IOException
    {
        writer.write("HTTP/1.1 200 OK\r\n");
        if (contentType != null)
        {
            if (contentType.contains("video"))
            {
                writer.write("Transfer-Encoding: chunked\r\n");
            }
            writer.write("Content-Type: ");
            writer.write(contentType);
            writer.write("\r\n");
        }
        writer.write("\r\n");
        writer.flush();
    }

    private void serveHax(SystemVersions systemVersion, OutputStream out, HTTPRequest header) throws Exception
    {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

        writeHeader(writer, "video/mp4");
        if (Stagefright.serveHax(out, systemVersion, header))
            System.out.println("SUCCESS!");
        else
            serveError(out);
    }

    private HTTPRequest getRequest(InputStream in) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line = reader.readLine();

        String[] splitLine = line.split(" ");
        String method = splitLine[0].trim();
        String path = splitLine[1].trim();
        String protocol = splitLine[2].trim();

        List<HTTPPropriety> props = new ArrayList();
        for (line = reader.readLine(); (line != null) && (!line.isEmpty()); line = reader.readLine())
        {
            splitLine = line.split(":", 2);
            props.add(new HTTPPropriety(splitLine[0].trim(), splitLine[1].trim()));
        }

        return new HTTPRequest(method, protocol, path, props);
    }
}
