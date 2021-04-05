package com.req.processmaster.mytask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.req.processmaster.MainActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileFromURL extends AsyncTask<String, String, String> {

    private final MainActivity parent;
    File folder;
    private ProgressDialog dialog;

    public DownloadFileFromURL(MainActivity parent)
    {
        dialog = new ProgressDialog(parent);
        this.parent = parent;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            URL url = new URL(f_url[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            int lenghtOfFile = conection.getContentLength();

            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            OutputStream output;
            folder = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "ProcessManager");
            boolean success = true;
            if (!folder.exists())
            {
                success = folder.mkdirs();
            }
            if (success)
            {
                // Output stream to write file
                output = new FileOutputStream(folder.getAbsolutePath()+"/simpleWebsiteHTMLCSSJavaScricpt.zip");
                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                    // flushing output

                }
                output.flush();
                output.close();

            }
            else
            {
                dialog.dismiss();

                parent.runOnUiThread(new Runnable() {
                    public void run() {
                        parent.callAlert("Storage Error");
                    }
                });


            }

            // closing streams
            input.close();
        }
        catch (Exception e) {
            dialog.dismiss();

            parent.runOnUiThread(new Runnable() {
                public void run() {
                    parent.callAlert("Server Error");
                }
            });

            Log.e("Error: ", e.getMessage());
        }
        return null;
    }

    protected void onProgressUpdate(String... progress) {
        dialog.setMessage("Please wait...  "+Integer.parseInt(progress[0]));
    }

    @Override
    protected void onPostExecute(String file_url) {
        // dismiss the dialog after the file was downloaded
        dialog.dismiss();

        if(folder!=null)
        parent.unpackZip(folder.getAbsolutePath(),"/simpleWebsiteHTMLCSSJavaScricpt.zip");


    }

}