package com.skettios.kexdroid;

import android.widget.TextView;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class HaxClient implements Runnable
{
    private final Socket socket;
//    private final TextView exploits;
//    private final TextView data;
//    private final TextView errors;
    private static final File dataFile = new File("src/main/data");

    static
    {
        dataFile.mkdirs();
    }

    public HaxClient(Socket socket, TextView exploits, TextView data, TextView errors)
    {
        this.socket = socket;
//        this.exploits = exploits;
//        this.data = data;
//        this.errors = errors;
    }

    public void run()
    {
        try
        {
            InputStream in = this.socket.getInputStream();
            OutputStream out = this.socket.getOutputStream();


            HTTPRequest header = getRequest(in);

            String path = header.getPath();

            SystemVersions version = SystemVersions.getSystemVersion(header.getPropriety("User-Agent"));

            if (version != null)
            {
                if (path.indexOf('?') != -1)
                {
                    serveHax(version, out, header);
//                    exploits.setText("Exploits: " + Long.toString(ResourceCounter.instance.payloadServed()));
                }
                else
                {
                    serveError(out);
//                    errors.setText("Errors: " + Long.toString(ResourceCounter.instance.errors()));
                }
            }
            else
            {
                serveFile(out, header);
//                data.setText("Data: " + Long.toString(ResourceCounter.instance.dataServed()));
            }

            in.close();
            out.close();
            this.socket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
//            this.errors.setText(Long.toString(ResourceCounter.instance.errors()));
        }
    }

    private void serveFile(OutputStream out, HTTPRequest header) throws IOException
    {
        writeHeader(new BufferedWriter(new OutputStreamWriter(out)), null);


        System.out.println(header.getPath());
        File file;
        if (header.getPath().equals("/"))
        {
            file = new File(dataFile, "/index.html");
        }
        else
        {
            file = new File(dataFile, header.getPath());
        }
        System.out.println(file);
        out.write(Util.readFile(file));
        out.flush();
    }

    private void serveError(OutputStream out) throws IOException
    {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

        writeHeader(writer, "text/html");
        writer.write("<html><head><title>Error</title></head><body><p>There was an error</p></body></html>");
        writer.flush();
    }


    private void writeHeader(BufferedWriter writer, String contentType)
            throws IOException
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

        if (-1 == header.getPath().indexOf('?'))
        {
            serveError(out);
            return;
        }

        String payloadName = header.getPath().substring(header.getPath().indexOf('?') + 1);
        System.out.println(payloadName);

        switch (systemVersion)
        {
            case EU_2_0_0:
            case EU_2_1_0:
            case EU_4_0_0:
            case EU_5_0_0:
            case JP_4_0_0:
            case JP_5_0_0:
            case JP_5_1_0:
            case US_5_0_0:
            case US_5_1_0:
            case US_5_5_1:
                System.out.println(systemVersion.name());
                writeHeader(writer, "video/mp4");
                Stagefright.serveHax(out, systemVersion, header, payloadName + ".bin");
                break;

            default:
                serveError(out);
        }
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


/* Location:              D:\Libraries\Desktop\wii u exploit server\HaxServer.jar!\com\gudenau\haxserver\HaxClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */