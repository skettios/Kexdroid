package com.skettios.kexdroid.tasks;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import com.skettios.kexdroid.MainActivity;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.skettios.kexdroid.MainActivity.external;

public class DownloadFileTask extends AsyncTask<String, String, String>
{
    private final Activity activity;

    public DownloadFileTask(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings)
    {
        try
        {
            downloadFile(strings[0]);

            ZipInputStream zip = new ZipInputStream(new FileInputStream(new File(external, "/temp/hbl.zip")));
            ZipEntry entry = zip.getNextEntry();
            while (entry != null)
            {
                File fileEntry = new File(external, "/temp/" + entry.getName());

                if (!entry.isDirectory())
                    extractFile(zip, fileEntry);
                else
                    fileEntry.mkdir();

                zip.closeEntry();
                entry = zip.getNextEntry();
            }

            zip.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s)
    {
        try
        {
            File www = new File(MainActivity.external, "/temp/www/");
            File dest = new File(MainActivity.external, "/payloads/");
            File[] binaries = www.listFiles();

            for (File bin : binaries)
            {
                String name = bin.getName();
                if (name.contains("code550"))
                    copyFile(bin, dest);
                if (name.contains("code532"))
                    copyFile(bin, dest);
                if (name.contains("code500"))
                    copyFile(bin, dest);
                if (name.contains("code400"))
                    copyFile(bin, dest);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            deleteDirectory(new File(MainActivity.external, "/temp/"));
            activity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    ((AppCompatActivity) activity).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.GREEN));
                }
            });
        }
    }

    private void deleteDirectory(File directory)
    {
        File[] contents = directory.listFiles();
        for (File file : contents)
        {
            if (file.isDirectory())
                deleteDirectory(file);
            else
                file.delete();
        }

        directory.delete();
    }

    private void downloadFile(String string) throws IOException
    {
        URL url = new URL(string);
        URLConnection conn = url.openConnection();
        conn.connect();

        BufferedInputStream in = new BufferedInputStream(url.openStream());
        File file = new File(external, "/temp/hbl.zip");
        file.getParentFile().mkdirs();
        FileOutputStream out = new FileOutputStream(file);
        byte data[] = new byte[1024];
        int count;
        while ((count = in.read(data)) != -1)
            out.write(data, 0, count);

        out.flush();
        out.close();
        in.close();
    }

    private void extractFile(ZipInputStream in, File file) throws IOException
    {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        byte[] data = new byte[1024];
        int read;
        while ((read = in.read(data)) != -1)
            bos.write(data, 0, read);

        bos.flush();
        bos.close();
    }

    private void copyFile(File filePath, File fileDest) throws IOException
    {
        FileInputStream in = new FileInputStream(filePath);
        FileOutputStream out = new FileOutputStream(fileDest + "/" + filePath.getName());
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0)
            out.write(buffer, 0, length);

        in.close();
        out.flush();
        out.close();
    }
}
